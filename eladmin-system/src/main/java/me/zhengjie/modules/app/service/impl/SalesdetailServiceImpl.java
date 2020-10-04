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

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.app.domain.Salesdetail;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.app.repository.SalesdetailRepository;
import me.zhengjie.modules.app.service.SalesdetailService;
import me.zhengjie.modules.app.service.dto.SalesdetailDto;
import me.zhengjie.modules.app.service.dto.SalesdetailQueryCriteria;
import me.zhengjie.modules.app.service.mapstruct.SalesdetailMapper;
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
public class SalesdetailServiceImpl implements SalesdetailService {

    private final SalesdetailRepository salesdetailRepository;
    private final SalesdetailMapper salesdetailMapper;

    @Override
    public Map<String,Object> queryAll(SalesdetailQueryCriteria criteria, Pageable pageable){
        Page<Salesdetail> page = salesdetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(salesdetailMapper::toDto));
    }

    @Override
    public List<SalesdetailDto> queryAll(SalesdetailQueryCriteria criteria){
        return salesdetailMapper.toDto(salesdetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SalesdetailDto findById(String id) {
        Salesdetail salesdetail = salesdetailRepository.findById(id).orElseGet(Salesdetail::new);
        ValidationUtil.isNull(salesdetail.getId(),"Salesdetail","id",id);
        return salesdetailMapper.toDto(salesdetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesdetailDto create(Salesdetail resources) {
        List<Salesdetail> list = salesdetailRepository.findByZhbhAndProductid(resources.getZhbh(),resources.getProductid());
        if(list.size() > 0 ){
            throw new BadRequestException("产品，客户对应的销售信息已存在");
        }
        resources.setId(IdUtil.simpleUUID()); 
        return salesdetailMapper.toDto(salesdetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Salesdetail resources) {
        Salesdetail salesdetail = salesdetailRepository.findById(resources.getId()).orElseGet(Salesdetail::new);
        ValidationUtil.isNull( salesdetail.getId(),"Salesdetail","id",resources.getId());
        List<Salesdetail> list = salesdetailRepository.findByZhbhAndProductid(resources.getZhbh(),resources.getProductid());
        if(list.size() > 1  ){
            throw new BadRequestException("产品，客户对应的销售信息已存在");
        }
        for(Salesdetail salesdetail1 : list){
            if(salesdetail1.getId().equals(resources.getId()) == false){
                throw new BadRequestException("产品，客户对应的销售信息已存在");
            }
        }
        salesdetail.copy(resources);
        salesdetailRepository.save(salesdetail);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            salesdetailRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SalesdetailDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SalesdetailDto salesdetail : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("租户编号", salesdetail.getZhbh());
            map.put("产品id", salesdetail.getProductid());
            map.put("商务阶段", salesdetail.getBusinessstatus());
            map.put("上线情况", salesdetail.getOnline());
            map.put("上线日期", salesdetail.getOnlinedate());
            map.put("下线日期", salesdetail.getOfflinedate());
            map.put("上线负责人", salesdetail.getOnlineleader());
            map.put("商务岗确认人", salesdetail.getBusinessman());
            map.put("需求工单号", salesdetail.getReqnum());
            map.put("合同编号", salesdetail.getContractnum());
            map.put("建设费", salesdetail.getConstructioncost());
            map.put("运维费/年", salesdetail.getMaintenancecost());
            map.put("备注", salesdetail.getNote());
            map.put("创建者", salesdetail.getCreateBy());
            map.put("更新者", salesdetail.getUpdatedBy());
            map.put("创建日期", salesdetail.getCreateTime());
            map.put("更新时间", salesdetail.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}