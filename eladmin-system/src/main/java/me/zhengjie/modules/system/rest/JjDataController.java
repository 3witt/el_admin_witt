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
import me.zhengjie.modules.system.domain.JjData;
import me.zhengjie.modules.system.service.JjDataService;
import me.zhengjie.modules.system.service.dto.JjDataQueryCriteria;
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
* @date 2020-08-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "jj/api管理")
@RequestMapping("/api/jjData")
public class JjDataController {

    private final JjDataService jjDataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('jjData:list')")
    public void download(HttpServletResponse response, JjDataQueryCriteria criteria) throws IOException {
        jjDataService.download(jjDataService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询jj/api")
    @ApiOperation("查询jj/api")
    @PreAuthorize("@el.check('jjData:list')")
    public ResponseEntity<Object> query(JjDataQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(jjDataService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增jj/api")
    @ApiOperation("新增jj/api")
    @PreAuthorize("@el.check('jjData:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody JjData resources){
        return new ResponseEntity<>(jjDataService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改jj/api")
    @ApiOperation("修改jj/api")
    @PreAuthorize("@el.check('jjData:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody JjData resources){
        jjDataService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除jj/api")
    @ApiOperation("删除jj/api")
    @PreAuthorize("@el.check('jjData:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        jjDataService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}