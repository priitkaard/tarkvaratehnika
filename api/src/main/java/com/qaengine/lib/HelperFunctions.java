package com.qaengine.lib;

import com.qaengine.exceptions.InternalServerErrorException;
import org.apache.commons.beanutils.BeanUtilsBean;

public class HelperFunctions {
    private static BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();

    public static void copyProperties(Object destination, Object origin) {
        try {
            beanUtils.copyProperties(destination, origin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
