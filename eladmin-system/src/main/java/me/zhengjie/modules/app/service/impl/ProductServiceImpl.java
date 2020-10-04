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
package me.zhengjie.modules.app.service.impl;

import me.zhengjie.modules.app.domain.Product;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.app.repository.ProductRepository;
import me.zhengjie.modules.app.service.ProductService;
import me.zhengjie.modules.app.service.dto.ProductDto;
import me.zhengjie.modules.app.service.dto.ProductQueryCriteria;
import me.zhengjie.modules.app.service.mapstruct.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
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
* @author gaohl
* @date 2020-10-04
**/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Map<String,Object> queryAll(ProductQueryCriteria criteria, Pageable pageable){
        Page<Product> page = productRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productMapper::toDto));
    }

    @Override
    public List<ProductDto> queryAll(ProductQueryCriteria criteria){
        return productMapper.toDto(productRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProductDto findById(String id) {
        Product product = productRepository.findById(id).orElseGet(Product::new);
        ValidationUtil.isNull(product.getId(),"Product","id",id);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDto create(Product resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return productMapper.toDto(productRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Product resources) {
        Product product = productRepository.findById(resources.getId()).orElseGet(Product::new);
        ValidationUtil.isNull( product.getId(),"Product","id",resources.getId());
        product.copy(resources);
        productRepository.save(product);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProductDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductDto product : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("产品名称", product.getProductname());
            map.put("所属BU", product.getBuname());
            map.put("备注", product.getNote());
            map.put("创建人", product.getCreateBy());
            map.put("创建时间", product.getCreateTime());
            map.put("更新人", product.getUpdatedBy());
            map.put("更新时间", product.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}