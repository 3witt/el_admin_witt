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

import me.zhengjie.modules.system.domain.JjData;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.JjDataRepository;
import me.zhengjie.modules.system.service.JjDataService;
import me.zhengjie.modules.system.service.dto.JjDataDto;
import me.zhengjie.modules.system.service.dto.JjDataQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.JjDataMapper;
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
 * @author witt
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2020-08-05
 **/
@Service
@RequiredArgsConstructor
public class JjDataServiceImpl implements JjDataService {

    private final JjDataRepository jjDataRepository;
    private final JjDataMapper jjDataMapper;

    @Override
    public Map<String, Object> queryAll(JjDataQueryCriteria criteria, Pageable pageable) {
        Page<JjData> page = jjDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(jjDataMapper::toDto));
    }

    @Override
    public List<JjDataDto> queryAll(JjDataQueryCriteria criteria) {
        return jjDataMapper.toDto(jjDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public JjDataDto findById(Integer id) {
        JjData jjData = jjDataRepository.findById(id).orElseGet(JjData::new);
        ValidationUtil.isNull(jjData.getId(), "JjData", "id", id);
        return jjDataMapper.toDto(jjData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JjDataDto create(JjData resources) {
        if (jjDataRepository.findByJjCode(resources.getJjCode()) != null) {
            throw new EntityExistException(JjData.class, "jj_code", resources.getJjCode());
        }
        return jjDataMapper.toDto(jjDataRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JjData resources) {
        JjData jjData = jjDataRepository.findById(resources.getId()).orElseGet(JjData::new);
        ValidationUtil.isNull(jjData.getId(), "JjData", "id", resources.getId());
        JjData jjData1 = null;
        jjData1 = jjDataRepository.findByJjCode(resources.getJjCode());
        if (jjData1 != null && !jjData1.getId().equals(jjData.getId())) {
            throw new EntityExistException(JjData.class, "jj_code", resources.getJjCode());
        }
        jjData.copy(resources);
        jjDataRepository.save(jjData);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            jjDataRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<JjDataDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JjDataDto jjData : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("基金代码", jjData.getJjCode());
            map.put("基金名称", jjData.getJjName());
            map.put("基金类型", jjData.getJjType());
            map.put("金经理基", jjData.getJjManager());
            map.put("基金最新涨值", jjData.getEquityReturn());
            map.put("基金最新涨值时间", jjData.getTimeStr());
            map.put("基金成立时间", jjData.getJjEstablishedTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<JjData> findAll() {
        return jjDataRepository.findAll();
    }

    @Override
    public Page<JjData> findAll(Pageable pageable) {
        return jjDataRepository.findAll(pageable);
    }

    @Override
    public List<JjData> saveAll(List<JjData> jjDatas) {
        return jjDataRepository.saveAll(jjDatas);
    }

    @Override
    public JjData findByJjCode(String jjCode) {
        return jjDataRepository.findByJjCode(jjCode);
    }

    @Override
    public void deleteAll() {
        jjDataRepository.deleteAll();
    }

}
