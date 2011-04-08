package com.acme.startup;

import javax.enterprise.util.AnnotationLiteral;

public class DestroyedLiteral extends AnnotationLiteral<Destroyed> implements Destroyed {

    public static final Destroyed INSTANCE = new DestroyedLiteral();

}
