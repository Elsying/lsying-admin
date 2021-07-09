package com.example.outsourcetob.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.outsourcetob.entity.AccountOne;
import com.example.outsourcetob.param.AccountOnePageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author lsy
 * @since 2021-01-04
 */
@Repository
public interface AccountOneMapper extends BaseMapper<AccountOne> {


}
