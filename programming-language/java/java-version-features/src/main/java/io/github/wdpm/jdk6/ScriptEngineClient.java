package io.github.wdpm.jdk6;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * 对脚本语言的支持：JavaScript为例
 *
 * @author evan
 * @since 2020/4/19
 */
public class ScriptEngineClient {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine        engine  = manager.getEngineByName("ECMAScript");
        try {
            String jsPath = ScriptEngineClient.class.getResource("/test.js")
                                                    .getPath();
            engine.eval(new FileReader(jsPath));//加载
            Object ret = ((Invocable) engine).invokeFunction("test", (Object[]) null);//执行test()
            System.out.println("The result is : " + ret);//123.0
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
