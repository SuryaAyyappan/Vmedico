package com.vmmedico.common;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentConfirmation(String to, String patientName, String doctorName,
                                            LocalDate date, LocalTime start, LocalTime end) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your Appointment is Confirmed!");

            String htmlContent = """
                <html>
                <body style='font-family:Arial,sans-serif;'>
                    <h2 style='color:#2c3e50;'>Appointment Confirmation</h2>
                    <p>Dear <b>%s</b>,</p>
                    <p>Your appointment has been successfully booked with <b>Dr. %s</b>.</p>
                    <table style='border-collapse:collapse;'>
                        <tr><td><b>Date:</b></td><td>%s</td></tr>
                        <tr><td><b>Time:</b></td><td>%s - %s</td></tr>
                    </table>
                    <p style='margin-top:10px;'>Please arrive 10 minutes before your scheduled time.</p>
                    <p style='color:#888;'>Thank you for choosing VM Medico.</p>
                </body>
                </html>
            """.formatted(patientName, doctorName, date, start, end);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendPrescriptionEmail(String toEmail, String patientName, String doctorName,
                                      LocalDate date, LocalTime startTime, String fileName, byte[] pdfBytes) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Your Prescription from " + doctorName);
            String html = "<p>Dear " + patientName + ",</p>"
                    + "<p>Your prescription (dated " + date + " at " + startTime + ") is attached.</p>"
                    + "<p>Regards,<br/>" + (doctorName != null ? doctorName : "Your Doctor") + "</p>";
            helper.setText(html, true);
            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send prescription email", e);
        }
    }
}
