package com.number26.APIStatistics.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;

public class BucketManagerTest {


    @Test
    public void ShouldGetAverage()
    {
        BucketManager bucketManager = new BucketManager();
        assertThat(bucketManager.getAverage(22, 100, 2)).isEqualTo(74);
    }
}