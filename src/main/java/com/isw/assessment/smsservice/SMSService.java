package com.isw.assessment.smsservice;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SMSService {

	private static final String TEST_SENDER = "TEST";
	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient smsAPIClient;

    @Autowired
    public SMSService(WebClient smsAPIClient) {
		this.smsAPIClient = smsAPIClient;
	}

	public SMSStatus sendSMS(String phoneNumber, String sms) {
		return smsAPIClient.get()
				.uri(uB -> uB.path("/rest/sms/submit").queryParam("dest", phoneNumber).queryParam("src", TEST_SENDER)
						.queryParam("text", sms).build())
				.headers(h -> h.setBasicAuth("test-challenge", "HwR7F1Oe")).retrieve().bodyToMono(SMSStatus.class)
				.block(REQUEST_TIMEOUT);
	}


}
