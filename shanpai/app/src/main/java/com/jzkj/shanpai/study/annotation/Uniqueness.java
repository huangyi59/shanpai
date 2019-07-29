package com.jzkj.shanpai.study.annotation;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
