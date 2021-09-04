package com.example.outsourcetob.service;

import com.example.outsourcetob.entity.AccountOne;
import com.example.outsourcetob.entity.AccountOneRequest;
import com.example.outsourcetob.entity.AccountOneResponse;
import com.example.outsourcetob.param.AccountOnePageParam;
import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;

/**
 *  服务类
 *
 * @author lsy
 * @since 2021-01-04
 */
public interface AccountOneService extends BaseService<AccountOne> {

    /**
     * 保存
     *
     * @param accountOne
     * @return
     * @throws Exception
     */
    boolean saveAccountOne(AccountOneRequest accountOneRequest) throws Exception;

    /**
     * 修改
     *
     * @param accountOne
     * @return
     * @throws Exception
     */
    boolean updateAccountOne(AccountOne accountOne) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAccountOne(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param accountOneQueryParam
     * @return
     * @throws Exception
     */
    Paging<AccountOne> getAccountOnePageList(AccountOnePageParam accountOnePageParam) throws Exception;


    AccountOneResponse getBydid(String id) throws Exception;

}
