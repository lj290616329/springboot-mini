package com.tsingtec.mini.utils;

import com.github.dozermapper.core.Mapper;
import com.vip.vjtools.vjkit.collection.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现深度的BeanOfClasssA<->BeanOfClassB复制
 *
 * 使用 Dozer最新版6.5.0，dozermapper
 *
 * 可复制JDK8后的数据类型，如LocalDateTime
 */
@Component
public class BeanMapper {

//    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private static Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        BeanMapper.mapper = mapper;
    }

    /**
     * 简单的复制出新类型对象.
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
     * 简单的复制出新对象ArrayList
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
        List<D> destionationList = new ArrayList<D>();
        for (S source : sourceList) {
            if (source != null) {
                destionationList.add(mapper.map(source, destinationClass));
            }
        }
        return destionationList;
    }

    /**
     * 简单复制出新对象数组
     */
    public static <S, D> D[] mapArray(final S[] sourceArray, final Class<D> destinationClass) {
        D[] destinationArray = ArrayUtil.newArray(destinationClass, sourceArray.length);

        int i = 0;
        for (S source : sourceArray) {
            if (source != null) {
                destinationArray[i] = mapper.map(sourceArray[i], destinationClass);
                i++;
            }
        }

        return destinationArray;
    }
}
