package com.vidaEsSalud.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.vidaEsSalud.repository.ParametroRepository;

@Service
public class MailServie {

    @Autowired
    private ParametroRepository parametroRepository;

   // @Autowired
    //private TemplateEngine templateEngine;


    public boolean sendEmail(String from, String to, String subject, String template,
            Context context) {
                try {
                    Properties props = System.getProperties();
                   // props.put("mail.transport.protocol", parametroRepository.findByClave("MAIL_PROTOCOL").getValor());
                    props.put("mail.smtp.port", Integer.parseInt(parametroRepository.findByClave("MAIL_PORT").getValor()));
                    props.put("mail.smtp.starttls.enable", parametroRepository.findByClave("MAIL_SMTP_STARTTLS").getValor());
                    //props.put("mail.smtp.ssl.protocols", parametroRepository.findByClave("MAIL_SMTP_SSL_PROTOCOLS").getValor());
                    props.put("mail.smtp.startls.required", "true");
                    props.put("mail.smtp.auth", parametroRepository.findByClave("MAIL_SMTP_AUTH").getValor());

                    Session session = Session.getDefaultInstance(props);

                    MimeMessage message = new MimeMessage(session);

                    MimeMessage msg = new MimeMessage(session);

                    MimeMessageHelper messageHelper = new MimeMessageHelper(msg, false);
                    messageHelper.setFrom(from);
                    messageHelper.setTo(to);
                    messageHelper.setSubject(subject);

                     
 	                String[] toIds = to.split(",");

 	                Address[] ia = new InternetAddress[toIds.length];
 	                int i = 0;
 	                for (String address : toIds) {
 	                    ia[i] = new InternetAddress(address);
 	                    i++;
 	                }

 	              message.addRecipients(RecipientType.TO, ia);

 	            
 	            //String content =  templateEngine.process(template, context); template html para correo

               // messageHelper.setText(content, true);
 	            
 	           // messageHelper.addInline("escudo", new ClassPathResource("/static/img/logo_pie.png"), "image/png");

                messageHelper.setText(template, false);
 	        
                Transport transport = session.getTransport();
	        	
	            System.out.println("Sending...");
	            System.out.println(parametroRepository.findByClave("MAIL_HOST").getValor());
	            System.out.println(parametroRepository.findByClave("MAIL_USERNAME").getValor());
	            System.out.println(parametroRepository.findByClave("MAIL_PASSWORD").getValor());

                transport.connect(parametroRepository.findByClave("MAIL_HOST").getValor(), 
                parametroRepository.findByClave("MAIL_USERNAME").getValor(),
                parametroRepository.findByClave("MAIL_PASSWORD").getValor());       	
	            
	            // Send the email.
	            transport.sendMessage(msg, msg.getAllRecipients());
	            System.out.println("Email sent!");
	            return true;
                    



                    
                } catch (Exception ex) {
                    System.out.println("The email was not sent.");
	                System.out.println("Error message: " + ex.getMessage());
                    ex.printStackTrace();
	                return false;
                }
    }

}
