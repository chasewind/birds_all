package com.bird.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientHelper {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost/");
        CloseableHttpResponse response = httpclient.execute(httpget);
        httpget.setHeader("X-Forwarded-For", "192.168.0.1"); 
        try {
        } finally {
            response.close();
        }
    }
}
