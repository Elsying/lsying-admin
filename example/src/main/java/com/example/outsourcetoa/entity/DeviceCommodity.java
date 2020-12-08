package com.example.outsourcetoa.entity;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import io.geekidea.springbootplus.framework.core.validator.groups.Update;

/**
 *
 *
 * @author lsy
 * @since 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceCommodity对象")
public class DeviceCommodity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    @Null(message = "用户名不用传")
    private String username;

    @ApiModelProperty("设备编号")
    @NotBlank(message = "设备编号不能为空")
    private String did;

    @ApiModelProperty("设备名字")
    private String dname;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("创建时间")
    @Null(message = "创建时间不用传")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Null(message = "修改时间不用传")
    private Date updateTime;

    @ApiModelProperty("逻辑删除，0：未删除，1：已删除")
    @Null(message = "逻辑删除不用传")
    @TableLogic
    private Integer deleted;

}
