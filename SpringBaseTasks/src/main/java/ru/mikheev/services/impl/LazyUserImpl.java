package ru.mikheev.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mikheev.services.LazyBean;
import ru.mikheev.services.LazyUser;

@Slf4j
@Component
public class LazyUserImpl implements LazyUser {

    public final LazyBean lazyBean;

    public LazyUserImpl(@Lazy LazyBean lazyBean) {
        this.lazyBean = lazyBean;
    }

    @Override
    public void callLazy() {
        log.info("Calling lazy");
        lazyBean.print();
    }
}
