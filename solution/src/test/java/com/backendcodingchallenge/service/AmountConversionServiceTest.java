package com.backendcodingchallenge.service;

import com.backendcodingchallenge.service.external.ExchangeRateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AmountConversionServiceTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private AmountConversionService euroConversionService;

    @Test
    public void test_convertEuroToPounds_isCalled() {

        when(exchangeRateService.findExchangeRate()).thenReturn(BigDecimal.TEN);

        BigDecimal pounds = euroConversionService.convertEuroToPounds("2");
        assertEquals(new BigDecimal("20.00"), pounds);
    }

    @Test
    public void test_success_removeUnnecessarySigns() {
        assertEquals("13.44", euroConversionService.removeUnnecessaryChars("13,44"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryChars("13.44"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryChars(" 13.44"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryChars(" 13.44 "));
    }

    @Test
    public void test_fail_removeUnnecessarySigns() {
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13, 44"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13.4 4"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars(" 1 3. 44"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars(" 13 .44 "));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13,44EUR"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13,44 EUR"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13.44eur"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryChars("13.44 eur "));
    }

    @Test
    public void test_success_removeUnnecessarySignsWithEuroText() {
        assertEquals("13.00", euroConversionService.removeUnnecessaryCharsWithEuroText("13.00"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText(" 13.44EUR"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44 EUR"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44eur"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44 eur "));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44EUR"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44 EUR"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText(" 13,44eur"));
        assertEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44  eur"));
    }

    @Test
    public void test_fail_removeUnnecessarySignsWithEuroText() {
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText(" 13.44E UR"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44 EU R"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44eu r"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13.44 e ur "));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44E UR"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText("13,44 EU R"));
        assertNotEquals("13.44", euroConversionService.removeUnnecessaryCharsWithEuroText(" 13,44eur!"));
    }
}
