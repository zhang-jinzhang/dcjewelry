package com.ceyi.project.dcjewelry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by lingh on 2017/3/23.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JewelryAppConfig.class})
public class SpringTest {
    @Test
    public void testDumy() {

    }
}
