package com.telran.bankapplication.service.util;

import com.telran.bankapplication.entity.enums.CurrencyCode;
import com.telran.bankapplication.service.exception.RequestApiException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class CurrencyService {

    private static final String NATIONAL_BANK_POLAND_API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/";

    public BigDecimal getCurrencyRate(CurrencyCode currencyCode) {
        if (CurrencyCode.PLN == currencyCode) {
            return BigDecimal.ONE;
        }
        String url = NATIONAL_BANK_POLAND_API_URL + currencyCode.toString().toLowerCase();
        JSONObject currencyJsonObj;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) new URL(url).getContent()))){
            currencyJsonObj = new JSONObject(reader.lines().collect(Collectors.joining("\n")));
        } catch (IOException e) {
            throw new RequestApiException();
        }
        JSONObject rateJsonObj = currencyJsonObj.getJSONArray("rates").getJSONObject(0);

        return BigDecimal.valueOf(rateJsonObj.getDouble("mid"));
    }
}
