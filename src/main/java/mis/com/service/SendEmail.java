package mis.com.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import mis.com.entity.EmailEntity;
import mis.com.repository.EmailRepository;
import mis.com.utils.DateUtils;

@Service
public class SendEmail {

	@Value("${spring.mail.username}")
	String userMail;
	@Value("${ftp.host.saveDailReport}")
	String saveDailyReport;
	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private EmailRepository emailRepository;

	public static final Logger Logger = LoggerFactory.getLogger(SendEmail.class);

	public String sendDailyReport(String yyyyMMdd, String htmlBody) {

		List<EmailEntity> emailList = null;
		@SuppressWarnings("unused")
		String status = "";
		List<String> toList = null;
		List<String> ccList = null;
		List<String> bccList = null;
		try {
			emailList = emailRepository.findAllByIsActiveAndReports("MISReport");
			System.out.println("From Mail To ....................................");
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
				// helper.setText(
				// "Hi Sir," + "\n\n" + "Here is your System Generated Report" + "\n\n" +
				// "Thanks & Regards.");
				helper.setText(htmlBody, true);
				helper.setSubject("FTD And MTD OBD Consumption Summary Report.");

				FileSystemResource file = new FileSystemResource(
						new File(saveDailyReport + "SummaryMISReport" + yyyyMMdd + ".xls"));
				System.out.println("File Name::" + file.getFilename());
				if (file.exists()) {
					helper.addAttachment(file.getFilename(), file);
					emailSender.send(message);
				} else {
					Logger.info("***** Give file Not Found:: *****" + saveDailyReport + "SummaryMISReport" + yyyyMMdd
							+ ".xls");

				}
				status = "Success";
			} else {
				Logger.info("***** E-Mail Not Found From DB *****");
			}
		} catch (Exception e) {
			Logger.info("*****Got Exception Given file:: *****" + saveDailyReport + "SummaryMISReport"
					+ DateUtils.getDateInString() + ".xls" + "  Message::" + e.getMessage());
			status = "Fail";
		}
		return status = "Success";
	}

	public String sendAlertOBDPanelReceivedReport(List<String> receivedIPReport) {

		List<EmailEntity> emailList = null;
		@SuppressWarnings("unused")
		String status = "";
		List<String> toList = null;
		List<String> ccList = null;
		List<String> bccList = null;
		try {
			emailList = emailRepository.findAllByIsActiveAndReports("Panel Alert");
			;
			System.out.println("From Mail To ....................................");
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
				helper.setSubject("FTD And MTD OBD Consumption Panel Alert.");
				helper.setText("Hi Support Team," + "\n\n" + "\n\n"
						+ "We Could Not Receieved As Given Below Panel Summary. Please Insert Before 8:00 AM" + "\n\n"
						+ Arrays.toString(receivedIPReport.toArray()) + "\n\n" + "Thanks & Regards.");

				emailSender.send(message);

				status = "Success";
			} else {
				Logger.info("***** E-Mail Not Found From DB *****");
			}
		} catch (Exception e) {
			Logger.info("***** Got Exception " + e.getMessage());
			return status = "Fail";
		}
		return status = "Success";
	}

	public String sendZeroAccountAlert(List<Map<String, String>> zeroAccount) {

		List<EmailEntity> emailList = null;
		@SuppressWarnings("unused")
		String status = "";
		List<String> toList = null;
		List<String> ccList = null;
		List<String> bccList = null;
		try {
			emailList = emailRepository.findAllByIsActiveAndReports("ZeroAC Alert");
			;
			System.out.println("sendZeroAccountAlert From Mail To ....................................");
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
				helper.setSubject("Update Zero Account USER_IP_MAPPING.");
				helper.setText("Hi Operation Team," + "\n\n" + "\n\n"
						+ "We Got Zero Account  USER IP MAPPING as given below." + "\n\n"
						+ new Gson().toJson(zeroAccount).toString() + "\n\n" + "Thanks & Regards.");

				emailSender.send(message);

				status = "Success";
			} else {
				Logger.info("***** E-Mail Not Found From DB *****");
			}
		} catch (Exception e) {
			Logger.info("***** Got Exception " + e.getMessage());
			return status = "Fail";
		}
		return status = "Success";
	}
}
