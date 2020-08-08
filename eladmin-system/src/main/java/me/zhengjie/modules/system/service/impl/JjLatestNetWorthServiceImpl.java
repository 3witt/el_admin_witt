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
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.JjLatestNetWorth;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.JjLatestNetWorthRepository;
import me.zhengjie.modules.system.service.JjLatestNetWorthService;
import me.zhengjie.modules.system.service.dto.JjLatestNetWorthDto;
import me.zhengjie.modules.system.service.dto.JjLatestNetWorthQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.JjLatestNetWorthMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author witt
* @date 2020-08-07
**/
@Service
@RequiredArgsConstructor
public class JjLatestNetWorthServiceImpl implements JjLatestNetWorthService {

    private final JjLatestNetWorthRepository jjLatestNetWorthRepository;
    private final JjLatestNetWorthMapper jjLatestNetWorthMapper;

    @Override
    public Map<String,Object> queryAll(JjLatestNetWorthQueryCriteria criteria, Pageable pageable){
        Page<JjLatestNetWorth> page = jjLatestNetWorthRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(jjLatestNetWorthMapper::toDto));
    }

    @Override
    public List<JjLatestNetWorthDto> queryAll(JjLatestNetWorthQueryCriteria criteria){
        return jjLatestNetWorthMapper.toDto(jjLatestNetWorthRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public JjLatestNetWorthDto findById(Integer id) {
        JjLatestNetWorth jjLatestNetWorth = jjLatestNetWorthRepository.findById(id).orElseGet(JjLatestNetWorth::new);
        ValidationUtil.isNull(jjLatestNetWorth.getId(),"JjLatestNetWorth","id",id);
        return jjLatestNetWorthMapper.toDto(jjLatestNetWorth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JjLatestNetWorthDto create(JjLatestNetWorth resources) {
        return jjLatestNetWorthMapper.toDto(jjLatestNetWorthRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JjLatestNetWorth resources) {
        JjLatestNetWorth jjLatestNetWorth = jjLatestNetWorthRepository.findById(resources.getId()).orElseGet(JjLatestNetWorth::new);
        ValidationUtil.isNull( jjLatestNetWorth.getId(),"JjLatestNetWorth","id",resources.getId());
        jjLatestNetWorth.copy(resources);
        jjLatestNetWorthRepository.save(jjLatestNetWorth);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            jjLatestNetWorthRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<JjLatestNetWorthDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JjLatestNetWorthDto jjLatestNetWorth : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("基金代码", jjLatestNetWorth.getJjCode());
            map.put("时间戳", jjLatestNetWorth.getX());
            map.put("时间代表时间", jjLatestNetWorth.getXTime());
            map.put("净值金额", jjLatestNetWorth.getY());
            map.put("增长百分比", jjLatestNetWorth.getEquityReturn());
            map.put(" createdTime",  jjLatestNetWorth.getCreatedTime());
            map.put(" unitMoney",  jjLatestNetWorth.getUnitMoney());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}