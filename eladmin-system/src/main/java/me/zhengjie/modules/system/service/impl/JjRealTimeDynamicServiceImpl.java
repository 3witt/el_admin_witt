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

import me.zhengjie.modules.system.domain.JjRealTimeDynamic;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.JjRealTimeDynamicRepository;
import me.zhengjie.modules.system.service.JjRealTimeDynamicService;
import me.zhengjie.modules.system.service.dto.JjRealTimeDynamicDto;
import me.zhengjie.modules.system.service.dto.JjRealTimeDynamicQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.JjRealTimeDynamicMapper;
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
* @date 2020-08-06
**/
@Service
@RequiredArgsConstructor
public class JjRealTimeDynamicServiceImpl implements JjRealTimeDynamicService {

    private final JjRealTimeDynamicRepository jjRealTimeDynamicRepository;
    private final JjRealTimeDynamicMapper jjRealTimeDynamicMapper;

    @Override
    public Map<String,Object> queryAll(JjRealTimeDynamicQueryCriteria criteria, Pageable pageable){
        Page<JjRealTimeDynamic> page = jjRealTimeDynamicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(jjRealTimeDynamicMapper::toDto));
    }

    @Override
    public List<JjRealTimeDynamicDto> queryAll(JjRealTimeDynamicQueryCriteria criteria){
        return jjRealTimeDynamicMapper.toDto(jjRealTimeDynamicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public JjRealTimeDynamicDto findById(Integer id) {
        JjRealTimeDynamic jjRealTimeDynamic = jjRealTimeDynamicRepository.findById(id).orElseGet(JjRealTimeDynamic::new);
        ValidationUtil.isNull(jjRealTimeDynamic.getId(),"JjRealTimeDynamic","id",id);
        return jjRealTimeDynamicMapper.toDto(jjRealTimeDynamic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JjRealTimeDynamicDto create(JjRealTimeDynamic resources) {
        return jjRealTimeDynamicMapper.toDto(jjRealTimeDynamicRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JjRealTimeDynamic resources) {
        JjRealTimeDynamic jjRealTimeDynamic = jjRealTimeDynamicRepository.findById(resources.getId()).orElseGet(JjRealTimeDynamic::new);
        ValidationUtil.isNull( jjRealTimeDynamic.getId(),"JjRealTimeDynamic","id",resources.getId());
        jjRealTimeDynamic.copy(resources);
        jjRealTimeDynamicRepository.save(jjRealTimeDynamic);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            jjRealTimeDynamicRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<JjRealTimeDynamicDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JjRealTimeDynamicDto jjRealTimeDynamic : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("基金代码", jjRealTimeDynamic.getFundcode());
            map.put("更新时间", jjRealTimeDynamic.getGztime());
            map.put("最新估值", jjRealTimeDynamic.getGszzl());
            map.put("最新值", jjRealTimeDynamic.getGsz());
            map.put("昨日净值", jjRealTimeDynamic.getDwjz());
            map.put("净值日期", jjRealTimeDynamic.getJzrq());
            map.put("基金名称", jjRealTimeDynamic.getName());
            map.put("创建时间", jjRealTimeDynamic.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}