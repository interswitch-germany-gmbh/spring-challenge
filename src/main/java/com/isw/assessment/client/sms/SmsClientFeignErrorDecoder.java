package com.isw.assessment.client.sms;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SmsClientFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        String responseBody = readResponseBody(response);
        return switch (response.status()) {
            case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, responseBody);
            case 401 -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, responseBody);
            case 403 -> new ResponseStatusException(HttpStatus.FORBIDDEN);
            case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND);
            case 408 -> new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, responseBody);
            case 500 -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, responseBody);
            default -> new RuntimeException(responseBody);
        };

    }

    private static String readResponseBody(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(response.body().asReader(StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            return "";
        }
        return stringBuilder.toString();
    }
}