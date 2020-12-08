package com.example.outsourcetoa.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.geekidea.springbootplus.framework.core.pagination.BasePageOrderParam;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author lsy
 * @date 2020-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class DeviceCommodityPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
