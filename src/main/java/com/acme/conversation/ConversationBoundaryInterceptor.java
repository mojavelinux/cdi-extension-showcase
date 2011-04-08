/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.acme.conversation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.seam.solder.reflection.AnnotationInspector;

/**
 * Intercepts methods annotated as Conversational entry points: @{@link Begin}
 * and @{@link End}
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@ConversationBoundary
@Interceptor
public class ConversationBoundaryInterceptor implements Serializable
{
   private static final long serialVersionUID = -2729227895205287477L;

   @Inject
   Conversation conversation;
   
   @Inject
   BeanManager beanManager;

   @AroundInvoke
   public Object around(final InvocationContext ctx) throws Exception
   {
      Object result = null;

      try
      {
         if (AnnotationInspector.isAnnotationPresent(ctx.getMethod(), Begin.class, beanManager))
         {
            beginConversation(ctx);
         }

         result = ctx.proceed();

         if (AnnotationInspector.isAnnotationPresent(ctx.getMethod(), End.class, beanManager))
         {
            endConversation(ctx);
         }
      }
      catch (Exception e)
      {
         handleExceptionBegin(ctx, e);
         handleExceptionEnd(ctx, e);
         throw e;
      }

      return result;
   }

   private void handleExceptionBegin(final InvocationContext ctx, final Exception e)
   {
      if (AnnotationInspector.isAnnotationPresent(ctx.getMethod(), Begin.class, beanManager))
      {
         List<? extends Class<? extends Exception>> typesPermittedByBegin = getPermittedExceptionTypesBegin(ctx.getMethod());
         for (Class<? extends Exception> type : typesPermittedByBegin)
         {
            if (type.isInstance(e) == false)
            {
               conversation.end();
            }
         }
      }
   }

   private void handleExceptionEnd(final InvocationContext ctx, final Exception e)
   {
      if (AnnotationInspector.isAnnotationPresent(ctx.getMethod(), End.class, beanManager))
      {
         List<? extends Class<? extends Exception>> typesPermittedByEnd = getPermittedExceptionTypesEnd(ctx.getMethod());
         for (Class<? extends Exception> type : typesPermittedByEnd)
         {
            if (type.isInstance(e))
            {
               conversation.end();
            }
         }
      }
   }

   private void beginConversation(final InvocationContext ctx) throws Exception
   {
      String cid = AnnotationInspector.getAnnotation(ctx.getMethod(), Begin.class, beanManager).id();
      if ((cid != null) && !"".equals(cid))
      {
         conversation.begin(cid);
      }
      else
      {
         conversation.begin();
      }

      long timeout = AnnotationInspector.getAnnotation(ctx.getMethod(), Begin.class, beanManager).timeout();
      if (timeout != -1)
      {
         conversation.setTimeout(timeout);
      }
   }

   private void endConversation(final InvocationContext ctx)
   {
      conversation.end();
   }

   private List<? extends Class<? extends Exception>> getPermittedExceptionTypesBegin(final Method m)
   {
      return Arrays.asList(AnnotationInspector.getAnnotation(m, Begin.class, beanManager).permit());
   }

   private List<? extends Class<? extends Exception>> getPermittedExceptionTypesEnd(final Method m)
   {
      return Arrays.asList(AnnotationInspector.getAnnotation(m, End.class, beanManager).permit());
   }
}
