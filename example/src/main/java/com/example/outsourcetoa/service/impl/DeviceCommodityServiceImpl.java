package com.example.outsourcetoa.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.mapper.CommodityMapper;
import com.example.outsourcetoa.mapper.DeviceCommodityMapper;
import com.example.outsourcetoa.service.DeviceCommodityService;
import com.example.outsourcetoa.param.DeviceCommodityPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.outsourcetoa.vo.DeviceCommodityVo;
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
 * @since 2020-11-30
 */
@Slf4j
@Service
public class DeviceCommodityServiceImpl extends BaseServiceImpl<DeviceCommodityMapper, DeviceCommodity> implements DeviceCommodityService {

    @Autowired
    private DeviceCommodityMapper deviceCommodityMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveDeviceCommodity(DeviceCommodity deviceCommodity) throws Exception {
        LambdaQueryWrapper<DeviceCommodity> wrapper = Wrappers.<DeviceCommodity>lambdaQuery()
                .eq(DeviceCommodity::getDid, deviceCommodity.getDid())
                .eq(DeviceCommodity::getUsername, JwtUtil.getUsername(JwtTokenUtil.getToken()));
        // 校验用户编号是否存在
        if (this.getOne(wrapper)!=null) {
            throw new BusinessException("用户编号已存在");
        }
        deviceCommodity.setUsername(JwtUtil.getUsername(JwtTokenUtil.getToken()));
        return super.save(deviceCommodity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDeviceCommodity(DeviceCommodity deviceCommodity) throws Exception {
        LambdaQueryWrapper<DeviceCommodity> wrapperw = Wrappers.<DeviceCommodity>lambdaQuery()
                .eq(DeviceCommodity::getId, deviceCommodity.getId());
        DeviceCommodity deviceCommodity1= this.getOne(wrapperw);
        if (deviceCommodity1 == null) {
            throw new BusinessException("修改的设备不存在");
        }
        deviceCommodity1.setDid(deviceCommodity.getDid())
                .setDname(deviceCommodity.getDname())
                .setRemarks(deviceCommodity.getRemarks())
                .setUpdateTime(new Date());
        LambdaQueryWrapper<DeviceCommodity> wrapper = Wrappers.<DeviceCommodity>lambdaQuery()
                .eq(DeviceCommodity::getDid, deviceCommodity.getDid())
                .eq(DeviceCommodity::getUsername, JwtUtil.getUsername(JwtTokenUtil.getToken()));
        // 校验用户编号是否存在
        if (this.getOne(wrapper)!=null&& !deviceCommodity.getId().equals(this.getOne(wrapper).getId())) {
            throw new BusinessException("设备编号已存在");
        }
        return super.updateById(deviceCommodity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDeviceCommodity(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public Paging<DeviceCommodityVo> getDeviceCommodityPageListByuser(DeviceCommodityPageParam deviceCommodityPageParam) throws Exception {
        Page<DeviceCommodity> page = new PageInfo<>(deviceCommodityPageParam, OrderItem.desc(getLambdaColumn(DeviceCommodity::getCreateTime)));
        LambdaQueryWrapper<DeviceCommodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceCommodity::getUsername, JwtUtil.getUsername(JwtTokenUtil.getToken()));
        IPage<DeviceCommodity> iPage = deviceCommodityMapper.selectPage(page, wrapper);
        IPage<DeviceCommodityVo> newiPage=new Page<>();
        List<DeviceCommodityVo>newdc=new ArrayList<>();
        iPage.getRecords().forEach(item->{
            LambdaQueryWrapper<Commodity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(Commodity::getDid, item.getId());
            DeviceCommodityVo deviceCommodityVo=new DeviceCommodityVo();
            BeanUtils.copyProperties(item,deviceCommodityVo);
            deviceCommodityVo.setCommoditynum(commodityMapper.selectCount(wrapper2));
            newdc.add(deviceCommodityVo);
        });
        newiPage.setRecords(newdc);
        newiPage.setTotal(iPage.getTotal());
        newiPage.setCurrent(iPage.getCurrent());
        newiPage.setSize(iPage.getSize());
        return new Paging<DeviceCommodityVo>(newiPage);
    }

    @Override
    public Paging<DeviceCommodityVo> getDeviceCommodityPageList(DeviceCommodityPageParam deviceCommodityPageParam) throws Exception {
        Page<DeviceCommodity> page = new PageInfo<>(deviceCommodityPageParam, OrderItem.desc(getLambdaColumn(DeviceCommodity::getCreateTime)));
        LambdaQueryWrapper<DeviceCommodity> wrapper = new LambdaQueryWrapper<>();
        IPage<DeviceCommodity> iPage = deviceCommodityMapper.selectPage(page, wrapper);
        IPage<DeviceCommodityVo> newiPage=new Page<>();
        List<DeviceCommodityVo>newdc=new ArrayList<>();
        iPage.getRecords().forEach(item->{
            LambdaQueryWrapper<Commodity> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(Commodity::getDid, item.getId());
            DeviceCommodityVo deviceCommodityVo=new DeviceCommodityVo();
            BeanUtils.copyProperties(item,deviceCommodityVo);
            deviceCommodityVo.setCommoditynum(commodityMapper.selectCount(wrapper2));
            newdc.add(deviceCommodityVo);
        });
        newiPage.setRecords(newdc);
        newiPage.setTotal(iPage.getTotal());
        newiPage.setCurrent(iPage.getCurrent());
        newiPage.setSize(iPage.getSize());
        return new Paging<DeviceCommodityVo>(newiPage);
    }

    @Override
    public DeviceCommodity getDeviceCommodityBydid(String did) throws Exception {
        LambdaQueryWrapper<DeviceCommodity> wrapperdev = Wrappers.<DeviceCommodity>lambdaQuery()
                .eq(DeviceCommodity::getDid, did);
        return this.getOne(wrapperdev);
    }

    @Override
    public DeviceCommodity getDeviceCommodityByid(Integer id) throws Exception {
        LambdaQueryWrapper<DeviceCommodity> wrapperdev = Wrappers.<DeviceCommodity>lambdaQuery()
                .eq(DeviceCommodity::getId, id);
        return this.getOne(wrapperdev);
    }


}
