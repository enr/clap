package com.github.enr.clap.test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.testng.Assert.assertNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.enr.clap.util.Casts;

@Test(suiteName = "Util package")
public class CastsTest {

    Map<String, Object> objects = new HashMap<String, Object>();

    @BeforeClass
    public void initData() {
        objects.put("pojo", new PojoImpl());
    }

    @Test(expectedExceptions = { ClassCastException.class })
    public void castFailingWithExceptionTest() {
        //Date poj = Casts.castOrFail(context.getBean("pojo"), Date.class);
    	Casts.castOrFail(objects.get("pojo"), Date.class);
    }

    @Test
    public void castOrNullForIncompatibleTypes() {
        //Date poj = Casts.castOrNull(context.getBean("pojo"), Date.class);
        Date poj = Casts.castOrNull(objects.get("pojo"), Date.class);
        assertNull(poj);
    }

    @Test
    public void testNullReferenceCasting() {
        Date poj = Casts.castOrNull(null, Date.class);
        assertNull(poj);
    }

    @Test
    public void automaticCast() {
        Pojo pojo = Casts.cast(objects.get("pojo"));
        assertThat(pojo).as("pojo").isNotNull().isInstanceOf(PojoImpl.class);
    }

    @Test(expectedExceptions = { ClassCastException.class })
    public void automaticCastFailing() {
        @SuppressWarnings("unused")
        String pojo = Casts.cast(objects.get("pojo"));
    }

    @Test
    public void objectCastingTest() {
        Object original = objects.get("pojo");
        assertThat(original).as("original pojo").isNotNull().isInstanceOf(PojoImpl.class);
        Pojo pojo1 = Casts.castOrFail(objects.get("pojo"), Pojo.class);
        assertThat(pojo1).as("pojo 1").isNotNull().isInstanceOf(PojoImpl.class);
        PojoImpl pojo2 = Casts.castOrNull(objects.get("pojo"), PojoImpl.class);
        assertThat(pojo2).as("pojo 2").isNotNull().isInstanceOf(PojoImpl.class);
    }

    @Test(expectedExceptions = { ClassCastException.class })
    public void objectCastFailingWithExceptionTest() {
        Casts.castOrFail(objects.get("pojo"), Date.class);
    }

    @Test
    public void objectCastFailingWithNull() {
        Date pojo = Casts.castOrNull(objects.get("pojo"), Date.class);
        assertThat(pojo).as("pojo as date").isNull();
    }

    @Test
    public void nullReferenceCasting() {
        Date pojo = Casts.castOrNull(null, Date.class);
        assertThat(pojo).as("null casting").isNull();
    }

    @Test
    public void testGetFromMap() {
    	Date today = new Date();
    	Map<String, Object> rawMap = new HashMap<String, Object>();
    	rawMap.put("astring", "A String");
    	rawMap.put("adate", today);
    	String s = Casts.getFromMap(rawMap, "astring", String.class);
    	Date d = Casts.getFromMap(rawMap, "adate", Date.class);
        assertThat(s).as("string").isEqualTo("A String");
        assertThat(d).as("date").isEqualTo(today);
    }

    @DataProvider
    public Object[][] castsdata() {
        return new Object[][] {
                new Object[] { new Date(), Date.class },
                new Object[] { "Linux", String.class },
                new Object[] { 2, Integer.class },
                new Object[] { new HashMap<Object, Object>(), Map.class } };
    }
    @Test(dataProvider = "castsdata")
    public void testCastOrFail(Object obj, Class<?> requestedType) {
    	Object result = Casts.castOrFail(obj, requestedType);
    	assertThat(result).as("cast result "+obj).isInstanceOf(requestedType);
    }
    
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void invalidDataChecks_01() {
        Casts.castOrFail("", null);
    }    
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void invalidDataChecks_02() {
        Casts.castOrNull("", null);
    }    
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void invalidDataChecks_03() {
        Casts.getFromMap(null, "", Date.class);
    }    
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void invalidDataChecks_04() {
        Casts.getFromMap(new HashMap<Object, Object>(), "", null);
    }
}
