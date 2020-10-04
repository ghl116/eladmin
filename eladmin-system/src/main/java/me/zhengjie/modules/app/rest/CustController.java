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
package me.zhengjie.modules.app.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.app.domain.Cust;
import me.zhengjie.modules.app.service.CustService;
import me.zhengjie.modules.app.service.dto.CustQueryCriteria;
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
* @author gaohl
* @date 2020-10-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/api/cust管理")
@RequestMapping("/api/cust")
public class CustController {

    private final CustService custService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cust:list')")
    public void download(HttpServletResponse response, CustQueryCriteria criteria) throws IOException {
        custService.download(custService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/api/cust")
    @ApiOperation("查询/api/cust")
    @PreAuthorize("@el.check('cust:list')")
    public ResponseEntity<Object> query(CustQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(custService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/api/cust")
    @ApiOperation("新增/api/cust")
    @PreAuthorize("@el.check('cust:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Cust resources){
        return new ResponseEntity<>(custService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/api/cust")
    @ApiOperation("修改/api/cust")
    @PreAuthorize("@el.check('cust:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Cust resources){
        custService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/api/cust")
    @ApiOperation("删除/api/cust")
    @PreAuthorize("@el.check('cust:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        custService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}