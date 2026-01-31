package io.github.wdpm.annotation.javac;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 利用javac的编译时注解处理。被处理的注解可以为@Retention(RetentionPolicy.SOURCE)
 *
 * <p>
 * fixme: doesn't work
 * <p>
 * usage:
 * javac -processor io.github.wdpm.annotation.javac.SimpleProcessor SimpleTest.java
 * </p>
 *
 * @author evan
 * @date 2020/5/2
 */
@SupportedAnnotationTypes("io.github.wdpm.annotation.javac.Simple")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SimpleProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement t : annotations)
            System.out.println(t);

        for (Element el : roundEnv.getElementsAnnotatedWith(Simple.class))
            display(el);

        return false;
    }

    private void display(Element el) {
        System.out.println("==== " + el + " ====");
        System.out.println(el.getKind() +
                " : " + el.getModifiers() +
                " : " + el.getSimpleName() +
                " : " + el.asType());
        if (el.getKind().equals(ElementKind.CLASS)) {
            TypeElement te = (TypeElement) el;
            System.out.println(te.getQualifiedName());
            System.out.println(te.getSuperclass());
            System.out.println(te.getEnclosedElements());
        }
        if (el.getKind().equals(ElementKind.METHOD)) {
            ExecutableElement ex = (ExecutableElement) el;
            System.out.print(ex.getReturnType() + " ");
            System.out.print(ex.getSimpleName() + "(");
            System.out.println(ex.getParameters() + ")");
        }
    }
}
