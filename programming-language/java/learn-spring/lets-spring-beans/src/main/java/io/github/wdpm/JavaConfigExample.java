package io.github.wdpm;

import io.github.wdpm.bean.Bar;
import io.github.wdpm.bean.LazyBean;
import io.github.wdpm.config.BeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author evan
 * @date 2020/5/19
 */
public class JavaConfigExample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BeanConfig.class);
        ctx.refresh();

        Bar bar = ctx.getBean(Bar.class);
        System.out.println("is Foo initialized? " + bar.isFooInitialized());
        bar.say();
        bar.getFoo().say();

        //test lazy bean
        LazyBean lazyBean = ctx.getBean(LazyBean.class);

        ctx.close();
    }
}
