package com.github.enr.clap.test;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.enr.clap.util.Casts;
import com.google.common.collect.Maps;


@Test(suiteName="Util package")
public class CastsTest
{

	Map<String, Object> objects = Maps.newHashMap();
	
    @BeforeClass
    public void initData()
    {
    	objects.put("pojo", new PojoImpl());
    }

    @Test
    public void automaticCast()
    {
    	Pojo pojo = Casts.cast(objects.get("pojo"));
        assertThat(pojo).as("pojo").isNotNull().isInstanceOf(PojoImpl.class);
    }
    
    @Test(expectedExceptions = { ClassCastException.class })
    public void automaticCastFailing()
    {
        String pojo = Casts.cast(objects.get("pojo"));
    }
    
    @Test
    public void objectCastingTest()
    {
        Object original = objects.get("pojo");
        assertThat(original).as("original pojo").isNotNull().isInstanceOf(PojoImpl.class);
        Pojo pojo1 = Casts.castOrFail(objects.get("pojo"), Pojo.class);
        assertThat(pojo1).as("pojo 1").isNotNull().isInstanceOf(PojoImpl.class);
        PojoImpl pojo2 = Casts.castOrNull(objects.get("pojo"), PojoImpl.class);
        assertThat(pojo2).as("pojo 2").isNotNull().isInstanceOf(PojoImpl.class);
    }

    @Test(expectedExceptions = { ClassCastException.class })
    public void objectCastFailingWithExceptionTest()
    {
        Casts.castOrFail(objects.get("pojo"), Date.class);
    }

    @Test
    public void objectCastFailingWithNull()
    {
        Date pojo = Casts.castOrNull(objects.get("pojo"), Date.class);
        assertThat(pojo).as("pojo as date").isNull();
    }

    @Test
    public void nullReferenceCasting()
    {
        Date pojo = Casts.castOrNull(null, Date.class);
        assertThat(pojo).as("null casting").isNull();
    }
}
