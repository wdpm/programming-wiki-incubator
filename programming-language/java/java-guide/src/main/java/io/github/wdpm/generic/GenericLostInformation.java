package io.github.wdpm.generic;

import java.util.*;

/**
 * 泛型擦除丢失参数类型的信息
 *
 * @author evan
 * @date 2020/5/1
 */
public class GenericLostInformation {

    public static void main(String[] args) {
        List<A>                list  = new ArrayList<>();
        Map<A, B>              map   = new HashMap<>();
        Quark<B>               quark = new Quark<>();
        Particle<Long, Double> p     = new Particle<>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
    }

    // [E]
    // [K, V]
    // [Q]
    // [Position, Momentum]
}

class A {
}

class B {
}

class Quark<Q> {
}

class Particle<Position, Momentum> {
}