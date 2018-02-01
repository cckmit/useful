package me.andpay.ac.wpn.srv.mock;

import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by cen on 2017/6/21.
 */
@ContextConfiguration(locations = { "/spring-config/ac-wpn-srv-test-mock-config.xml" } )
@RunWith(SpringDbUnitClassRunner.class)
public class MockBase {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();



    @Before
    public void before() {
    }
}
