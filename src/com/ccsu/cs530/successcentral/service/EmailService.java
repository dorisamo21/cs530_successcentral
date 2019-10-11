package com.ccsu.cs530.successcentral.service;

import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Map;
import java.util.Properties;

    public class EmailService {


        public static void sendPasswordResetEmail(String email, String token) {
            //Get properties object
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            String from = "successcentral@gmail.com";
            String password = "PASSWORD_HIDDEN";
            String sub = "Success Central Password Reset";

            //get Session
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from,password);
                        }
                    });

            //compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
                message.setSubject(sub);
                Multipart multipart = new MimeMultipart("alternative");
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("", "utf-8" );

                MimeBodyPart htmlPart = new MimeBodyPart();

                String html = "<head>\n" +
                        "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                        "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\n" +
                        "          crossorigin=\"anonymous\"\n" +
                        "    />\n" +
                        "    <link href=\"https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i\" rel=\"stylesheet\">\n" +
                        "</head>\n" +
                        "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n" +
                        "<div class=\"es-wrapper-color\" style=\"background-color:#F7F7F7;\">\n" +
                        "\n" +
                        "    <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\">\n" +
                        "        <tr style=\"border-collapse:collapse;\">\n" +
                        "            <td valign=\"top\" style=\"padding:0;Margin:0;\">\n" +
                        "                <br>\n" +
                        "                   You have received this email, because you initiated a password reset with Success Central. To continue with the process, please click <a href=\"http://localhost:8080/scapp/reset_password?token=" + token + "\">here</a>" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</div>" +
                        "</body>";

                htmlPart.setContent(html, "text/html; charset=utf-8");


                multipart.addBodyPart( textPart );
                multipart.addBodyPart( htmlPart );
                message.setContent(multipart);
                message.saveChanges();
                Transport.send(message);
                System.out.println("message sent successfully");

            } catch (MessagingException e) {throw new RuntimeException(e);}


        }

        public static void sendEmailToApproved(String to){
            //Get properties object
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            String from = "successcentral123@gmail.com";
            String password = "ehfltkah21";
            String sub = "You are now accepted as a mentor";

            //get Session
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from,password);
                        }
                    });
            //compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
                message.setSubject(sub);
                Multipart multipart = new MimeMultipart("alternative");
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("", "utf-8" );

                MimeBodyPart htmlPart = new MimeBodyPart();
                String approvedHtml = returnMessage(to);

                htmlPart.setContent(approvedHtml, "text/html; charset=utf-8");


                multipart.addBodyPart( textPart );
                multipart.addBodyPart( htmlPart );
                message.setContent(multipart);
                message.saveChanges();
                Transport.send(message);
                System.out.println("message sent successfully");

            } catch (MessagingException e) {throw new RuntimeException(e);}
        }


        public static void matchNoticeToMentor(Mentor mentor, List<Mentee> myMentees, Map<Integer, String> majors){
            //Get properties object

            //Sending email to mentor
            String to_mentor = mentor.getEmail();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            String from = "successcentral123@gmail.com";
            String password = "ehfltkah21";
            String sub1 = "You have been matched with a mentee";

            //get Session
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from,password);
                        }
                    });
            //compose message
            try {
                MimeMessage message1 = new MimeMessage(session);
                message1.addRecipient(Message.RecipientType.TO,new InternetAddress(to_mentor));
                message1.setSubject(sub1);
                Multipart multipart1 = new MimeMultipart("alternative");
                MimeBodyPart textPart1 = new MimeBodyPart();
                textPart1.setText("", "utf-8" );

                MimeBodyPart htmlPart1 = new MimeBodyPart();

                String matchedHtml1 = matchingMessageToMentor(myMentees,majors);

                htmlPart1.setContent(matchedHtml1, "text/html; charset=utf-8");

                multipart1.addBodyPart( textPart1 );
                multipart1.addBodyPart( htmlPart1 );
                message1.setContent(multipart1);
                message1.saveChanges();
                Transport.send(message1);

                System.out.println("message sent successfully");

            } catch (MessagingException e) {throw new RuntimeException(e);}

        }

        public static void matchNoticeToMentee(Mentee mentee, Mentor mentor){
            //Get properties object

            //Sending email to mentor
            String to_mentee = mentee.getEmail();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            String from = "successcentral123@gmail.com";
            String password = "ehfltkah21";
            String sub2 = "You have been matched with a mentor";

            //get Session
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from,password);
                        }
                    });
            //compose message
            try {
                // Sending message to mentee
                MimeMessage message2 = new MimeMessage(session);
                message2.addRecipient(Message.RecipientType.TO,new InternetAddress(to_mentee));
                message2.setSubject(sub2);
                Multipart multipart2 = new MimeMultipart("alternative");
                MimeBodyPart textPart2 = new MimeBodyPart();
                textPart2.setText("", "utf-8" );

                MimeBodyPart htmlPart2 = new MimeBodyPart();
                String matchedHtml2 = matchingMessageToMentee(mentor,mentee);

                htmlPart2.setContent(matchedHtml2, "text/html; charset=utf-8");


                multipart2.addBodyPart( textPart2 );
                multipart2.addBodyPart( htmlPart2 );
                message2.setContent(multipart2);
                message2.saveChanges();
                Transport.send(message2);

                System.out.println("message sent successfully");

            } catch (MessagingException e) {throw new RuntimeException(e);}

        }





        public static String matchingMessageToMentor (List<Mentee> mentee, Map<Integer, String> majors){

            String msg = "<head>\n" +
                    "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                    "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\n" +
                    "          crossorigin=\"anonymous\"\n" +
                    "    />\n" +
                    "    <link href=\"https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i\" rel=\"stylesheet\">\n" +
                    "</head>\n" +
                    "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n" +
                    "<div class=\"es-wrapper-color\" style=\"background-color:#F7F7F7;\">\n" +
                    "\n" +
                    "    <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\">\n" +
                    "        <tr style=\"border-collapse:collapse;\">\n" +
                    "            <td valign=\"top\" style=\"padding:0;Margin:0;\">\n" +
                    "                <br>\n" +
                    "                <table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#3D5CA3;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#3d5ca3\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#3D5CA3;\" bgcolor=\"#3d5ca3\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"560\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td>\n" +
                    "                                                                <div class=\"container text-center\">\n" +
                    "                                                                    <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#FFFFFF;\">\n" +
                    "                                                                        <b>CCSU Success Central</b></h3>\n" +
                    "                                                                </div>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;display:none;\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FAFAFA;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fafafa\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-repeat:no-repeat;\" align=\"left\">\n" +
                    "                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"560\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:20px;\"> <h1 style=\"Margin:0;line-height:60px;mso-line-height-rule:exactly;font-family:lora, georgia, 'times new roman', serif;font-size:50px;font-style:normal;font-weight:normal;color:#333333;\"><em>You have been assigned a mentee.</em></h1> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                                <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#3D5CA3;\">Here's information about your new mentee(s):</h3>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table border=0 class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;\">\n" +
                    "                                        <table width=\"100%\">\n" ;
            for(int i=0;i<mentee.size();i++) {
                msg +=
                        "                                            <tr style=\"border-collapse:collapse;\">\n" +
                                "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"> <h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#333333;\">Name : " + mentee.get(i).getFirstName() + " " + mentee.get(i).getLastName() + "</h4> </td>\n" +
                                "                                            </tr>\n" +
                                "                                            <tr style=\"border-collapse:collapse;\">\n" +
                                "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">" + mentee.get(i).getEmail() + "</p> </td>\n" +
                                "                                            </tr>\n" +
                                "                                            <tr style=\"border-collapse:collapse;\">\n" +
                                "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\"> Major : " + mentee.get(i).getMajor() + "</p> </td>\n" +
                                "                                            </tr>\n"+
                                "                                             <tr height=20>&nbsp;<td></td></tr>";
            }
                    msg +=
                                "                                            <tr style=\"border-collapse:collapse;\">\n" +
                                "                                                <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\"><span class=\"product-description\">Make sure that you would reach to your mentee as soon as possible.</span></p> </td>\n" +
                                "                                            </tr>\n" +

                                "                                            <tr style=\"border-collapse:collapse;\">\n" +
                                "                                                <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\"><span class=\"product-description\">You can check the information about your mentee in success central website. <a href=\"http://localhost:8080/scapp/home\"><button>Go</button></a></span></p> </td>\n" +
                                "                                            </tr>\n" +
                                "                                        </table>\n" +
                                "                                    </td>\n" +
                                "\n" +
                                "                                </tr>\n"+
                    "                                <tr><td>&nbsp;</td></tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:15px;padding-bottom:15px;background-color:#F7C052;\" bgcolor=\"#f7c052\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"580\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"202\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p0r es-m-p20b\" width=\"182\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/39911527588288171.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">R.C. Vance Academic Center 20801 </p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                                <td class=\"es-hidden\" width=\"20\" style=\"padding:0;Margin:0;\"></td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/35681527588356492.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td esdev-links-color=\"#ffffff\" align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#FFFFFF;\"><a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;text-decoration:underline;color:#FFFFFF;\" href=\"mailto:simmonsred@ccsu.edu\">simmonsred@ccsu.edu</a></p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/50681527588357616.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">1-860-832-3134</p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "</div>";
            return msg;
        }

        public static String matchingMessageToMentee(Mentor mentor, Mentee mentee){

            String msg = "\n" +
                    "<head>\n" +
                    "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                    "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\n" +
                    "          crossorigin=\"anonymous\"\n" +
                    "    />\n" +
                    "    <link href=\"https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i\" rel=\"stylesheet\">\n" +
                    "</head>\n" +
                    "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n" +
                    "<div class=\"es-wrapper-color\" style=\"background-color:#F7F7F7;\">\n" +
                    "\n" +
                    "    <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\">\n" +
                    "        <tr style=\"border-collapse:collapse;\">\n" +
                    "            <td valign=\"top\" style=\"padding:0;Margin:0;\">\n" +
                    "                <br>\n" +
                    "                <table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#3D5CA3;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#3d5ca3\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#3D5CA3;\" bgcolor=\"#3d5ca3\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"560\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td>\n" +
                    "                                                                <div class=\"container text-center\">\n" +
                    "                                                                    <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#FFFFFF;\">\n" +
                    "                                                                        <b>CCSU Success Central</b></h3>\n" +
                    "                                                                </div>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;display:none;\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FAFAFA;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fafafa\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-repeat:no-repeat;\" align=\"left\">\n" +
                    "                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"560\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:20px;\"> <h1 style=\"Margin:0;line-height:60px;mso-line-height-rule:exactly;font-family:lora, georgia, 'times new roman', serif;font-size:50px;font-style:normal;font-weight:normal;color:#333333;\"><em>You have a mentor now.</em></h1> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;\"> <h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#333333;\">Meet your new mentor.</h4> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">Thank you for applying to Success Central.&nbsp;<br>We have many mentors in our program;</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">we have paired you with a mentor believe will be a good match. </p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                                <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#3D5CA3;\">Here's information about your mentor:</h3>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table border=0 class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;\">\n" +
                    "                                        <table width=\"100%\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"> <h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#333333;\">Name : " + mentor.getFirstName()+" "+mentor.getLastName()+"</h4> </td>\n"+

                    "                                            </tr>\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">"+mentor.getEmail()+"</p> </td>\n" +
                    "                                            </tr>\n" +
                    "\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\"><span class=\"product-description\">Your mentor will reach you.</span></p> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                    </td>\n" +
                    "\n" +
                    "                                </tr>\n" +
                    "                                <tr><td>&nbsp;</td></tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:15px;padding-bottom:15px;background-color:#F7C052;\" bgcolor=\"#f7c052\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"580\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"202\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p0r es-m-p20b\" width=\"182\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/39911527588288171.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">R.C. Vance Academic Center 20801 </p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                                <td class=\"es-hidden\" width=\"20\" style=\"padding:0;Margin:0;\"></td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/35681527588356492.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td esdev-links-color=\"#ffffff\" align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#FFFFFF;\"><a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;text-decoration:underline;color:#FFFFFF;\" href=\"mailto:simmonsred@ccsu.edu\">simmonsred@ccsu.edu</a></p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/50681527588357616.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">1-860-832-3134</p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            return msg;
        }

        public static String returnMessage(String email){
            String msg = "<head>\n" +
                    "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                    "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\n" +
                    "          crossorigin=\"anonymous\"\n" +
                    "    />\n" +
                    "    <link href=\"https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i\" rel=\"stylesheet\">\n" +
                    "</head>\n" +
                    "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n" +
                    "<div class=\"es-wrapper-color\" style=\"background-color:#F7F7F7;\">\n" +
                    "\n" +
                    "    <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\">\n" +
                    "        <tr style=\"border-collapse:collapse;\">\n" +
                    "            <td valign=\"top\" style=\"padding:0;Margin:0;\">\n" +
                    "                <br>\n" +
                    "                <table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#3D5CA3;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#3d5ca3\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;background-color:#3D5CA3;\" bgcolor=\"#3d5ca3\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"560\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td>\n" +
                    "                                                                <div class=\"container text-center\">\n" +
                    "                                                                    <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#FFFFFF;\">\n" +
                    "                                                                        <b>CCSU Success Central</b></h3>\n" +
                    "                                                                </div>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"270\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"270\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;display:none;\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FAFAFA;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fafafa\" align=\"center\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-repeat:no-repeat;\" align=\"left\">\n" +
                    "                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"560\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:20px;\"> <h1 style=\"Margin:0;line-height:60px;mso-line-height-rule:exactly;font-family:lora, georgia, 'times new roman', serif;font-size:50px;font-style:normal;font-weight:normal;color:#333333;\"><em>Welcome</em></h1> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;\"> <h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#333333;\">We're happy to have you with us.</h4> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">Thank you for submitting your application to become a mentor.&nbsp;<br>You are accepted as a mentor.</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">Now you can go and register.</p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table border=0 class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-bottom:5px;padding-left:20px;padding-right:20px;padding-top:30px;background-color:#FFFFFF;background-repeat:no-repeat;\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                    "                                        <h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#3D5CA3;\">Here's how to get started:</h3>\n" +
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;\">\n" +
                    "                                        <table width=\"100%\">\n" +
                    "                                           <!-- <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;padding-top:15px;\"> <img src=\"https://lsa2019.ucdavis.edu/wp-content/uploads/sites/457/2019/01/Screen-Shot-2019-01-15-at-7.25.48-AM.png\" alt=\"Fill out a profile\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" title=\"Fill out a profile\" width=\"39\"></td>\n" +
                    "                                            </tr>-->\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;\"> <h4 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;color:#333333;\">Register as a mentor</h4> </td>\n" +
                    "                                            </tr>\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\"><span class=\"product-description\">You can click here to register</span></p> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                    </td>\n" +
                    "\n" +
                    "                                </tr>\n" +
                    "\n" +
                    "\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-left:20px;padding-right:20px;padding-bottom:30px;\">\n" +
                    "                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"560\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;\"> <span class=\"es-button-border\" style=\"border-style:solid;border-color:#3D5CA3;background:#FFFFFF;border-width:2px;display:inline-block;border-radius:4px;width:auto;\"> <a href=\"http://localhost:8080/scapp/register?email=" + email;


            msg += "\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:16px;color:#3D5CA3;border-style:solid;border-color:#FFFFFF;border-width:10px 15px 10px 15px;display:inline-block;background:#FFFFFF;border-radius:4px;font-weight:normal;font-style:normal;line-height:19px;width:auto;text-align:center;\">Let's get started »</a> </span> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                    "                    <tr style=\"border-collapse:collapse;\">\n" +
                    "                        <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                            <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\">\n" +
                    "                                <tr style=\"border-collapse:collapse;\">\n" +
                    "                                    <td style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:15px;padding-bottom:15px;background-color:#F7C052;\" bgcolor=\"#f7c052\" align=\"left\">\n" +
                    "                                        <!--[if mso]><table width=\"580\" cellpadding=\"0\"\n" +
                    "                                                            cellspacing=\"0\"><tr><td width=\"202\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p0r es-m-p20b\" width=\"182\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/39911527588288171.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">R.C. Vance Academic Center 20801 </p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                                <td class=\"es-hidden\" width=\"20\" style=\"padding:0;Margin:0;\"></td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td class=\"es-m-p20b\" width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/35681527588356492.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td esdev-links-color=\"#ffffff\" align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#FFFFFF;\"><a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;text-decoration:underline;color:#FFFFFF;\" href=\"mailto:simmonsred@ccsu.edu\">simmonsred@ccsu.edu</a></p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"179\" valign=\"top\"><![endif]-->\n" +
                    "                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                    "                                            <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                <td width=\"179\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                    "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:5px;\"> <img src=\"https://ygewk.stripocdn.email/content/guids/CABINET_66498ea076b5d00c6f9553055acdb37a/images/50681527588357616.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\" width=\"24\"></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr style=\"border-collapse:collapse;\">\n" +
                    "                                                            <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#FFFFFF;\">1-860-832-3134</p> </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </table> </td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                        <!--[if mso]></td></tr></table><![endif]--> </td>\n" +
                    "                                </tr>\n" +
                    "                            </table> </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "</div>";

            return msg;
        }

    }
