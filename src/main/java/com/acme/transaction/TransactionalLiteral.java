package com.acme.transaction;

import javax.enterprise.util.AnnotationLiteral;

public class TransactionalLiteral extends AnnotationLiteral<Transactional> implements Transactional {

    public static final Transactional INSTANCE = new TransactionalLiteral();

}
