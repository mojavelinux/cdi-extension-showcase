package com.acme.wicketint;

import javax.enterprise.util.AnnotationLiteral;

public class HomePageLiteral extends AnnotationLiteral<WicketHomePage> implements WicketHomePage
{
   public static final HomePageLiteral INSTANCE = new HomePageLiteral();
}
