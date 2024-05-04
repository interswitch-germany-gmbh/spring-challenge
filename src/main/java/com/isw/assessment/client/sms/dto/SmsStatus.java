package com.isw.assessment.client.sms.dto;

public record SmsStatus(String destination, Integer errorCode, String errorMessage, String referenceId, String status,
                        String ticketId) {
}

