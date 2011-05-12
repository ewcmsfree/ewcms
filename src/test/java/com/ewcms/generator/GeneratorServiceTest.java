/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/generator/applicationContext*.xml"}, inheritLocations = true)
public class GeneratorServiceTest {

    @Autowired
    private GeneratorServiceable generator;

    @Test
    public void testGenerator()throws Exception{
        generator.generatorDebug(10);
    }
}
