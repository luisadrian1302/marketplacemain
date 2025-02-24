package com.example.marketplacemain.marketplacemain.autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destinatario, String asunto, String mensaje, String url) throws MessagingException {


        System.out.println(mailSender);

        String msg = "<!DOCTYPE html>\r\n" + //
                        "<html lang=\"en\">\r\n" + //
                        "<head>\r\n" + //
                        "    <meta charset=\"UTF-8\">\r\n" + //
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                        "    <title>Confirmación de Cuenta</title>\r\n" + //
                        "    <style>\r\n" + //
                        "        body {\r\n" + //
                        "            font-family: Arial, sans-serif;\r\n" + //
                        "            background-color: #f9f9f9;\r\n" + //
                        "            margin: 0;\r\n" + //
                        "            padding: 0;\r\n" + //
                        "        }\r\n" + //
                        "        .email-container {\r\n" + //
                        "            max-width: 600px;\r\n" + //
                        "            margin: 20px auto;\r\n" + //
                        "            background-color: #ffffff;\r\n" + //
                        "            border: 1px solid #ddd;\r\n" + //
                        "            border-radius: 8px;\r\n" + //
                        "            overflow: hidden;\r\n" + //
                        "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);\r\n" + //
                        "        }\r\n" + //
                        "        .email-header {\r\n" + //
                        "            background-color: #ffc107;\r\n" + //
                        "            color: #ffffff;\r\n" + //
                        "            text-align: center;\r\n" + //
                        "            padding: 20px;\r\n" + //
                        "        }\r\n" + //
                        "        .email-body {\r\n" + //
                        "            padding: 20px;\r\n" + //
                        "            color: #333333;\r\n" + //
                        "            line-height: 1.6;\r\n" + //
                        "        }\r\n" + //
                        "        .email-footer {\r\n" + //
                        "            text-align: center;\r\n" + //
                        "            padding: 10px;\r\n" + //
                        "            font-size: 12px;\r\n" + //
                        "            color: #888888;\r\n" + //
                        "            background-color: #f2f2f2;\r\n" + //
                        "        }\r\n" + //
                        "        .verification-code {\r\n" + //
                        "            font-size: 24px;\r\n" + //
                        "            font-weight: bold;\r\n" + //
                        "            color: #ffc107;\r\n" + //
                        "            margin: 20px 0;\r\n" + //
                        "            text-align: center;\r\n" + //
                        "        }\r\n" + //
                        "        .button {\r\n" + //
                        "            display: inline-block;\r\n" + //
                        "            margin: 20px auto;\r\n" + //
                        "            padding: 10px 20px;\r\n" + //
                        "            background-color: #ffc107;\r\n" + //
                        "            color: #ffffff;\r\n" + //
                        "            text-decoration: none;\r\n" + //
                        "            border-radius: 5px;\r\n" + //
                        "            text-align: center;\r\n" + //
                        "        }\r\n" + //
                        "        .button:hover {\r\n" + //
                        "            background-color: #ffc107;\r\n" + //
                        "        }\r\n" + //
                        "    </style>\r\n" + //
                        "</head>\r\n" + //
                        "<body>\r\n" + //
                        "    <div class=\"email-container\">\r\n" + //
                        "        <div class=\"email-header\">\r\n" + //
                        "            <h1>Confirmación de Cuenta</h1>\r\n" + //
                        "        </div>\r\n" + //
                        "        <div class=\"email-body\">\r\n" + //
                        "            <p>Hola,</p>\r\n" + //
                        "            <p>¡Gracias por registrarte! Para completar el proceso de registro, por favor verifica tu correo electrónico usando el código de verificación a continuación:</p>\r\n" + //
                        "            <div class=\"verification-code\">"+ mensaje +"</div>\r\n" + //
                        "            <p>O haz clic en el siguiente enlace para confirmar tu cuenta:</p>\r\n" + //
                        "            <a href=\""+url+"\" class=\"button\">Confirmar Cuenta</a>\r\n" + //
                        "            <p>Si no reconoces esta actividad, simplemente ignora este correo.</p>\r\n" + //
                        "        </div>\r\n" + //
                        "        <div class=\"email-footer\">\r\n" + //
                        "            <p>© 2024 Tu Empresa. Todos los derechos reservados.</p>\r\n" + //
                        "        </div>\r\n" + //
                        "    </div>\r\n" + //
                        "</body>\r\n" + //
                        "</html>\r\n" ;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(msg, true); // 'true' indica que el contenido es HTML
        helper.setFrom("lm0336172@gmail.com"); // Cambia esto por tu correo
        mailSender.send(mimeMessage);
    }
}

