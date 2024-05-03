package com.isw.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
 

@SpringBootApplication
public class ChallengeApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(ChallengeApplication.class, args);
  } 
  
	@Bean
	public WebClient getAPIClient() {
		return WebClient.create("https://sms.vanso.com/");
	}
}

 