package io.github.wdpm.model;

import java.util.Locale;

/**
 * @author evan
 * @date 2020/5/20
 */
public enum Flavor {
    VANILLA,
    CHOCOLATE,
    STRAWBERRY;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.getDefault());
    }
}
