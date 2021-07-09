package com.example.outsourcetoa.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.geekidea.springbootplus.framework.util.JsonSerializeUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author lsy
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CommodityVo对象")
public class CommodityVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("设备id")
    private Integer did;

    @ApiModelProperty("用户名")
    private String username;

    @JsonSerialize(using = JsonSerializeUtils.class)
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品编码")
    private String code;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("一级类目")
    private String firstCategory;

    @ApiModelProperty("二级类目")
    private String secondaryCategory;

    @ApiModelProperty("三级类目")
    private String threeCategory;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("发货地址")
    private String address;

    @ApiModelProperty("发货方式")
    private String deliveryMethod;

    @ApiModelProperty("运费承担人")
    private String freightPayer;

    @ApiModelProperty("发货日期")
    private String deliveryDate;

    @ApiModelProperty("新旧状态")
    private String onstate;

    @ApiModelProperty("上架状态 0：未上架，1：已上架")
    private Integer shelfStatus;

    @ApiModelProperty("图片url数组")
    private String piclist;

    @ApiModelProperty("距上方随机数开始")
    private Integer upstart;

    @ApiModelProperty("距上方随机数结束")
    private Integer upend;

    @ApiModelProperty("距左方随机数开始")
    private Integer leftstart;

    @ApiModelProperty("距左方随机数结束")
    private Integer leftend;

    @JsonSerialize(using = JsonSerializeUtils.class)
    @ApiModelProperty("商品说明")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
