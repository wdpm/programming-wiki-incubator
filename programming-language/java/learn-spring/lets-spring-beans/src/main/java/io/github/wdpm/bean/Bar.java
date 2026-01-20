package io.github.wdpm.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author evan
 * @date 2020/5/19
 */
@Component
public class Bar {

    @Autowired
    private Foo foo;

    public void say() {
        System.out.println("Bar");
    }

    public Foo getFoo() {
        return foo;
    }

    public boolean isFooInitialized() {
        return foo != null;
    }

}
