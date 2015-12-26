package org.ryu1.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RflectionサポートUtilクラス.
 * 
 * @author R.Ishitsuka
 * 
 */
public final class ReflectionUtil {

    /** logger. */
    private static Logger logger = LoggerFactory
            .getLogger(ReflectionUtil.class);

    /**
     * デフォルトコンストラクタの禁止.
     */
    private ReflectionUtil() {
    }

    /**
     * Getterメソッドの返却.
     * 
     * @param propertyName
     *        Getterメソッドの対象となるフィールド
     * @param object
     *        対象のオブジェクト
     * @return Getterメソッド
     * @throws IntrospectionException
     *         IntrospectionException
     */
    public static Method getGetterMethod(final String propertyName,
            final Object object) throws IntrospectionException {
        // nullチェック
        if (StringUtils.isBlank(propertyName)) {
            logger.error("getGetterMethod called. but,"
                    + " propertyName is nothing.");
            throw new InvalidParameterException("getGetterMethod called. but,"
                    + " propertyName is nothing.");
        }
        if (object == null) {
            logger.error("getGetterMethod called. but,"
                    + " target object is nothing.");
            throw new InvalidParameterException("getGetterMethod called. but,"
                    + " target object is nothing.");
        }

        PropertyDescriptor propertyDescriptor;
        propertyDescriptor =
                new PropertyDescriptor(propertyName, object.getClass());

        return propertyDescriptor.getReadMethod();
    }

    /**
     * Setterメソッドの返却.
     * 
     * @param propertyName
     *        Setterメソッドの対象となるフィールド
     * @param object
     *        対象のオブジェクト
     * @return Setterメソッド
     * @throws IntrospectionException
     *         IntrospectionException
     */
    public static Method getSetterMethod(final String propertyName,
            final Object object) throws IntrospectionException {
        // nullチェック
        if (StringUtils.isBlank(propertyName)) {
            logger.error("getSetterMethod called. but,"
                    + " propertyName is nothing.");
            throw new InvalidParameterException("getSetterMethod called. but,"
                    + " propertyName is nothing.");
        }
        if (object == null) {
            logger.error("getSetterMethod called. but,"
                    + " target object is nothing.");
            throw new InvalidParameterException("getSetterMethod called. but,"
                    + " target object is nothing.");
        }

        PropertyDescriptor propertyDescriptor;
        propertyDescriptor =
                new PropertyDescriptor(propertyName, object.getClass());
        return propertyDescriptor.getWriteMethod();
    }
}
