package com.example.outsourcetoa.service.impl;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.mapper.CommodityMapper;
import com.example.outsourcetoa.mapper.DeviceCommodityMapper;
import com.example.outsourcetoa.service.CommodityService;
import com.example.outsourcetoa.param.CommodityPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.outsourcetoa.service.DeviceCommodityService;
import com.example.outsourcetoa.vo.CommodityVo;
import io.geekidea.springbootplus.framework.common.exception.BusinessException;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 *
 * @author lsy
 * @since 2020-12-17
 */
@Slf4j
@Service
public class CommodityServiceImpl extends BaseServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private DeviceCommodityService deviceCommodityService;
    @Autowired
    private DeviceCommodityMapper deviceCommodityMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCommodity(Commodity commodity) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = Wrappers.<Commodity>lambdaQuery()
                .eq(Commodity::getDid, commodity.getDid())
                .eq(Commodity::getName, commodity.getName());
        // 校验用户编号是否存在
        if (this.list(wrapper).size()>0) {
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
        if (this.list(wrapper).size()>0){
            if(did.equals(commodity.getDid()))
            {
                throw new BusinessException("已存在该商品");
            }
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
        BeanUtils.copyProperties(commodity,commodity1);
        commodity1.setUpdateTime(new Date());
//        commodity1.setName(commodity.getName())
//                .setCode(commodity.getCode())
//                .setPrice(commodity.getPrice())
//                .setFirstCategory(commodity.getFirstCategory())
//                .setSecondaryCategory(commodity.getSecondaryCategory())
//                .setThreeCategory(commodity.getThreeCategory())
//                .setBrand(commodity.getBrand())
//                .setAddress(commodity.getAddress())
//                .setDeliveryMethod(commodity.getDeliveryMethod())
//                .setFreightPayer(commodity.getFreightPayer())
//                .setDeliveryDate(commodity.getDeliveryDate())
//                .setOnstate(commodity.getOnstate())
//                .setShelfStatus(commodity.getShelfStatus())
//                .setPiclist(commodity.getPiclist())
//                .setDescription(commodity.getDescription())
//                .setUpdateTime(new Date());
        // 校验用户编号是否存在
        if (this.list(wrapper).size()>0){
            if(!commodity.getId().equals(this.list(wrapper).get(0).getId())) {
                throw new BusinessException("商品名重复");
            }
        }
        return super.updateById(commodity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCommodity(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<CommodityVo> getCommodityPageList(CommodityPageParam commodityPageParam) throws Exception {
        Page<Commodity> page = new PageInfo<>(commodityPageParam, OrderItem.desc(getLambdaColumn(Commodity::getCreateTime)));
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        IPage<Commodity> iPage = commodityMapper.selectPage(page, wrapper);
        IPage<CommodityVo> newiPage=new Page<>();
        List<CommodityVo> newdc=new ArrayList<>();
        iPage.getRecords().forEach(item->{
            LambdaQueryWrapper<DeviceCommodity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(DeviceCommodity::getId, item.getDid());
            if(deviceCommodityMapper.selectOne(wrapper2)!=null){
                if(deviceCommodityMapper.selectOne(wrapper2).getDeleted().equals(0)){
                    CommodityVo commodityVo=new CommodityVo();
                    BeanUtils.copyProperties(item,commodityVo);
                    commodityVo.setUsername(deviceCommodityMapper.selectOne(wrapper2).getUsername());
                    commodityVo.setId(item.getId());
                    newdc.add(commodityVo);
                }
            }
        });
        BeanUtils.copyProperties(iPage,newiPage);
        newiPage.setRecords(newdc);
        return new Paging<CommodityVo>(newiPage);
    }

    @Override
    public Paging<CommodityVo> getCommodityPageListBydid(CommodityPageParam commodityPageParam,Integer did) throws Exception {
        Page<Commodity> page = new PageInfo<>(commodityPageParam, OrderItem.desc(getLambdaColumn(Commodity::getCreateTime)));
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Commodity::getDid, did);
        IPage<Commodity> iPage = commodityMapper.selectPage(page, wrapper);
        IPage<CommodityVo> newiPage=new Page<>();
        List<CommodityVo> newdc=new ArrayList<>();
        iPage.getRecords().forEach(item->{
            LambdaQueryWrapper<DeviceCommodity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(DeviceCommodity::getId, item.getDid());
            CommodityVo commodityVo=new CommodityVo();
            BeanUtils.copyProperties(item,commodityVo);
            commodityVo.setUsername(deviceCommodityMapper.selectOne(wrapper2).getUsername());
            commodityVo.setId(item.getId());
            newdc.add(commodityVo);
        });
        BeanUtils.copyProperties(iPage,newiPage);
        newiPage.setRecords(newdc);
        return new Paging<CommodityVo>(newiPage);
    }

    @Override
    public List<Commodity> getCommodityPageListBydnumber(String dnumber) throws Exception {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        DeviceCommodity deviceCommodity= deviceCommodityService.getDeviceCommodityBydid(dnumber);
        if(deviceCommodity==null){
            return null;
        }
        wrapper.eq(Commodity::getDid, deviceCommodity.getId());
        return this.list(wrapper);
    }


}
