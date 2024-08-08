package com.bandomatteo.Prototipo1.mappers;

public interface Mapper <A,B> {

    B mapto(A a);

    A mapfrom(B b);
}
