package com.jzkj.shanpai.study.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    // id description 类似方法定义
    // 注解元素在使用时表现为名-值对的形式 并需要至于@UseCase之后的括号内
    // 注解元素可用的类型 所有的基本类型 String Class enum Annotation
    //
    public int id();
    public String description() default " no description ";
}
