package com.chenqiao.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Optional {

    String stringValue() default "";
    int intVaalue() default 0;
    float floatValue() default 0f;
    boolean booleanValue() default false;
}
