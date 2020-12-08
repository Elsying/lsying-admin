package com.example.outsourcetoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.outsourcetoa.entity.DeviceCommodity;
import com.example.outsourcetoa.param.DeviceCommodityPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author lsy
 * @since 2020-11-30
 */
@Repository
public interface DeviceCommodityMapper extends BaseMapper<DeviceCommodity> {


}
