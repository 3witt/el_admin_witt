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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author witt
* @date 2020-08-07
**/
@Entity
@Data
@Table(name="jj_latest_net_worth")
public class JjLatestNetWorth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "jj_code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "基金代码")
    private String jjCode;

    @Column(name = "x")
    @ApiModelProperty(value = "时间戳")
    private String x;

    @Column(name = "x_time")
    @ApiModelProperty(value = "时间代表时间")
    private Timestamp xTime;

    @Column(name = "y")
    @ApiModelProperty(value = "净值金额")
    private String y;

    @Column(name = "equity_return")
    @ApiModelProperty(value = "增长百分比")
    private String equityReturn;

    @Column(name = "created_time")
    @ApiModelProperty(value = "createdTime")
    private Timestamp createdTime;

    @Column(name = "unit_money")
    @ApiModelProperty(value = "unitMoney")
    private String unitMoney;

    public void copy(JjLatestNetWorth source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}