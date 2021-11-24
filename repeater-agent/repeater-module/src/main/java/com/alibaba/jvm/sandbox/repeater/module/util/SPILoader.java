package com.alibaba.jvm.sandbox.repeater.module.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * {@link SPILoader} 加载spi
 * <p>
 *
 * @author zhaoyb1990
 */
@Slf4j
public class SPILoader {

    public static <T> List<T> loadSPI(Class<T> spiType, ClassLoader classLoader) {
        ServiceLoader<T> loaded = ServiceLoader.load(spiType, classLoader);
        Iterator<T> spiIterator = loaded.iterator();
        List<T> target = Lists.newArrayList();
        while (spiIterator.hasNext()) {
            try {
                target.add(spiIterator.next());
            } catch (Throwable e) {
                log.error("Error load spi {} >>> ", spiType.getCanonicalName(), e);
            }
        }
        return target;
    }
}
