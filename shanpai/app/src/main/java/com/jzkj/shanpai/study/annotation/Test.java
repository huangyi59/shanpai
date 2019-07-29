package com.jzkj.shanpai.study.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解时，需要一些元注解
 * @Target用来定义你的注解将应用于什么地方 方法或则是一个域
 *
 * @Rectetion 用来定义该注解在哪一个级别可用，在源代码中（SOURCE）类文件中（CLASS）或者运行时（RUNTIME）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

}
