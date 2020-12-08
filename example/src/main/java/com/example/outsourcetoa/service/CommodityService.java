package com.example.outsourcetoa.service;

import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.param.CommodityPageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

import java.util.List;

/**
 *  服务类
 *
 * @author lsy
 * @since 2020-11-30
 */
public interface CommodityService extends BaseService<Commodity> {

    /**
     * 保存
     *
     * @param commodity
     * @return
     * @throws Exception
     */
    boolean saveCommodity(Commodity commodity) throws Exception;

    /**
     * 复制
     *
     * @param commodity
     * @return
     * @throws Exception
     */
    public boolean copyCommodity(Commodity commodity,Integer did) throws Exception;

    /**
     * 修改
     *
     * @param commodity
     * @return
     * @throws Exception
     */
    boolean updateCommodity(Commodity commodity) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCommodity(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param commodityPageParam
     * @return
     * @throws Exception
     */
    Paging<Commodity> getCommodityPageList(CommodityPageParam commodityPageParam) throws Exception;


    /**
     * 获取分页对象 根据did
     *
     * @param commodityPageParam
     * @return
     * @throws Exception
     */
    Paging<Commodity> getCommodityPageListBydid(CommodityPageParam commodityPageParam,Integer did) throws Exception;

    /**
     * 根据设备编号获取所有商品
     *
     * @param dnumber
     * @return
     * @throws Exception
     */
     List<Commodity> getCommodityPageListBydnumber(String dnumber) throws Exception;
}
