package mis.com.Naman.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.nio.file.Path;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mis.com.Naman.Mail.EmailService;
import mis.com.Naman.pojos.ClientWiseReport;

@Component
public class ConvertAllClientDataToCsv2 {

	@Value("${Csv.filePath}")
	String folderPath;

	@Value("${Misis.Subject}")
	String emailSubject;

	@Autowired
	EmailService emailService;

	@Autowired
	ConvertHTMLFormatToSendEmail convertHTMLFormatToSendEmail;
	
	@Autowired
	ConvertAllClientMisToHtml clientMisToHtml;

	private static final String CSV_HEADER = "EXECUTION_DATE, CLIENT_NAME, USER_NAME, TOTAL_MSISDN, VALID_MSISDN, ATTEMPTED_CALLS, CONNECTED_CALLS, DIGIT_PRESSED, LISTEN_RATE, TOTAL_BILL_SEC, CREDIT_USED, PANEL_NAME\n";

	public String generateAllClientCsv(List<ClientWiseReport> ClientReportList, String Client)
			throws IOException, MessagingException, ParseException {
		StringBuilder csvContent = new StringBuilder();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Integer totlalMsisdn = 0;
		Integer totalValidMsisdn = 0;
		Integer totlaAttemptedCalls = 0;
		Integer totalConnectedCalls = 0;
		Integer totalBillSec = 0;
		Integer totalCreditsUser = 0;
		Integer totaldigitpressed = 0;
		double totallinstenrate = 0;
		String formattedDate="";
		String clientName="";

		for (ClientWiseReport clientReport : ClientReportList) {
			formattedDate = dateFormat.format(clientReport.getExecution_date());
			clientName=clientReport.getClientname();
			
			csvContent.append(formattedDate).append(",").append(clientReport.getClientname())
					.append(",").append(clientReport.getUsername()).append(",")
					.append(clientReport.getTotalmsisdn()).append(",").append(clientReport.getValidmsisdn()).append(",")
					.append(clientReport.getAttemptedcalls()).append(",").append(clientReport.getConnectedcalls())
					.append(",").append(clientReport.getDigitpressed()).append(",")
					.append(Math.round(clientReport.getListenrate()) + "%").append(",")
					.append(clientReport.getTotalbillsec()).append(",").append(clientReport.getCreditused()).append(",")
					.append(clientReport.getPanelname()).append("\n");

			totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(clientReport.getTotalmsisdn()));
			totalValidMsisdn = totalValidMsisdn + (int) Math.round(Double.valueOf(clientReport.getValidmsisdn()));
			totlaAttemptedCalls = totlaAttemptedCalls
					+ (int) Math.round(Double.valueOf(clientReport.getAttemptedcalls()));
			totalConnectedCalls = totalConnectedCalls
					+ (int) Math.round(Double.valueOf(clientReport.getConnectedcalls()));
			totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(clientReport.getTotalbillsec()));
			totalCreditsUser = totalCreditsUser + (int) Math.round(Double.valueOf(clientReport.getCreditused()));

			totaldigitpressed = totaldigitpressed + (int) Math.round(Double.valueOf(clientReport.getDigitpressed()));
			totallinstenrate = totallinstenrate + clientReport.getListenrate();
		}

		csvContent.append("Total").append(",").append("").append(",").append("").append(",").append(totlalMsisdn)
				.append(",").append(totalValidMsisdn).append(",").append(totlaAttemptedCalls).append(",")
				.append(totalConnectedCalls).append(",").append(totaldigitpressed).append(",")
				.append(Math.round(totallinstenrate / ClientReportList.size()) + "%").append(",").append(totalBillSec)
				.append(",").append(totalCreditsUser).append(",").append("").append("\n");

		String dateFolder = "ClientWiseUserSummaryReports_" + LocalDate.now().minusDays(1).toString(); // Format like "2024-08-09"
		
		Path dateFolderPath = Paths.get(folderPath, dateFolder);

		if (!Files.exists(dateFolderPath)) {
			Files.createDirectories(dateFolderPath);
		}

		// Construct the file path
		String fileName = Client + "_" + LocalDate.now().minusDays(1) + ".csv";
		Path filePath = dateFolderPath.resolve(fileName);

		if (Files.exists(filePath)) {
			// Append to the file
			try {
				Files.write(filePath, csvContent.toString().getBytes(StandardCharsets.UTF_8),
						StandardOpenOption.APPEND);
			} catch (IOException e) {
				throw new IOException("Failed to append data to file: " + fileName, e);
			}
		} else {
			// Create new file and write content
			try {
				Files.write(filePath, (CSV_HEADER + csvContent.toString()).getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				throw new IOException("Failed to create or write data to file: " + fileName, e);
			}
		}

		
		String bodytext = clientMisToHtml.makeHtmlFormatForOBDReport(Client, ClientReportList);
		emailService.SendAllClientMailtoOperation(emailSubject + " " + Client + " " + LocalDate.now().minusDays(1), bodytext, filePath.toFile(),
				Client);

		return fileName;
	}


}
