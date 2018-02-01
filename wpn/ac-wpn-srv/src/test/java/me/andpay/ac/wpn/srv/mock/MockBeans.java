package me.andpay.ac.wpn.srv.mock;

import me.andpay.ac.wpn.srv.service.DataInitService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cen on 2017/6/20.
 */
public class MockBeans extends MockBase {


    @Autowired
    private DataInitService dataInitService;

}
