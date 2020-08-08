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
* @date 2020-08-05
**/
@Data
public class JjDataDto implements Serializable {

    private Integer id;

    /** 基金代码 */
    private String jjCode;

    /** 基金名称 */
    private String jjName;

    /** 基金类型 */
    private String jjType;

    /** 金经理基 */
    private String jjManager;

    /** 基金最新涨值 */
    private String equityReturn;

    /** 基金最新涨值时间 */
    private String timeStr;

    /** 基金成立时间 */
    private Timestamp jjEstablishedTime;
}