/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.EmailRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.EncryptUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "email")
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Override
    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig config(EmailConfig emailConfig, EmailConfig old) throws Exception {
        emailConfig.setId(1L);
        if (!emailConfig.getPass().equals(old.getPass())) {
            // 对称加密
            emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
        }
        return emailRepository.save(emailConfig);
    }

    @Override
    @Cacheable(key = "'config'")
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }

        // 封装
        MailAccount account = new MailAccount();
        account.setUser(emailConfig.getFromUser());
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 使用STARTTLS安全连接
        account.setStarttlsEnable(true);
        // 指定实现javax.net.SocketFactory接口的类的名称,这个类将被用于创建SMTP的套接字
        account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
        // 如果设置为true,未能创建一个套接字使用指定的套接字工厂类将导致使用java.net.Socket创建的套接字类, 默认值为true
        account.setSocketFactoryFallback(true);
        String content = emailVo.getContent();
        content = content.replace("https://mail.qq.com", "..");
        // 发送
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    // 暗抄送
                    .setBccs(emailConfig.getFromUser())
                    // 明抄送
//                    .setCcs(emailConfig.getFromUser())
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * 获取哦key
     *
     * @author witt
     * @description
     * @date 16:30 2020/8/11
     */
    @Override
    public SendCloud getKey(String apiUser, String apiKey) {
        return SendCloud.createWebApi(apiUser, apiKey);
    }

    @Override
    public Result sendEmail(EmailVo emailVo, EmailConfig emailConfig) {
        try {
            String apiUser = "a1371912_test_uALIUv";
            String apiKey = "cu1cWRgrNZquwo0l";
            SendCloud webapi = getKey(apiUser, apiKey);
            Email email = Email.general()
                    .from(emailConfig.getFromUser())
                    .fromName(emailConfig.getUser())
                    .html(emailVo.getContent())          // or .plain()
                    .subject(emailVo.getSubject())
                    .to(emailVo.getTos().get(0));
            Result result = webapi.mail().send(email);
            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
