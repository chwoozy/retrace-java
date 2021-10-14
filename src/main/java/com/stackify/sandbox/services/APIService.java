package com.stackify.sandbox.services;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class APIService {
    public static final String API_END_POINT = "https://sandboxapi.azurewebsites.net/api/values";

    private static RestTemplate restTemplate;

    public static String call(String extension) {
        try {
            restTemplate = new RestTemplate();
            return restTemplate.getForObject(API_END_POINT + "/" + extension, String.class);
        } catch (HttpClientErrorException e) {
            return "";
        }
    }

}
