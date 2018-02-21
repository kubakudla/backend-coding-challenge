package com.backendcodingchallenge.util;

import org.junit.Test;

import java.math.BigDecimal;

import static com.backendcodingchallenge.util.CalculationUtil.calculateVat;
import static com.backendcodingchallenge.util.CalculationUtil.exchangeMoney;
import static org.junit.Assert.assertEquals;

public class CalculationUtilTest {

    @Test
    public void test_calculateVat() {
        assertEquals(new BigDecimal("25.00"), calculateVat(new BigDecimal("150")));
        assertEquals(new BigDecimal("14.14"), calculateVat(new BigDecimal("84.84")));
        assertEquals(new BigDecimal("7.71"), calculateVat(new BigDecimal("46.27")));
        assertEquals(new BigDecimal("403923.67"), calculateVat(new BigDecimal("2423542")));
        assertEquals(new BigDecimal("0.13"), calculateVat(new BigDecimal("0.80")));
        assertEquals(new BigDecimal("0.13"), calculateVat(new BigDecimal("0.8")));
        assertEquals(new BigDecimal("0.00"), calculateVat(new BigDecimal("0")));
        assertEquals(new BigDecimal("0.00"), calculateVat(new BigDecimal("0.01")));
    }

    @Test
    public void test_exchangeMoney() {
        // check that no money is lost
        assertEquals(new BigDecimal("10.00"), exchangeMoney(new BigDecimal("20"), new BigDecimal("0.5")));
        assertEquals(new BigDecimal("18.41"), exchangeMoney(new BigDecimal("20.69"), new BigDecimal("0.89")));
        assertEquals(new BigDecimal("7.37"), exchangeMoney(new BigDecimal("11.34"), new BigDecimal("0.65")));
        assertEquals(new BigDecimal("43.85"), exchangeMoney(new BigDecimal("42.99"), new BigDecimal("1.02")));
    }
}
