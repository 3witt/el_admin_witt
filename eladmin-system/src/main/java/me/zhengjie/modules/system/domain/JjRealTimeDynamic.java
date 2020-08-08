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
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author witt
* @date 2020-08-06
**/
@Entity
@Data
@Table(name="jj_real_time_dynamic")
public class JjRealTimeDynamic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "fundcode")
    @ApiModelProperty(value = "基金代码")
    private String fundcode;

    @Column(name = "gztime")
    @ApiModelProperty(value = "更新时间")
    private String gztime;

    @Column(name = "gszzl")
    @ApiModelProperty(value = "最新估值")
    private String gszzl;

    @Column(name = "gsz")
    @ApiModelProperty(value = "最新值")
    private String gsz;

    @Column(name = "dwjz")
    @ApiModelProperty(value = "昨日净值")
    private String dwjz;

    @Column(name = "jzrq")
    @ApiModelProperty(value = "净值日期")
    private String jzrq;

    @Column(name = "name")
    @ApiModelProperty(value = "基金名称")
    private String name;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    public void copy(JjRealTimeDynamic source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}