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

    private final static String SMS_API_CALL_ERROR_MESSAGE = "Call of the Sms Api failed";

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorResponse = SMS_API_CALL_ERROR_MESSAGE;
        String responseBody = readResponseBody(response);
        errorResponse += responseBody.isBlank() ? "" : ", responseBody: " + responseBody;

        return new ResponseStatusException(HttpStatus.valueOf(response.status()), errorResponse);
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