package frame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.DecimalFormat;

public class Converter {

    private final String apiKey;
    private final DecimalFormat df = new DecimalFormat("0.00");

    public Converter(String apiKey) {
        this.apiKey = apiKey;
    }

    public String convert(double amount, String convertFrom, String convertTo) throws CurrencyApiException {
        double result = (amount * findExchangeRate(convertFrom, convertTo));
        return df.format(result);
    }

    private double findExchangeRate(String convertFrom, String convertTo) throws CurrencyApiException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            HttpEntity entity = getExchangeRateData(convertFrom, convertTo);
            String entityString = EntityUtils.toString(entity);
            JsonObject jsonObject = gson.fromJson(entityString, JsonObject.class);
            if (jsonObject.has("message")){
                throw new CurrencyApiException(String.valueOf(jsonObject.get("message")));
            }
            return jsonObject.get("data").getAsJsonObject().get(convertTo).getAsJsonObject().get("value").getAsDouble();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private HttpEntity getExchangeRateData(String convertFrom, String convertTo) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.currencyapi.com/v3/latest?base_currency=" + convertFrom + "&currencies=" + convertTo);
        request.setHeader("apikey", apiKey);
        try {
            HttpResponse response = client.execute(request);
            return response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }


}
