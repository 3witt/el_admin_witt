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
 * @author witt
 * @website https://el-admin.vip
 * @description /
 * @date 2020-08-05
 **/
@Entity
@Data
@Table(name = "jj_data", uniqueConstraints = {
        @UniqueConstraint(name = "one_dm", columnNames = {"jj_code"})
})
public class JjData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "jj_code", unique = true, nullable = false)
    @NotBlank
    @ApiModelProperty(value = "基金代码")
    private String jjCode;

    @Column(name = "jj_name", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "基金名称")
    private String jjName;

    @Column(name = "jj_type", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "基金类型")
    private String jjType;

    @Column(name = "jj_manager")
    @ApiModelProperty(value = "金经理基")
    private String jjManager;

    @Column(name = "equity_return")
    @ApiModelProperty(value = "基金最新涨值")
    private String equityReturn;

    @Column(name = "time_str")
    @ApiModelProperty(value = "基金最新涨值时间")
    private String timeStr;

    @Column(name = "jj_established_time")
    @ApiModelProperty(value = "基金成立时间")
    private Timestamp jjEstablishedTime;

    public void copy(JjData source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
