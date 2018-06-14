package com.hunt.service.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * bean转换工具类
 * @author cwx
 *2017年10月23日
 */
@SuppressWarnings("all")
public class MyBeanUtils extends BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(MyBeanUtils.class);

    /**
     * list转换
     * @param sources 数据源list
     * @param Object 目标实体对象
     * @return
     */
    public static <T> List<T> copyPropertiesList(Collection<?> sources, Class clazz) {
        if (sources == null) {
            return null;
        } else {
            ArrayList targets = new ArrayList();
            Iterator arg2 = sources.iterator();

            while (arg2.hasNext()) {
                Object source = arg2.next();

                try {
                    Object t = clazz.newInstance();
                    copyProperties(source, t);
                    targets.add(t);
                } catch (Exception arg5) {
                    logger.error("", arg5);
                }
            }

            return targets;
        }
    }
    @SuppressWarnings("unchecked")
    public static <T, D> T populateTbyDBySpring(D sourceObj, Class<T> clazz)
    {
        if (sourceObj == null) {
            return null;
        }
        Object t = null;
        try {
            t = clazz.newInstance();
        } catch (IllegalAccessException e) {
            logger.error("自动转换失败", e);
        } catch (InstantiationException exx) {
            logger.error("自动转换失败", exx);
        }
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, t);

        return (T) t;

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T, D> List<T> populateTListbyDListBySpring(List<D> sourceObjs, Class<T> clazz)
    {
        if (sourceObjs == null) {
            return null;
        }
        int len = sourceObjs.size();
        List ts = new ArrayList(len);
        Object t = null;
        for (int i = 0; i < len; i++) {
            Object d = sourceObjs.get(i);
            t = populateTbyDBySpring(d, clazz);
            ts.add(t);
        }
        return ts;
    }

    /**
     * 将javaBean转换为bean
     * @param bean
     * @return
     */
    public static Map<String, Object> bean2map(Object bean) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                Method readMethod = propertyDescriptor.getReadMethod();
                if(null != readMethod){
                    Object invoke = readMethod.invoke(bean);
                    resultMap.put(name, invoke);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
            logger.error("java bean 转换map失败", e);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("", e);
        }
        return resultMap;
    }

    public static Map<String, Object> bean2mapExclude(Object bean, String ignoreProperties) {
        HashSet excludeSet = new HashSet();
        if (!StringUtils.isEmpty(ignoreProperties)) {
            excludeSet.addAll(Arrays.asList(ignoreProperties.split(",")));
        }

        HashMap result = new HashMap();
        Method[] methods = bean.getClass().getDeclaredMethods();
        Method[] arg4 = methods;
        int arg5 = methods.length;

        for (int arg6 = 0; arg6 < arg5; ++arg6) {
            Method method = arg4[arg6];

            try {
                if (method.getName().startsWith("get")) {
                    String e = method.getName();
                    if (e.startsWith("get") && e.length() >= 4) {
                        String field = e.substring(3);
                        if (e.length() > 4) {
                            field = field.substring(0, 1).toLowerCase() + field.substring(1);
                        } else {
                            field = field.substring(0, 1).toLowerCase();
                        }

                        if (!ignoreProperties.contains(field)) {
                            Object value = method.invoke(bean, (Object[]) null);
                            if (value != null) {
                                result.put(field, value.toString());
                            }
                        }
                    }
                }
            } catch (Exception arg11) {
                logger.error("", arg11);
            }
        }

        return result;
    }
}
