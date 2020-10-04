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

import me.zhengjie.modules.app.domain.Cust;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.app.repository.CustRepository;
import me.zhengjie.modules.app.service.CustService;
import me.zhengjie.modules.app.service.dto.CustDto;
import me.zhengjie.modules.app.service.dto.CustQueryCriteria;
import me.zhengjie.modules.app.service.mapstruct.CustMapper;
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
* @date 2020-10-03
**/
@Service
@RequiredArgsConstructor
public class CustServiceImpl implements CustService {

    private final CustRepository custRepository;
    private final CustMapper custMapper;

    @Override
    public Map<String,Object> queryAll(CustQueryCriteria criteria, Pageable pageable){
        Page<Cust> page = custRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(custMapper::toDto));
    }

    @Override
    public List<CustDto> queryAll(CustQueryCriteria criteria){
        return custMapper.toDto(custRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CustDto findById(String zhbh) {
        Cust cust = custRepository.findById(zhbh).orElseGet(Cust::new);
        ValidationUtil.isNull(cust.getZhbh(),"Cust","zhbh",zhbh);
        return custMapper.toDto(cust);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustDto create(Cust resources) {
//        resources.setZhbh(IdUtil.simpleUUID());
        return custMapper.toDto(custRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Cust resources) {
        Cust cust = custRepository.findById(resources.getZhbh()).orElseGet(Cust::new);
        ValidationUtil.isNull( cust.getZhbh(),"Cust","id",resources.getZhbh());
        cust.copy(resources);
        custRepository.save(cust);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String zhbh : ids) {
            custRepository.deleteById(zhbh);
        }
    }

    @Override
    public void download(List<CustDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CustDto cust : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("客户编号", cust.getZhbh());
            map.put("客户名称", cust.getKhmc());
            map.put("客户类别", cust.getKhlb());
            map.put("备注", cust.getNote());
            map.put("创建人", cust.getCreateuser());
            map.put("创建时间", cust.getCreatetime());
            map.put("更新人", cust.getUpdateuser());
            map.put("更新时间", cust.getUpdatetime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}