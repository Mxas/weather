package com.tieto.weather.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.services.RepositoryService;
import com.tieto.weather.services.WsMapperService;

/**
 * Tests {@link RepositoryService}.
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RepositoryServiceTest extends BaseTest {

    @Autowired
    private WsMapperService mapperService;

    @Test
    public void getAllPopulatList() {
    }

}
