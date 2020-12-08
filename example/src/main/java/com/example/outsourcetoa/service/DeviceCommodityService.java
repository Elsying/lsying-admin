package com.example.outsourcetoa.service;

import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.param.DeviceCommodityPageParam;
import com.example.outsourcetoa.vo.DeviceCommodityVo;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author lsy
 * @since 2020-11-30
 */
public interface DeviceCommodityService extends BaseService<DeviceCommodity> {

    /**
     * 保存
     *
     * @param deviceCommodity
     * @return
     * @throws Exception
     */
    boolean saveDeviceCommodity(DeviceCommodity deviceCommodity) throws Exception;

    /**
     * 修改
     *
     * @param deviceCommodity
     * @return
     * @throws Exception
     */
    boolean updateDeviceCommodity(DeviceCommodity deviceCommodity) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteDeviceCommodity(Long id) throws Exception;


    /**
     * 获取分页对象-根据用户
     *
     * @param deviceCommodityQueryParam
     * @return
     * @throws Exception
     */
    Paging<DeviceCommodityVo> getDeviceCommodityPageListByuser(DeviceCommodityPageParam deviceCommodityPageParam) throws Exception;


    /**
     * 获取分页对象-所有
     *
     * @param deviceCommodityQueryParam
     * @return
     * @throws Exception
     */
    Paging<DeviceCommodityVo> getDeviceCommodityPageList(DeviceCommodityPageParam deviceCommodityPageParam) throws Exception;


    /**
     * 根据设备编号获取设备
     *
     * @param did
     * @return
     * @throws Exception
     */
     DeviceCommodity getDeviceCommodityBydid(String did) throws Exception;

}
