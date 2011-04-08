package com.acme.wicketint;

import javax.enterprise.util.AnnotationLiteral;

public class BuiltInLiteral extends AnnotationLiteral<BuiltIn> implements BuiltIn
{
   public static final BuiltInLiteral INSTANCE = new BuiltInLiteral();
}
