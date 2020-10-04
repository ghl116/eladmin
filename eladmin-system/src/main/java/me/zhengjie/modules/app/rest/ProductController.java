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
import me.zhengjie.modules.app.domain.Product;
import me.zhengjie.modules.app.service.ProductService;
import me.zhengjie.modules.app.service.dto.ProductQueryCriteria;
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
@Api(tags = "api/product管理")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('product:list')")
    public void download(HttpServletResponse response, ProductQueryCriteria criteria) throws IOException {
        productService.download(productService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/product")
    @ApiOperation("查询api/product")
    @PreAuthorize("@el.check('product:list')")
    public ResponseEntity<Object> query(ProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(productService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/product")
    @ApiOperation("新增api/product")
    @PreAuthorize("@el.check('product:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Product resources){
        return new ResponseEntity<>(productService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/product")
    @ApiOperation("修改api/product")
    @PreAuthorize("@el.check('product:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Product resources){
        productService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/product")
    @ApiOperation("删除api/product")
    @PreAuthorize("@el.check('product:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        productService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}