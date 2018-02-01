package me.andpay.ac.wpn.srv.validate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by cen on 2016/11/16.
 */
public class WpnValidateAspect  extends ValidationAspect{


    //Controller层切点
    @Pointcut(value = "execution(*  me.andpay.ac.wpp.web.controllers..*.*(..))")
    public void validateionAspect() {
    }

    @Before("validateionAspect()")
    public void doBefore(JoinPoint joinPoint) {
        super.doBefore(joinPoint);
    }
}
