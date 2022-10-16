package ru.mikheev.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mikheev.services.LazyBean;
import ru.mikheev.services.NotLazyCaller;

@Slf4j
@Component
public class NotLazyCallerImpl implements NotLazyCaller {

    private final LazyBean lazyBean;

    //@Lazy должно быть и в компоненте и в том месте где используется, тогда прокинется прокси
    public NotLazyCallerImpl(@Lazy LazyBean lazyBean) {
        this.lazyBean = lazyBean;
    }

    @Override
    public void callLazy() {
        log.info("Calling lazy");
        lazyBean.print();
    }
}
