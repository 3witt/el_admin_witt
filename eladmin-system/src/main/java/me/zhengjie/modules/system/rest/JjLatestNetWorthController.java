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
import me.zhengjie.modules.system.domain.JjLatestNetWorth;
import me.zhengjie.modules.system.service.JjLatestNetWorthService;
import me.zhengjie.modules.system.service.dto.JjLatestNetWorthQueryCriteria;
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
* @date 2020-08-07
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "jj/api管理")
@RequestMapping("/api/jjLatestNetWorth")
public class JjLatestNetWorthController {

    private final JjLatestNetWorthService jjLatestNetWorthService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('jjLatestNetWorth:list')")
    public void download(HttpServletResponse response, JjLatestNetWorthQueryCriteria criteria) throws IOException {
        jjLatestNetWorthService.download(jjLatestNetWorthService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询jj/api")
    @ApiOperation("查询jj/api")
    @PreAuthorize("@el.check('jjLatestNetWorth:list')")
    public ResponseEntity<Object> query(JjLatestNetWorthQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(jjLatestNetWorthService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增jj/api")
    @ApiOperation("新增jj/api")
    @PreAuthorize("@el.check('jjLatestNetWorth:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody JjLatestNetWorth resources){
        return new ResponseEntity<>(jjLatestNetWorthService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改jj/api")
    @ApiOperation("修改jj/api")
    @PreAuthorize("@el.check('jjLatestNetWorth:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody JjLatestNetWorth resources){
        jjLatestNetWorthService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除jj/api")
    @ApiOperation("删除jj/api")
    @PreAuthorize("@el.check('jjLatestNetWorth:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        jjLatestNetWorthService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}