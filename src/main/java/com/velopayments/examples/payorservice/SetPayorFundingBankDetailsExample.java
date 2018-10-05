package com.velopayments.examples.payorservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authentication.AuthenticationExample;

import java.io.IOException;
import java.util.*;

public class SetPayorFundingBankDetailsExample {

    /**
     * Usage - parameter 1 = Velo API Key
     *         parameter 2 = Velo API Secret
     *         parameter 3 = Payor ID (UUID)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        setFundingBankDetails(args[0], args[1], args[3]);
    }

    public static String setFundingBankDetails(String apiKey, String apiSecret, String payorId) throws IOException {
        return setFundingBankDetails(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String setFundingBankDetails(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws IOException {

        String apiUrl = "https://api.sandbox.velopayments.com/v1/payors/";
        String apiAction = "/payorFundingBankDetailsUpdate";

        //Get API Access Token
        String apiAccessToken = AuthenticationExample.getApiToken(apiKey, apiSecret);

        // Path parameters
        String apiUrlWithQueryParams = apiUrl + payorId + apiAction;

        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("routingNumber", "123456789");
        updateRequest.put("accountNumber", "7894561321259");
        updateRequest.put("accountName", "Umphrey's McGee");

        //create json object
        ObjectMapper objectMapper = new ObjectMapper();
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        return String.valueOf(httpClient.post(apiUrlWithQueryParams, httpHeaders, updateRequestJson, HttpClient.ContentType.JSON).getCode());
    }
}
