package com.example.outsourcetoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.param.CommodityPageParam;

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
public interface CommodityMapper extends BaseMapper<Commodity> {


}
