package io.github.wdpm.bean;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author evan
 * @date 2020/5/19
 */
@Lazy
@Component
public class LazyBean {

    public LazyBean() {
    }

    @PostConstruct
    public void onInit() {
        System.out.println("LazyBean is created.");
    }
}
