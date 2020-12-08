package com.example.outsourcetoa.controller;

import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.service.DeviceCommodityService;
import com.example.outsourcetoa.vo.DeviceCommodityVo;
import lombok.extern.slf4j.Slf4j;
import com.example.outsourcetoa.param.DeviceCommodityPageParam;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.common.param.IdParam;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  控制器
 *
 * @author lsy
 * @since 2020-11-30
 */
@Slf4j
@RestController
@RequestMapping("/deviceCommodity")
@Module("outsourcetoa")
@Api(value = "API", tags = {"设备管理"})
public class DeviceCommodityController extends BaseController {

    @Autowired
    private DeviceCommodityService deviceCommodityService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addDeviceCommodity(@Validated(Add.class) @RequestBody DeviceCommodity deviceCommodity) throws Exception {
        boolean flag = deviceCommodityService.saveDeviceCommodity(deviceCommodity);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateDeviceCommodity(@Validated(Update.class) @RequestBody DeviceCommodity deviceCommodity) throws Exception {
        boolean flag = deviceCommodityService.updateDeviceCommodity(deviceCommodity);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteDeviceCommodity(@PathVariable("id") Long id) throws Exception {
        boolean flag = deviceCommodityService.deleteDeviceCommodity(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = DeviceCommodity.class)
    public ApiResult<DeviceCommodity> getDeviceCommodity(@PathVariable("id") Long id) throws Exception {
        DeviceCommodity deviceCommodity = deviceCommodityService.getById(id);
        return ApiResult.ok(deviceCommodity);
    }

    /**
     * 分页列表-当前用户设备
     */
    @PostMapping("/getPageListByuser")
    @OperationLog(name = "分页列表-当前用户设备", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表-当前用户设备", response = DeviceCommodity.class)
    public ApiResult<Paging<DeviceCommodityVo>> getDeviceCommodityPageListByuse(@Validated @RequestBody DeviceCommodityPageParam deviceCommodityPageParam) throws Exception {
        Paging<DeviceCommodityVo> paging = deviceCommodityService.getDeviceCommodityPageListByuser(deviceCommodityPageParam);
        return ApiResult.ok(paging);
    }


    /**
     * 分页列表-所有
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("admin:device:page")
    @OperationLog(name = "分页列表-所有", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表-所有", response = DeviceCommodity.class)
    public ApiResult<Paging<DeviceCommodityVo>> getDeviceCommodityPageList(@Validated @RequestBody DeviceCommodityPageParam deviceCommodityPageParam) throws Exception {
        Paging<DeviceCommodityVo> paging = deviceCommodityService.getDeviceCommodityPageList(deviceCommodityPageParam);
        return ApiResult.ok(paging);
    }

}

