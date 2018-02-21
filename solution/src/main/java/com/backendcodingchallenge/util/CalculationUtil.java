package com.backendcodingchallenge.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Util class for different functionalities related to calculations
 */
public class CalculationUtil {

    // precision same as in sql
    private static final int PRECISION = 4;
    // if we divide by 6 we get 20% value of VAT from gross value
    private static final String VAT_DIVISION = "6";
    private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static int DECIMALS = 2;

    private static final BigDecimal VAT_DIVISION_VALUE = new BigDecimal(VAT_DIVISION);

    private static final MathContext MATH_CONTEXT = new MathContext(PRECISION);

    public static BigDecimal calculateVat(BigDecimal amount) {
        amount = rounded(amount);
        return rounded(amount.divide(VAT_DIVISION_VALUE, ROUNDING_MODE));
    }

    public static BigDecimal exchangeMoney(BigDecimal amount, BigDecimal exchangeRate) {
        amount = rounded(amount);
        return rounded(amount.multiply(exchangeRate, MATH_CONTEXT));
    }

    private static BigDecimal rounded(BigDecimal aNumber) {
        return aNumber.setScale(DECIMALS, ROUNDING_MODE);
    }
}
