package mis.com.Naman.Mail;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import mis.com.entity.EmailEntity;
import mis.com.repository.EmailRepository;

@Configuration
public class EmailService {

	@Value("${spring.mail.host}")
	private String host; // SMTP server hostname

	@Value("${spring.mail.username}")
	private String userMail; // SMTP username

	@Value("${spring.mail.password}")
	private String password; // SMTP password

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	public JavaMailSender emailSender;

	@Value("${default.to}")
	String toString;

	@Value("${default.cc}")
	String ccString;

	@Value("${default.bcc}")
	String bccString;

	Logger Logger = LoggerFactory.getLogger(EmailService.class);

	public String userDailyReport(String subject, String htmlBody, File filepath, String Client) {

		List<EmailEntity> emailList = null;
		@SuppressWarnings("unused")
		String status = "";
		List<String> toList = null;
		List<String> ccList = null;
		List<String> bccList = null;
		try {

			emailList = emailRepository.findAllByIsActiveAndReports(Client.toLowerCase());

			System.out.println("From Mail To .................................... " + emailList.toString());
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

				FileSystemResource file = new FileSystemResource(new File(filepath.getPath()));
				System.out.println("File Name::" + file.getFilename());
				if (file.exists()) {
					helper.addAttachment(file.getFilename(), file);
					emailSender.send(message);
					
					Logger.info("Mail send to client Successfully ******************************");
				} else {
					Logger.info("***** Give file Not Found:: *****" + filepath.getName());

				}
				status = "Success";
			} else {

				System.out.println("From Mail To .................................... " + emailList.toString());

				status = "Success";

			}
		} catch (Exception e) {
			Logger.info("*****Got Exception Given file:: ***** " + filepath.getName()+ e.getLocalizedMessage());
			status = "Fail";
			
			e.printStackTrace();
		}
		return status;
	}

	public String userDailyReport2(String subject, String htmlBody, File filepath, String Client) {

		EmailEntity emailList = null;
		String status = "";

		try {

			emailList = emailRepository.findEmailByIsActiveAndReports(Client.toLowerCase());

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

			if (emailList != null) {

				System.out.println("From Mail To .................................... " + emailList.toString());

				String toListString = emailList.getMail_to();

				if (toListString != null) {
					String[] toList = toString.split(",");
					InternetAddress[] toAddress = new InternetAddress[toList.length];

					for (int i = 0; i < toList.length; i++)
						toAddress[i] = new InternetAddress(toList[i]);

					message.addRecipients(Message.RecipientType.TO, toAddress);
				}

				String ccListString = emailList.getCc();

				if (ccListString != null) {
					String[] ccList = ccString.split(",");
					InternetAddress[] ccAddress = new InternetAddress[ccList.length];

					for (int i = 0; i < ccList.length; i++)
						ccAddress[i] = new InternetAddress(ccList[i]);

					message.addRecipients(Message.RecipientType.CC, ccAddress);

				}

				String bccListString = emailList.getBcc();

				if (bccListString != null) {
					String[] bccList = bccString.split(",");
					InternetAddress[] bccAddress = new InternetAddress[bccList.length];

					for (int i = 0; i < bccList.length; i++)
						bccAddress[i] = new InternetAddress(bccList[i]);

					message.addRecipients(Message.RecipientType.BCC, bccAddress);

				}

				helper.setFrom(userMail);

				helper.setText(htmlBody, true);
				helper.setSubject(subject);

				FileSystemResource file = new FileSystemResource(new File(filepath.getPath()));
				System.out.println("File Name::" + file.getFilename());
				if (file.exists()) {
					helper.addAttachment(file.getFilename(), file);
					emailSender.send(message);
				} else {
					Logger.info("***** Give file Not Found:: *****" + filepath.getName());

				}
				status = "Success";
			} else {
				status = "Success";
				Logger.info("***** E-Mail Not Found From DB, So Message is Delivered to operations  *****");

//				this.SendAllClientMailtoOperation(subject, htmlBody, filepath, Client);
			}
		} catch (Exception e) {
			Logger.info("*****Got Exception Given file:: ***** " + filepath.getName());
			status = "Fail";
		}
		return status;
	}

	public String SendAllClientMailtoOperation(String subject, String htmlBody, File filepath, String Client) {

		Logger.info("Sending mail to Operation");
		String status = "";

		try {

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

			// splitting to mail data and storing it to InternetAddress array
			String[] toList = toString.split(",");
			InternetAddress[] toAddress = new InternetAddress[toList.length];

			for (int i = 0; i < toList.length; i++)
				toAddress[i] = new InternetAddress(toList[i]);

			message.addRecipients(Message.RecipientType.TO, toAddress);

			// splitting cc mail and storing it to InternetAddress array
			String[] ccList = ccString.split(",");
			InternetAddress[] ccAddress = new InternetAddress[ccList.length];

			for (int i = 0; i < ccList.length; i++)
				ccAddress[i] = new InternetAddress(ccList[i]);

			message.addRecipients(Message.RecipientType.CC, ccAddress);

			// splitting bcc mail and storing it to InternetAddress array
			String[] bccList = bccString.split(",");
			InternetAddress[] bccAddress = new InternetAddress[bccList.length];

			for (int i = 0; i < bccList.length; i++)
				bccAddress[i] = new InternetAddress(bccList[i]);

			message.addRecipients(Message.RecipientType.BCC, bccAddress);

			helper.setFrom(userMail);
			helper.setText(htmlBody, true);
			helper.setSubject(subject + " All Client Data : ");

			FileSystemResource file = new FileSystemResource(new File(filepath.getPath()));
			System.out.println("File Name::" + file.getFilename());
			if (file.exists()) {
				helper.addAttachment(file.getFilename(), file);
				emailSender.send(message);

				status = "Success";
			} else {
				Logger.info("***** Give file Not Found:: *****" + filepath.getName());
				status = "Success";

			}

		} catch (Exception e) {
			Logger.info("*****Got Exception Given file:: ***** " + filepath.getName() + e.getLocalizedMessage());
			
			status = "Fail";
			
			e.printStackTrace();
		}

		return status;
	}

}
