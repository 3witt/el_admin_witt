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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.JjData;
import me.zhengjie.modules.system.service.dto.JjDataDto;
import me.zhengjie.modules.system.service.dto.JjDataQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author witt
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2020-08-05
 **/
public interface JjDataService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(JjDataQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<JjDataDto>
     */
    List<JjDataDto> queryAll(JjDataQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return JjDataDto
     */
    JjDataDto findById(Integer id);

    /**
     * 创建
     *
     * @param resources /
     * @return JjDataDto
     */
    JjDataDto create(JjData resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(JjData resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Integer[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<JjDataDto> all, HttpServletResponse response) throws IOException;

    List<JjData> findAll();

    Page<JjData> findAll(Pageable pageable);

    List<JjData> saveAll(List<JjData> jjDatas);

    JjData findByJjCode(String s);

    void deleteAll();
}
