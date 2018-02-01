package me.andpay.ac.wpn.srv.validate;

/**
 * Created by cen on 2016/11/16.
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by cen on 16/11/7.
 * 参数校验拦截器
 */
@Aspect
@Component
public class ValidationAspect  {


    public void doBefore(JoinPoint joinPoint) {

    }
}
