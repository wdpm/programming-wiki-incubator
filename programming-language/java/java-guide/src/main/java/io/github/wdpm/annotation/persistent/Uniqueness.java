package io.github.wdpm.annotation.persistent;

/**
 * @author evan
 * @date 2020/5/2
 */
public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}