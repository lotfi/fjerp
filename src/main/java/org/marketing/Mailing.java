package org.marketing;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mailing
{
    private static final Logger log = LogManager.getLogger(Mailing.class);

    private static Message populateMessage(Session session)
    {
        log.debug("populateMessage");

        Message message = new MimeMessage(session);

        try
        {
            Multipart multipart = new MimeMultipart();

            // creates body part for the message
            MimeBodyPart bodyPart = new MimeBodyPart();

            bodyPart.setContent(message, "text/html");

            // creates body part for the attachment
            MimeBodyPart attachPart = new MimeBodyPart();

            String attachFile = "D:/Documents/MyFile.mp4";

            attachPart.attachFile(attachFile);

            multipart.addBodyPart(attachPart);

            // adds parts to the multipart
            multipart.addBodyPart(bodyPart);

            multipart.addBodyPart(attachPart);

            // sets the multipart as message's content
            message.setContent(multipart);
        }

        catch (Exception e)
        {
            log.error(e);
        }

        return message;
    }

    private static void sendMail()
    {
        log.debug("sendMail");

        try
        {
            String host = "outlook.office365.com";

            String username = "lotfi.allal@outlook.fr";

            String password = "Filot!71";

            Properties props = new Properties();

            props.setProperty("mail.smtp.host", host);

            props.setProperty("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props);

            MimeMessage msg = new MimeMessage(session);

            // set the message content here

            Transport.send(msg, username, password);
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    public static void envoiEmail(String host, String port, final String uid, final String pwd, List<String> toAddr,
            String sub, String message, String[] fichiers, Map<String, String> mapImages)
            throws AddressException, MessagingException
    {
        log.debug("envoiEmail");

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", uid);
        properties.put("mail.password", pwd);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator()
        {
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(uid, pwd);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(uid));

        // InternetAddress[] toAddresses = { new InternetAddress(toAddr) };

        for (Iterator<String> toAddrIter = toAddr.iterator(); toAddrIter.hasNext();)
        {
            String email = toAddrIter.next();

            InternetAddress adresse = new InternetAddress(email);

            msg.setRecipient(Message.RecipientType.TO, adresse);
        }

        msg.setSubject(sub);

        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(message, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        // adds attachments
        if (
            fichiers != null && fichiers.length > 0
        )
        {
            for (String filePath : fichiers)
            {
                MimeBodyPart attachPart = new MimeBodyPart();

                try
                {
                    attachPart.attachFile(filePath);
                }

                catch (IOException ex)
                {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }

        // adds inline image attachments
        if (
            mapImages != null && mapImages.size() > 0
        )
        {
            Set<String> setImageID = mapImages.keySet();

            for (String contentId : setImageID)
            {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                String imageFilePath = mapImages.get(contentId);
                try
                {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(imagePart);
            }
        }

        // sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);

    }

    private static List<String> lireDestinataires()
    {
        log.debug("lireDestinataires");

        List<String> lot = new LinkedList<String>();

        try
        {
            URI uri = Mailing.class.getResource("/static/annuaire.txt").toURI();

            Path path = Paths.get(uri);

            List<String> contenu = Files.readAllLines(path, StandardCharsets.ISO_8859_1);

            Iterator<String> itr = contenu.iterator();

            while (
                itr.hasNext()
            )
            {
                String ligne = itr.next();

                ligne = ligne.trim();

                if (
                    ligne != null && ligne.isEmpty() == false
                )
                {
                    lot.add(ligne);

                }
            }

            // ecriture fichier
            /*
             * Files.write(Paths.get("annuaire.txt"), lot, StandardCharsets.ISO_8859_1,
             * StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
        }

        catch (Exception e)
        {
            log.error(e);
        }

        return lot;
    }

    public static void envoyerMail(List<String> cibles)
    {
        log.debug("envoyerMail");

        String host = "smtp.office365.com";
        String port = "587";
        String from = "lotfi.allal@outlook.fr";
        String password = "Filot!71";

        String subject = "Séminaires Systèmes d'information et Réalisation de Solutions Digitales";

        StringBuffer buffer = new StringBuffer("");

        try
        {

            // corps du message
            buffer.append("<html> <body> <p style=\"color: rgb(7, 34, 69)\"> Bonjour, <br> <br> ");
            buffer.append(
                    " Veuillez trouver dans le document ci-joint, les Fiches Techniques des Séminaires que nous organisons <br>");
            buffer.append("autour de la Conception des Systèmes d'information et <br> ");
            buffer.append("la Réalisation de Solutions Digitales en Open Source Java.<br> <br> ");
            buffer.append("Nous restons à votre disposition pour tout complément d'information.<br> <br>");
            buffer.append("Cordialement, <br> </p> ");
            buffer.append(" </body> ");
            buffer.append("</html> ");

            // insertion de la signature

            List<String> elements = Files
                    .readAllLines(Paths.get("C:/Workspace/fjerp/src/main/resources/signature.htm"));

            Iterator<String> strIterator = elements.iterator();

            while (
                strIterator.hasNext()
            )
            {
                String data = strIterator.next();

                buffer.append(data);
            }

            String message = buffer.toString();

            // inline images
            Map<String, String> inlineImages = new HashMap<String, String>();

            inlineImages.put("img1", "C:/Travail/Fjerp/communication/linkedin.png");

            inlineImages.put("img2", "C:/Travail/Fjerp/communication/twitter.png");

            inlineImages.put("img3", "C:/Travail/Fjerp/communication/facebook.png");

            inlineImages.put("img4", "C:/Travail/Fjerp/communication/logo.png");

            // attachments
            String[] attachFiles = new String[1];

            attachFiles[0] = "C:/Travail/Projets/formation/Séminaires-220920.pdf";

            envoiEmail(host, port, from, password, cibles, subject, message, attachFiles, inlineImages);

            log.debug("Message envoyé");
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    private static void batchProcess(List<String> adresses, int debut, int fin)
    {
        log.debug("batchProcess");

        List<String> lot = new LinkedList<String>();

        for (int i = debut; i <= fin; i++)
        {
            String data = adresses.get(i);

            lot.add(data);
        }

        envoyerMail(lot);
    }

    public static void main(String[] args)
    {
        log.debug("main");

        List<String> adresses = lireDestinataires();

        batchProcess(adresses, 0, 1);
    }

    /*
     * public static void main(String[] args) { log.debug("main");
     * 
     * String host = "smtp.office365.com"; String port = "587"; String from =
     * "lotfi.allal@outlook.fr"; String password = "Filot!71";
     * 
     * // message info String to = "lotfee@yahoo.com";
     * 
     * String subject =
     * "Séminaires Systèmes d'information et Réalisation de Solutions Digitales";
     * 
     * StringBuffer buffer = new StringBuffer("");
     * 
     * try {
     * 
     * // corps du message buffer.append("<html> <body> <p> Bonjour, <br>"); buffer.
     * append(" Veuillez trouver ci-joint les Fiches Techniques des Séminaires que nous organisons <br>"
     * ); buffer.
     * append("autour de l'Etude et de la Réalisation de Systèmes d'information. <br> "
     * ); buffer.append("<br>"); buffer.
     * append("Nous restons à votre disposition pour tout complément d'information.<br>"
     * ); buffer.append("Cordialement, <br> </p> "); buffer.append(" </body> ");
     * buffer.append("</html> ");
     * 
     * // insertion de la signature
     * 
     * List<String> elements = Files
     * .readAllLines(Paths.get("C:/Workspace/fjerp/src/main/resources/signature.htm"
     * ));
     * 
     * Iterator<String> strIterator = elements.iterator();
     * 
     * while ( strIterator.hasNext() ) { String data = strIterator.next();
     * 
     * buffer.append(data); }
     * 
     * String message = buffer.toString();
     * 
     * // inline images Map<String, String> inlineImages = new HashMap<String,
     * String>();
     * 
     * inlineImages.put("img1", "C:/Travail/Fjerp/communication/linkedin.png");
     * 
     * inlineImages.put("img2", "C:/Travail/Fjerp/communication/twitter.png");
     * 
     * inlineImages.put("img3", "C:/Travail/Fjerp/communication/facebook.png");
     * 
     * inlineImages.put("img4", "C:/Travail/Fjerp/communication/logo.png");
     * 
     * // attachments String[] attachFiles = new String[1];
     * 
     * attachFiles[0] = "C:/Travail/Projets/formation/Séminaires-210920.pdf";
     * 
     * envoiEmail(host, port, from, password, to, subject, message, attachFiles,
     * inlineImages);
     * 
     * log.debug("Message envoyé"); }
     * 
     * catch (Exception e) { log.error(e); } }
     */

}
