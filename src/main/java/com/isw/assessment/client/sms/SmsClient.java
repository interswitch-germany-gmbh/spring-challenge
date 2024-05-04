package com.isw.assessment.client.sms;

import com.isw.assessment.client.sms.dto.SmsStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(name = "${client.sms.name}", url = "${client.sms.url}", configuration = SmsClientConfiguration.class)
public interface SmsClient {

    @GetMapping(path = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    SmsStatus sendSms(@RequestParam("dest") String dest,
                      @RequestParam("src") String src,
                      @RequestParam("text") String text);


}