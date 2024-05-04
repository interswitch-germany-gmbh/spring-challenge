package com.isw.assessment.client.sms;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SmsClientFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = readResponseBody(response);
        responseBody= responseBody.isBlank() ? null : responseBody;

        return new ResponseStatusException(HttpStatus.valueOf(response.status()), responseBody);
    }

    private static String readResponseBody(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(response.body().asReader(StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            return "";
        }
        return stringBuilder.toString();
    }
}