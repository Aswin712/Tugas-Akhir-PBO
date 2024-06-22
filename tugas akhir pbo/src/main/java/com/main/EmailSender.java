package com.main;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static void sendLateReturnNotification(String studentEmail, double penaltyAmount) {
        final String username = "aswin2755@gmail.com"; // Ganti dengan email pengirim
        final String password = "1234567898."; // Ganti dengan password email pengirim

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentEmail));
            message.setSubject("Notifikasi Keterlambatan Pengembalian Buku");
            message.setText("Dear Mahasiswa,\n\n"
                    + "Anda telah terlambat mengembalikan buku. Denda yang harus Anda bayar: Rp " + penaltyAmount + "\n\n"
                    + "Terima kasih.");

            Transport.send(message);

            System.out.println("Email notifikasi berhasil dikirim ke " + studentEmail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
