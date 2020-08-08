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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author witt
* @date 2020-08-06
**/
@Data
public class JjRealTimeDynamicDto implements Serializable {

    private Integer id;

    /** 基金代码 */
    private String fundcode;

    /** 更新时间 */
    private String gztime;

    /** 最新估值 */
    private String gszzl;

    /** 最新值 */
    private String gsz;

    /** 昨日净值 */
    private String dwjz;

    /** 净值日期 */
    private String jzrq;

    /** 基金名称 */
    private String name;

    /** 创建时间 */
    private Timestamp createTime;
}