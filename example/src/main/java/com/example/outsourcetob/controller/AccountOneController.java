package com.example.outsourcetob.controller;

import com.example.outsourcetob.entity.AccountOne;
import com.example.outsourcetob.entity.AccountOneRequest;
import com.example.outsourcetob.entity.AccountOneResponse;
import com.example.outsourcetob.service.AccountOneService;
import io.geekidea.springbootplus.framework.util.Jackson;
import lombok.extern.slf4j.Slf4j;
import com.example.outsourcetob.param.AccountOnePageParam;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.common.param.IdParam;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制器
 *
 * @author lsy
 * @since 2021-01-04
 */
@Slf4j
@RestController
@RequestMapping("/accountOne")
@Module("outsourcetob")
@Api(value = "API", tags = {"外包b账号管理"})
public class AccountOneController extends BaseController {

    @Autowired
    private AccountOneService accountOneService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addAccountOne(@Validated(Add.class) @RequestBody AccountOneRequest accountOneRequest) throws Exception {
        boolean flag = accountOneService.saveAccountOne(accountOneRequest);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateAccountOne(@Validated(Update.class) @RequestBody AccountOne accountOne) throws Exception {
        boolean flag = accountOneService.updateAccountOne(accountOne);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteAccountOne(@PathVariable("id") Long id) throws Exception {
        boolean flag = accountOneService.deleteAccountOne(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = AccountOne.class)
    public ApiResult<AccountOneResponse> getAccountOne(@PathVariable("id") Long id) throws Exception {
//        AccountOne accountOne = accountOneService.getById(id);
        AccountOne accountOne=accountOneService.getById(id);
        AccountOneResponse accountOneResponse=new AccountOneResponse();
        BeanUtils.copyProperties(accountOne,accountOneResponse);
        List<String> javaType = Jackson.jsonToList(accountOne.getDevice(), String.class);
        accountOneResponse.setDevice(javaType);
        return ApiResult.ok(accountOneResponse);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @OperationLog(name = "分页列表", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表", response = AccountOne.class)
    public ApiResult<Paging<AccountOne>> getAccountOnePageList(@Validated @RequestBody AccountOnePageParam accountOnePageParam) throws Exception {
        Paging<AccountOne> paging = accountOneService.getAccountOnePageList(accountOnePageParam);
        return ApiResult.ok(paging);
    }

}

