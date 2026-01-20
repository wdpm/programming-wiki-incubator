package io.github.wdpm;

import io.github.wdpm.bean.Foo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author evan
 * @date 2020/5/19
 */
public class XmlConfigExample {

    public static final String CONTEXT_PATH = "beans.xml";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT_PATH);
        Foo                            foo     = context.getBean(Foo.class);
        foo.say();
    }
}
