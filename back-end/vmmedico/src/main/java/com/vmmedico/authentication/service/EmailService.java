package com.vmmedico.authentication.service;

public interface EmailService {

    /**
     * Send a plain text email.
     *
     * @param to recipient email address
     * @param subject subject of the email
     * @param body content/body of the email
     */
    void sendMail(String to, String subject, String body);
}
