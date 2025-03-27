package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {

    private static final Properties mailProps = new Properties();

    // Khởi tạo static block để đọc file application.properties
    static {
        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file application.properties");
            }
            mailProps.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc file application.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Gửi email với nội dung văn bản thuần túy hoặc HTML.
     *
     * @param recipient Địa chỉ email người nhận
     * @param subject   Tiêu đề email
     * @param content   Nội dung email (có thể là văn bản thuần túy hoặc HTML)
     * @throws MessagingException Nếu có lỗi trong quá trình gửi email
     */
    public static void sendEmail(String recipient, String subject, String content) throws MessagingException {
        // Lấy thông tin cấu hình từ file properties
        String host = mailProps.getProperty("mail.host");
        String port = mailProps.getProperty("mail.port");
        String username = mailProps.getProperty("mail.username");
        String password = mailProps.getProperty("mail.password");
        boolean auth = Boolean.parseBoolean(mailProps.getProperty("mail.auth"));
        boolean starttlsEnable = Boolean.parseBoolean(mailProps.getProperty("mail.starttls.enable"));

        // Cấu hình SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);

        // Tạo session để gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Thiết lập thông tin người gửi
            message.setFrom(new InternetAddress(username));

            // Thiết lập người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Thiết lập tiêu đề email
            message.setSubject(subject);

            // Thiết lập nội dung email (hỗ trợ cả văn bản thuần túy và HTML)
            message.setContent(content, "text/html; charset=UTF-8");

            // Gửi email
            Transport.send(message);

            System.out.println("Email sent successfully to: " + recipient);
        } catch (MessagingException e) {
            // Xử lý lỗi
            System.err.println("Failed to send email: " + e.getMessage());
            throw e; // Ném lại lỗi để lớp gọi xử lý
        }
    }
}