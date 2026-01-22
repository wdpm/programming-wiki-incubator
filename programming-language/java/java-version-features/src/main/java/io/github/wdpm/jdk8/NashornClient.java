package io.github.wdpm.jdk8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Nashorn JavaScript引擎：在JVM上开发和运行JS应用
 * @author evan
 * @since 2020/4/19
 */
public class NashornClient {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine        engine  = manager.getEngineByName("JavaScript");

        System.out.println(engine.getClass()
                                 .getName());
        System.out.println("Result: " + engine.eval("function f() { return -123; }; f();"));
    }
}
