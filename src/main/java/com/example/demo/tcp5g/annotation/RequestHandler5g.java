package com.example.demo.tcp5g.annotation;

import com.example.demo.tcp5g.msg.CmdType5g;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author TRH
 * @description: Processor annotations
 * @Package com.example.demo.tcp5g.annotation
 * @date 2023/3/27 16:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RequestHandler5g {

    CmdType5g type();
}
