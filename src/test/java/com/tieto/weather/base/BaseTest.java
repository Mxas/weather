package com.tieto.weather.base;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tieto.weather.helper.WeatherTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/test-beans.xml", "classpath:spring/context.xml" })
public abstract class BaseTest {

    //--------some config values ----
    @Value("${com.tieto.popular.cities.indicator}")
    protected int popularCitiesIndicator;

    @Value("#{'${com.tieto.predefined.popular.cities}'.split(',')}")
    protected Collection<String> predefinedPopularCities;

    @Value("#{'${com.tieto.predefined.popular.cities}'.split(',').length}")
    protected int predefinedPopularCitiesCount;
    //---------

    @Autowired
    protected WeatherTestHelper weatherTestHelper;

    @Before
    public void reinitTestData() throws Exception {
        weatherTestHelper.afterPropertiesSet();
    }
}
