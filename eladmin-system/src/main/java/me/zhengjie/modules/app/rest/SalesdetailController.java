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
import me.zhengjie.modules.app.domain.Salesdetail;
import me.zhengjie.modules.app.service.SalesdetailService;
import me.zhengjie.modules.app.service.dto.SalesdetailQueryCriteria;
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
* @date 2020-10-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/api/salesdetail管理")
@RequestMapping("/api/salesdetail")
public class SalesdetailController {

    private final SalesdetailService salesdetailService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('salesdetail:list')")
    public void download(HttpServletResponse response, SalesdetailQueryCriteria criteria) throws IOException {
        salesdetailService.download(salesdetailService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/api/salesdetail")
    @ApiOperation("查询/api/salesdetail")
    @PreAuthorize("@el.check('salesdetail:list')")
    public ResponseEntity<Object> query(SalesdetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(salesdetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/api/salesdetail")
    @ApiOperation("新增/api/salesdetail")
    @PreAuthorize("@el.check('salesdetail:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Salesdetail resources){
        return new ResponseEntity<>(salesdetailService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/api/salesdetail")
    @ApiOperation("修改/api/salesdetail")
    @PreAuthorize("@el.check('salesdetail:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Salesdetail resources){
        salesdetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/api/salesdetail")
    @ApiOperation("删除/api/salesdetail")
    @PreAuthorize("@el.check('salesdetail:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        salesdetailService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}