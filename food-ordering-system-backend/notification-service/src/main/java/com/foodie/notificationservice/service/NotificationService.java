package com.foodie.notificationservice.service;

import com.foodie.notificationservice.model.Notification;
import com.foodie.notificationservice.repository.NotificationRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Notification createNotification(String title, String message,String recipient) {
        Notification notification = new Notification(title, message, recipient);
        return notificationRepository.save(notification);
    }

    public  void sendNotification(Notification notification) throws MessagingException, DocumentException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("adoptawallet.devnet@gmail.com");
        helper.setTo(notification.getRecipient());
        helper.setSubject(notification.getTitle());
        helper.setText(notification.getMessage(),true);

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        createPDF(notification,pdfStream);

        helper.addAttachment("Notification.pdf",new ByteArrayResource(pdfStream.toByteArray()));


        mailSender.send(message);
        System.out.println("Notification sent successfully");
        notification.setSent(true);
        notificationRepository.save(notification);
    }

private void createPDF(Notification notification, ByteArrayOutputStream outputStream) throws MessagingException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Notification Details"));
        document.add(new Paragraph("Title: " + notification.getTitle()));
        document.add(new Paragraph("Message: " + notification.getMessage()));
        document.add(new Paragraph("Recipient: " + notification.getRecipient()));
        document.close();
}
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();  // Fetch all notifications
    }
}
