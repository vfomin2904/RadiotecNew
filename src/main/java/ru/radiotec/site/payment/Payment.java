package ru.radiotec.site.payment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Payment {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String addOrder(int order, int amount){

        Map<Object, Object> data = new HashMap<>();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("https://securepayments.sberbank.ru/payment/rest/register.do?userName=P7702407209-api&password=Radiotec1845&returnUrl=http://radiotec.ru/ru/success_payment&failUrl=http://radiotec.ru/ru/cart&orderNumber="+order+"&amount="+amount))
                .setHeader("User-Agent", "Java") // add request header
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {

            Map<String, String> map = mapper.readValue(response.body(), Map.class);

            return map.get("formUrl");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/ru/cart";
    }

    public void checkOrder(int order){

        Map<Object, Object> data = new HashMap<>();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("https://securepayments.sberbank.ru/payment/rest/getOrderStatusExtended.do?userName=P7702407209-api&password=Radiotec1845&orderNumber="+order))
                .setHeader("User-Agent", "Java")
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
