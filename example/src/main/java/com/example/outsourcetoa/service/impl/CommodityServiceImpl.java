package com.example.outsourcetoa.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.mapper.CommodityMapper;
import com.example.outsourcetoa.service.CommodityService;
import com.example.outsourcetoa.param.CommodityPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.outsourcetoa.service.DeviceCommodityService;
import io.geekidea.springbootplus.framework.common.exception.BusinessException;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.geekidea.springbootplus.framework.shiro.util.JwtTokenUtil;
import io.geekidea.springbootplus.framework.shiro.util.JwtUtil;
import io.geekidea.springbootplus.system.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 *
 * @author lsy
 * @since 2020-11-30
 */
@Slf4j
@Service
public class CommodityServiceImpl extends BaseServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private DeviceCommodityService deviceCommodityService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCommodity(Commodity commodity) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = Wrappers.<Commodity>lambdaQuery()
                .eq(Commodity::getDid, commodity.getDid())
                .eq(Commodity::getName, commodity.getName());
        // 校验用户编号是否存在
        if (this.getOne(wrapper)!=null) {
            throw new BusinessException("当前设备下已存在该商品");
        }
        return super.save(commodity);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean copyCommodity(Commodity commodity,Integer did) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = Wrappers.<Commodity>lambdaQuery()
                .eq(Commodity::getDid, did)
                .eq(Commodity::getName, commodity.getName());
        // 校验用户编号是否存在
        if (this.getOne(wrapper)!=null&&did.equals(commodity.getDid())) {
            throw new BusinessException("已存在该商品");
        }
        commodity.setDid(did);
        return super.save(commodity);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCommodity(Commodity commodity) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = Wrappers.<Commodity>lambdaQuery()
                .eq(Commodity::getDid, commodity.getDid())
                .eq(Commodity::getName, commodity.getName());

        LambdaQueryWrapper<Commodity> wrappers = Wrappers.<Commodity>lambdaQuery()
                .eq(Commodity::getId, commodity.getId());
        Commodity commodity1= this.getOne(wrappers);
        if (commodity1 == null) {
            throw new BusinessException("修改的商品不存在");
        }
        commodity1.setName(commodity.getName())
                .setCode(commodity.getCode())
                .setPrice(commodity.getPrice())
                .setFirstCategory(commodity.getFirstCategory())
                .setSecondaryCategory(commodity.getSecondaryCategory())
                .setThreeCategory(commodity.getThreeCategory())
                .setBrand(commodity.getBrand())
                .setAddress(commodity.getAddress())
                .setDeliveryMethod(commodity.getDeliveryMethod())
                .setFreightPayer(commodity.getFreightPayer())
                .setDeliveryDate(commodity.getDeliveryDate())
                .setOnstate(commodity.getOnstate())
                .setShelfStatus(commodity.getShelfStatus())
                .setPiclist(commodity.getPiclist())
                .setDescription(commodity.getDescription())
                .setUpdateTime(new Date());
        // 校验用户编号是否存在
        if (this.getOne(wrapper)!=null&& !commodity.getId().equals(this.getOne(wrapper).getId())) {
            throw new BusinessException("商品名重复");
        }
        return super.updateById(commodity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCommodity(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Commodity> getCommodityPageList(CommodityPageParam commodityPageParam) throws Exception {
        Page<Commodity> page = new PageInfo<>(commodityPageParam, OrderItem.desc(getLambdaColumn(Commodity::getCreateTime)));
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        IPage<Commodity> iPage = commodityMapper.selectPage(page, wrapper);
        return new Paging<Commodity>(iPage);
    }

    @Override
    public Paging<Commodity> getCommodityPageListBydid(CommodityPageParam commodityPageParam,Integer did) throws Exception {
        Page<Commodity> page = new PageInfo<>(commodityPageParam, OrderItem.desc(getLambdaColumn(Commodity::getCreateTime)));
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Commodity::getDid, did);
        IPage<Commodity> iPage = commodityMapper.selectPage(page, wrapper);
        return new Paging<Commodity>(iPage);
    }

    @Override
    public List<Commodity> getCommodityPageListBydnumber(String dnumber) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Commodity::getDid, deviceCommodityService.getDeviceCommodityBydid(dnumber).getId());
        return this.list(wrapper);
    }

}
