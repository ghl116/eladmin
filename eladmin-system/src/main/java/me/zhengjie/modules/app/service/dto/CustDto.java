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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author gaohl
* @date 2020-10-03
**/
@Data
public class CustDto implements Serializable {

    /** 银行编号  */
    private String zhbh;

    /** 客户名称 */
    private String khmc;

    /** 客户类别 */
    private String khlb;

    /** 备注 */
    private String note;

    /** 创建人 */
    private String createuser;

    /** 创建时间 */
    private Timestamp createtime;

    /** 更新人 */
    private String updateuser;

    /** 更新时间 */
    private Timestamp updatetime;
}