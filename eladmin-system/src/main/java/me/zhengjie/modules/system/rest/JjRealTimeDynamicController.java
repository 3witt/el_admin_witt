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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.JjRealTimeDynamic;
import me.zhengjie.modules.system.service.JjRealTimeDynamicService;
import me.zhengjie.modules.system.service.dto.JjRealTimeDynamicQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author witt
* @date 2020-08-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "jj/dynamic管理")
@RequestMapping("/api/jjRealTimeDynamic")
public class JjRealTimeDynamicController {

    private final JjRealTimeDynamicService jjRealTimeDynamicService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('jjRealTimeDynamic:list')")
    public void download(HttpServletResponse response, JjRealTimeDynamicQueryCriteria criteria) throws IOException {
        jjRealTimeDynamicService.download(jjRealTimeDynamicService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询jj/dynamic")
    @ApiOperation("查询jj/dynamic")
    @PreAuthorize("@el.check('jjRealTimeDynamic:list')")
    public ResponseEntity<Object> query(JjRealTimeDynamicQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(jjRealTimeDynamicService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增jj/dynamic")
    @ApiOperation("新增jj/dynamic")
    @PreAuthorize("@el.check('jjRealTimeDynamic:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody JjRealTimeDynamic resources){
        return new ResponseEntity<>(jjRealTimeDynamicService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改jj/dynamic")
    @ApiOperation("修改jj/dynamic")
    @PreAuthorize("@el.check('jjRealTimeDynamic:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody JjRealTimeDynamic resources){
        jjRealTimeDynamicService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除jj/dynamic")
    @ApiOperation("删除jj/dynamic")
    @PreAuthorize("@el.check('jjRealTimeDynamic:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        jjRealTimeDynamicService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}