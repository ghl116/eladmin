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
package me.zhengjie.modules.app.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.base.BaseEntity;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author gaohl
* @date 2020-10-04
**/
@Entity
@Data
@Table(name="salesdetail")
public class Salesdetail  extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private String id;

    @Column(name = "zhbh",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "租户编号")
    private String zhbh;

    @Column(name = "productid",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "产品id")
    private String productid;

    @Column(name = "businessstatus",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "商务阶段")
    private String businessstatus;

    @Column(name = "online")
    @ApiModelProperty(value = "上线情况")
    private String online;

    @Column(name = "onlinedate")
    @ApiModelProperty(value = "上线日期")
    private Timestamp onlinedate;

    @Column(name = "offlinedate")
    @ApiModelProperty(value = "下线日期")
    private Timestamp offlinedate;

    @Column(name = "onlineleader")
    @ApiModelProperty(value = "上线负责人")
    private String onlineleader;

    @Column(name = "businessman")
    @ApiModelProperty(value = "商务岗确认人")
    private String businessman;

    @Column(name = "reqnum")
    @ApiModelProperty(value = "需求工单号")
    private Integer reqnum;

    @Column(name = "contractnum")
    @ApiModelProperty(value = "合同编号")
    private Integer contractnum;

    @Column(name = "constructioncost")
    @ApiModelProperty(value = "建设费")
    private BigDecimal constructioncost;

    @Column(name = "maintenancecost")
    @ApiModelProperty(value = "运维费/年")
    private BigDecimal maintenancecost;

    @Column(name = "note")
    @ApiModelProperty(value = "备注")
    private String note;



    public void copy(Salesdetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}