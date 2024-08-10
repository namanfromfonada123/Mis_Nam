package mis.com.Naman.Mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import mis.com.entity.EmailEntity;
import mis.com.repository.EmailRepository;
import mis.com.utils.DateUtils;

@Configuration
public class EmailService {

	@Value("${spring.mail.host}")
    private String host; // SMTP server hostname
	
    @Value("${spring.mail.username}")
    private String userMail ; // SMTP username
    
    @Value("${spring.mail.password}")
    private String password; // SMTP password
    
    
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	public JavaMailSender emailSender;

	
	Logger Logger = LoggerFactory.getLogger(EmailService.class);
	
    public void sendEmailWithAttachment(String to, String subject, String htmlBody,File csvFile) throws MessagingException, IOException {
  
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

        // Set To: header field
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        
    	helper.setFrom(userMail);
	
		helper.setText(htmlBody, true);
		helper.setSubject(subject);

		
		// Create a ByteArrayResource from CSV content
//	    ByteArrayResource csvResource = new ByteArrayResource(csvContent) {
//	        @Override
//	        public String getFilename() {
//	            return filename;  // You can customize the filename if needed
//	        }
//	    };

		
		FileSystemResource file = new FileSystemResource(
				new File(csvFile.getPath()));
		System.out.println("File Name::" + file.getFilename());
		if (file.exists()) {
			helper.addAttachment(file.getFilename(), file);
			emailSender.send(message);
		} else {
			
			System.out.println("***** Give file Not Found:: *****" + csvFile.getPath());

		} 
		
//	    helper.addAttachment(csvResource.getFilename(), csvResource);
//	    emailSender.send(message);
        
        System.out.println("Sent message successfully....");
    }



public String userDailyReport(String subject, String htmlBody, File filepath, String Client) {

	List<EmailEntity> emailList = null;
	@SuppressWarnings("unused")
	String status = "";
	List<String> toList = null;
	List<String> ccList = null;
	List<String> bccList = null;
	try {
		emailList = emailRepository.findAllByIsActiveAndReports(Client.toLowerCase());
		System.out.println("From Mail To .................................... "+ emailList.toString());
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

		toList = emailList.stream().filter(to -> to.getMail_to() != null && !to.getMail_to().equals(""))
				.map(toMail -> toMail.getMail_to()).collect(Collectors.toList());
		if (emailList.size() > 0) {
			if (toList.size() > 0) {
				InternetAddress[] toAddress = new InternetAddress[toList.size()];

				int count1 = 0;

				for (String mailTo : toList) {
					System.out.println("Address To:: " + mailTo);
					toAddress[count1] = new InternetAddress(mailTo);

					count1++;

				}
				message.setRecipients(Message.RecipientType.TO, toAddress);
			}
			ccList = emailList.stream().filter(cc -> cc.getCc() != null && !cc.getCc().equals(""))
					.map(ccMail -> ccMail.getCc()).collect(Collectors.toList());

			if (ccList.size() > 0) {
				InternetAddress[] ccAddress = new InternetAddress[ccList.size()];

				int count1 = 0;

				for (String mailcc : ccList) {
					System.out.println("Address CC:: " + mailcc);
					ccAddress[count1] = new InternetAddress(mailcc);

					count1++;

				}
				message.setRecipients(Message.RecipientType.CC, ccAddress);

			}
			bccList = emailList.stream().filter(cc -> cc.getBcc() != null && !cc.getBcc().equals(""))
					.map(ccMail -> ccMail.getBcc()).collect(Collectors.toList());

			if (bccList.size() > 0) {
				InternetAddress[] bccAddress = new InternetAddress[ccList.size()];

				int count1 = 0;

				for (String mailbcc : bccList) {
					System.out.println("Address BCC:: " + mailbcc);
					bccAddress[count1] = new InternetAddress(mailbcc);

					count1++;

				}
				message.setRecipients(Message.RecipientType.BCC, bccAddress);

			}
			helper.setFrom(userMail);
			
			
			helper.setText(htmlBody, true);
			helper.setSubject(subject);

			FileSystemResource file = new FileSystemResource(
					new File(filepath.getPath()));
			System.out.println("File Name::" + file.getFilename());
			if (file.exists()) {
				helper.addAttachment(file.getFilename(), file);
				emailSender.send(message);
			} else {
				Logger.info("***** Give file Not Found:: *****" + filepath.getName());

			}
			status = "Success";
		} else {
			Logger.info("***** E-Mail Not Found From DB *****");
		}
	} catch (Exception e) {
		Logger.info("*****Got Exception Given file:: *****" + filepath.getName());
		status = "Fail";
	}
	return status = "Success";
}

}
