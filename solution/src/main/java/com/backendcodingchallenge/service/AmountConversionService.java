package com.backendcodingchallenge.service;


import com.backendcodingchallenge.service.external.ExchangeRateService;
import com.backendcodingchallenge.util.CalculationUtil;
import org.modelmapper.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for converting the 'Amount' field into correct value in Pounds
 * <p>
 * Allows commas/dots and 'eur'/'EUR' labels
 */
@Service
public class AmountConversionService {

    private static final String EUR = "EUR";

    private final ExchangeRateService exchangeRateService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AmountConversionService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Converter for the amount field which is a String.
     *
     * @return the same amount if in pounds, converted pounds if amount was in euro
     */
    public Converter<String, BigDecimal> provideStringToBigDecimalPoundsConverter() {

        return ctx -> ctx.getSource().toUpperCase().contains(EUR) ?
            convertEuroToPounds(removeUnnecessaryCharsWithEuroText(ctx.getSource())) : new BigDecimal(removeUnnecessaryChars(ctx.getSource()));
    }

    BigDecimal convertEuroToPounds(String amountString) {
        logger.error(amountString);
        BigDecimal amount = new BigDecimal(amountString);
        BigDecimal exchangeRate = exchangeRateService.findExchangeRate();
        logger.info("Amount in Pounds to be exchanged: " + exchangeRate);
        logger.info("Current exchange rate: " + exchangeRate);
        BigDecimal exchangedMoney = CalculationUtil.exchangeMoney(amount, exchangeRate);
        logger.info("Exchanged value in Euro: " + exchangedMoney);
        return exchangedMoney;
    }

    String removeUnnecessaryChars(String amount) {
        logger.error(amount);
        return replaceCommaWithDotAndTrim(amount);
    }

    String removeUnnecessaryCharsWithEuroText(String amount) {
        logger.error(amount);
        amount = replaceCommaWithDotAndTrim(amount);
        return removeEuroSign(amount);
    }

    private String removeEuroSign(String amount) {
        return amount.toUpperCase().replace(EUR, "").trim();
    }

    private String replaceCommaWithDotAndTrim(String text) {
        return text.replace(',', '.').trim();
    }
}
