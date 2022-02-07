package com.example.application.views;

import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

// Importing input output classes
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class SendMail {

    public void send(String mail_adr, String name, String amount, String dest, String time, String date) {
        String to = mail_adr;

        // Sender's email ID needs to be mentioned
        String from = "vincas.express@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                try {
                    String passw = read_pass();
                    return new PasswordAuthentication("vincas.express@gmail.com", passw);
                } catch (IOException e) {
                    System.out.println("reading password failed!");
                }
                return new PasswordAuthentication("vincas.express@gmail.com", "*****");
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("VinCas Express Tickets!");

            String uniqueID = UUID.randomUUID().toString().substring(0,8);

            String content = String.format("<h1>Hello %s!<h1><h3>These are your %s tickets for %s<h3><h3>Your train is leaving on the %s at %s.<h3><h3>This is your Ticket-ID: %s<h3><h2>Enjoy your Trip!<h2><h3>Thank you for choosing VinCasExpress :)", name,amount,dest,date,time,uniqueID);
            // Send the actual HTML message.
            message.setContent(
                content,
            "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Send message successfully....");
            System.out.println(content);

            // send mail info pop up
            Notification success = new Notification();
            success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            Div text = new Div(new Text("Mail send successfully!"));

            Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
            success.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            success.add(layout);
            success.open();


        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    // ignoring my problems with throw
    public String read_pass() throws IOException {
        // pass the path to the file as a parameter
        try {
            File myObj = new File("src/main/java/com/example/application/views/password.txt");
            
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              return data;
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

          return "READER FAILED";
    }

}