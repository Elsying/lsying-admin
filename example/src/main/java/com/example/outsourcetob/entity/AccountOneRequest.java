package com.example.outsourcetob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class AccountOneRequest {


    private String account;

    private String password;

    private List<String> device;

    private String server;

    private String hero;

    private String equipment;

    private Integer state;
}
