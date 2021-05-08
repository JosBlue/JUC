package com.ltce.com.order;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/3/23 下午3:52
 * @description:
 */
@Data
public class NestedInteger {

    /**
     * 结果值
     */
    Integer val;

    /**
     * 列表值
     */
    List<NestedInteger> getList;


    public  boolean isInteger(NestedInteger info) {
        return Objects.nonNull(info.getVal());
    }


}
