package ru.mikheev.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mikheev.services.LazyBean;

@Slf4j
@Lazy
@Component
public class LazyBeanImpl implements LazyBean {

    public LazyBeanImpl() {
        log.info("Lazy initialized");
    }

    @Override
    public void print() {
        log.info("Lazy called");
    }
}
