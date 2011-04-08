package com.acme.startup;

import javax.enterprise.util.AnnotationLiteral;

public class InitializedLiteral extends AnnotationLiteral<Initialized> implements Initialized {

    public static final Initialized INSTANCE = new InitializedLiteral();

}
