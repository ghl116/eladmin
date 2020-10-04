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
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author gaohl
* @date 2020-10-03
**/
@Entity
@Data
@Table(name="cust")
@EntityListeners(AuditingEntityListener.class)
public class Cust implements Serializable {

    @Id
    @Column(name = "zhbh")
    @ApiModelProperty(value = "银行编号 ")
    private String zhbh;

    @Column(name = "khmc")
    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @Column(name = "khlb")
    @ApiModelProperty(value = "客户类别")
    private String khlb;

    @Column(name = "note")
    @ApiModelProperty(value = "备注")
    private String note;

    @CreatedBy
    @Column(name = "createuser",updatable = false)
    @ApiModelProperty(value = "创建人",hidden = true)
    private String createuser;

    @Column(name = "createtime", updatable = false)
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Timestamp createtime;

    @LastModifiedBy
    @Column(name = "updateuser")
    @ApiModelProperty(value = "更新人",hidden = true)
    private String updateuser;

    @Column(name = "updatetime")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Timestamp updatetime;

    public void copy(Cust source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}