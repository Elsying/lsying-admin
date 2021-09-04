package com.example.outsourcetob.service.impl;

import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetob.entity.AccountOne;
import com.example.outsourcetob.entity.AccountOneRequest;
import com.example.outsourcetob.entity.AccountOneResponse;
import com.example.outsourcetob.mapper.AccountOneMapper;
import com.example.outsourcetob.service.AccountOneService;
import com.example.outsourcetob.param.AccountOnePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.geekidea.springbootplus.framework.common.service.impl.BaseServiceImpl;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.core.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.geekidea.springbootplus.framework.util.Jackson;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 *
 * @author lsy
 * @since 2021-01-04
 */
@Slf4j
@Service
public class AccountOneServiceImpl extends BaseServiceImpl<AccountOneMapper, AccountOne> implements AccountOneService {

    @Autowired
    private AccountOneMapper accountOneMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAccountOne(AccountOneRequest accountOneRequest) throws Exception {
        AccountOne accountOne=new AccountOne();
        BeanUtils.copyProperties(accountOneRequest,accountOne);
        accountOne.setDevice(Jackson.toJsonStringNonNull(accountOneRequest.getDevice()));
        accountOne.setUpdateTime(new Date());
        return super.save(accountOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAccountOne(AccountOne accountOne) throws Exception {
        return super.updateById(accountOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAccountOne(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<AccountOne> getAccountOnePageList(AccountOnePageParam accountOnePageParam) throws Exception {
        Page<AccountOne> page = new PageInfo<>(accountOnePageParam, OrderItem.desc(getLambdaColumn(AccountOne::getCreateTime)));
        LambdaQueryWrapper<AccountOne> wrapper = new LambdaQueryWrapper<>();
        IPage<AccountOne> iPage = accountOneMapper.selectPage(page, wrapper);
        return new Paging<AccountOne>(iPage);
    }

    @Override
    public AccountOneResponse getBydid(String id) throws Exception {
        AccountOne accountOne=accountOneMapper.selectById(id);
        AccountOneResponse accountOneResponse=new AccountOneResponse();
        BeanUtils.copyProperties(accountOne,accountOneResponse);
        List<String> javaType = Jackson.jsonToList(accountOne.getDevice(), String.class);
        accountOneResponse.setDevice(javaType);
        return accountOneResponse;
    }

}
