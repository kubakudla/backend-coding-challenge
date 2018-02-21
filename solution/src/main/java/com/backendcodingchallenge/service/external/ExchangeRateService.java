package com.backendcodingchallenge.service.external;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.backendcodingchallenge.util.ConstantUrls.EXCHANGE_RATE_API_URL;

/**
 * Service that uses external Exchange API to echange Pounds into Euro
 */
@Service
public class ExchangeRateService {

    private static final String GBP = "GBP";
    private static final String RATES = "rates";

    public BigDecimal findExchangeRate() {
        try {
            URL url = new URL(EXCHANGE_RATE_API_URL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();
            return jsonObject.get(RATES).getAsJsonObject().get(GBP).getAsBigDecimal();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
