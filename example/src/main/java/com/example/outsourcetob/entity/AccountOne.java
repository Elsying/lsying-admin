package com.example.outsourcetob.entity;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;

/**
 * 
 *
 * @author lsy
 * @since 2021-01-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOne对象")
public class AccountOne extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("账号")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("设备类型")
    private String device;

    @NotBlank(message = "区服不能为空")
    @ApiModelProperty("区服")
    private String server;

    @ApiModelProperty("武将")
    private String hero;

    @ApiModelProperty("装备")
    private String equipment;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @NotNull(message = "修改时间不能为空")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("状态，0：禁用，1：启用，2：锁定")
    private Integer state;

}
