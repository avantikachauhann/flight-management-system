import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Message;
import javax.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.*;

public class Mail {

    public static void sendMailOTP(String reciepient, String code){

//        System.out.println("Preparing to send mail");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "amity.airlines@gmail.com";
        String password = "VijayMallya123";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareMessage(session, code, reciepient);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println("Email sent");
    }

    private static Message prepareMessage(Session session, String code, String recepient) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amity.airlines@gmail.com",
                    "Alpha Airlines"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Email Verification Password");

            MimeMultipart content;
            content = new MimeMultipart();
            String cid = generateContentId("OTP");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setText(""
                            + "<html>"
                            + "<body>"
                            + "<p style=\"text-align:center;\">"
                            +   "<font size=\"5\" color=\"#3b3b3b\">Welcome to </font><br>"
                            +   "<font size=\"10\" color=\"#000000\"><b>Alpha Airlines</b></font>"
                            + "</p>"

                            + "<table align=\"center\" width=\"75%\">"
                            + "<tr>"
                            + "<th>"
                            + "<div style=\"margin: 0 auto; text-align: center;\">"
                            + "<img align=\"center\" src=\"cid:" + cid + "\" alt=\"OTP Representation\" />"
                            + "</div>"
                            + "</th>"
                            + "</tr>"
                            + "</table>"

                            + "<p style=\"font-size:140%; text-align:center;\">Your email verification one time password is : "
                            + code + "</p>"
                            + "<br>"

                            + "<p style=\"text-align: center;\">"
                            +   "<font size=\"3\" color=\"#000000\">Didn't do this. Write us back at </font>"
                            +   "<font size=\"3\" color=\"#007bff\">amity.airlines@gmail.com</font>"
                            +   "<font size=\"3\" color=\"#000000\"> and let us know about this issue.</font>"
                            +   "</p>"

                            + " </body>"
                            + "</html>"
                    , "US-ASCII", "html");
            content.addBodyPart(htmlPart);
            MimeBodyPart imagePart2 = new MimeBodyPart();

            {
                //Writing OTP on Image:
                Image.writeOTP(code);
            }

            imagePart2.attachFile("C:\\Users\\Saksham\\IdeaProjects\\NTCC\\src\\Images\\Mail\\ConfirmedEmail.png");
            imagePart2.setContentID("<" + cid + ">");
            imagePart2.setDisposition(MimeBodyPart.INLINE);
            content.addBodyPart(imagePart2);
            message.setContent(content);

            return message;

        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
    }

    public static void sendFMailOTP(String reciepient, String code){

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "amity.airlines@gmail.com";
        String password = "VijayMallya123";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareFMessage(session, code, reciepient);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Message prepareFMessage(Session session, String code, String recepient) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amity.airlines@gmail.com",
                    "Alpha Airlines"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Forgot Password?");

            message.setContent(

                    "<p style=\"text-align:center;\">"
                    +   "<font size=\"9\" color=\"#000000\"><b>Alpha Airlines</b></font>"
                    + "</p>"

                    + "<p style=\"font-size:140%; text-align:center;\">Your reset password verification one time password is : "
                    +   code + "</p>"
                    + "<br>"

                    + "<p style=\"text-align: center;\">"
                    +   "<font size=\"3\" color=\"#000000\">Didn't do this. Write us back at </font>"
                    +   "<font size=\"3\" color=\"#007bff\">amity.airlines@gmail.com</font>"
                    +   "<font size=\"3\" color=\"#000000\"> and let us know about this issue.</font>"
                    +  "</p>"

                    ,"text/html");
            return message;

        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void sendBookingConfirmation(String[] bookingDetails, String reciepient) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "amity.airlines@gmail.com";
        String password = "VijayMallya123";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareCMessage(session, bookingDetails, reciepient);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Message prepareCMessage(Session session, String[] details, String recepient) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amity.airlines@gmail.com",
                    "Alpha Airlines"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Flight Booked: " + details[0]);

            //New Order is: Refernce, Name, dAirport, dCity, dDate, dTime, aAirport, aCity, Passegers, Class
            //      Meals

            message.setContent(

                    "<p style=\"text-align:center;\">"
                    +   "<font size=\"9\" color=\"#000000\"><b>Alpha Airlines</b></font>"
                    + "</p>"

                    + "<p style=\"font-size:140%; text-align:center;\">Your flight is booked successfully. A few details of the booked flight are as follows:</p>"
                    + "<p style=\"font-size:110%; text-align:center;\">"
                            +"<br>Booking Refernce Number: " + details[0]
                            +"<br>Name: " + details[1]
                            +"<br>"
                            +"<br>Departure Airport: " + details[2]
                            +"<br>Departure City: " + details[3]
                            +"<br>Departure Date: " + details[4]
                            +"<br>Departure Time: " + details[5]
                            +"<br>"
                            +"<br>Arrival Airport: " + details[6]
                            +"<br>Arrival City: " + details[7]
                            +"<br>"
                            +"<br>Number of Passengers: " + details[8]
                            +"<br>Class of flight: " + details[9]
                            +"<br>Meal on flight: " + details[10]
                            +"<br><br>"
                            +"For more details, check the manage tab of the Aplha Airlines application."

                    + "<p style=\"text-align: center;\">"
                    +   "<font size=\"2\" color=\"#000000\">Didn't do this. Write us back at </font>"
                    +   "<font size=\"2\" color=\"#007bff\">amity.airlines@gmail.com</font>"
                    +   "<font size=\"2\" color=\"#000000\"> and let us know about this issue.</font>"
                    +  "</p>"

                    ,"text/html");
            return message;

        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void sendBookingReturnConfirmation(String s, String s1, String reciepient) {

        System.out.println("Preparing to send R mail");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "amity.airlines@gmail.com";
        String password = "VijayMallya123";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = prepareRMessage(session, s, s1, reciepient);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Email sent");

    }

    private static Message prepareRMessage(Session session, String rf1, String rf2, String recepient) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amity.airlines@gmail.com",
                    "Alpha Airlines"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Flight Booked");

            message.setContent(

                    "<p style=\"text-align:center;\">"
                            +   "<font size=\"9\" color=\"#000000\"><b>Alpha Airlines</b></font>"
                            + "</p>"

                            + "<p style=\"font-size:140%; text-align:center;\">Your flight ticket is booked successfully. Reference Numbers of the booked flight are as follows :</p>"
                            + "<br> <p style=\"font-size:140%; text-align:center;\">"  + rf1 + " and " + rf2 + "</p>"
                            + "<br>For more details, check the manage tab of the Aplha Airlines application."

                            + "<p style=\"text-align: center;\">"
                            +   "<font size=\"2\" color=\"#000000\">Didn't do this. Write us back at </font>"
                            +   "<font size=\"2\" color=\"#007bff\">amity.airlines@gmail.com</font>"
                            +   "<font size=\"2\" color=\"#000000\"> and let us know about this issue.</font>"
                            +  "</p>"

                    ,"text/html");
            return message;

        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

        sendBookingReturnConfirmation("AMI00SINLAX378-B1#1", "AMI00LAXSIN604-B2#1",
                "adidrcool@gmail.com");

//      sendBookingConfirmation(arr, "parth.risingstar@gmail.com");
    }

}