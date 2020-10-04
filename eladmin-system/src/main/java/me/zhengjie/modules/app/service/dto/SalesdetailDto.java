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
package me.zhengjie.modules.app.service.dto;

import lombok.Data;
import me.zhengjie.base.BaseDTO;
import me.zhengjie.base.BaseEntity;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author gaohl
* @date 2020-10-04
**/
@Data
public class SalesdetailDto extends BaseDTO implements Serializable {

    private String id;

    /** 租户编号 */
    private String zhbh;

    /** 产品id */
    private String productid;

    /** 商务阶段 */
    private String businessstatus;

    /** 上线情况 */
    private String online;

    /** 上线日期 */
    private Timestamp onlinedate;

    /** 下线日期 */
    private Timestamp offlinedate;

    /** 上线负责人 */
    private String onlineleader;

    /** 商务岗确认人 */
    private String businessman;

    /** 需求工单号 */
    private Integer reqnum;

    /** 合同编号 */
    private Integer contractnum;

    /** 建设费 */
    private BigDecimal constructioncost;

    /** 运维费/年 */
    private BigDecimal maintenancecost;

    /** 备注 */
    private String note;

}