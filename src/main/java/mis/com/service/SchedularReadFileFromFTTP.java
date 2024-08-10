//package mis.com.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.spire.ms.System.Collections.ArrayList;
//
//import mis.com.exceptions.EntityNotFoundException;
//import mis.com.repository.MisCallDetailsSummaryRepository;
//import mis.com.repository.UserIPMappingRepository;
//import mis.com.utils.DateUtils;
//
//@Service
//public class SchedularReadFileFromFTTP {
//	public static final Logger Logger = LoggerFactory.getLogger(SchedularReadFileFromFTTP.class);
//
//	@Value("${ftp.host.file.path}")
//	String hostFilePath;
//	@Value("${ftp.host.username}")
//	String hostUserName;
//	@Value("${ftp.host.ip}")
//	String hostIp;
//	@Value("${ftp.host.port}")
//	String hostPort;
//	@Value("${ftp.host.password}")
//	String hostPassword;
//	@Value("${ftp.host.moveXlxFileFolder}")
//	String moveXlxFileFolder;
//
//	@Autowired
//	private MisUploadService misService;
//
//	@Autowired
//	private FtdMtdLmtdMisSummaryExcelGenerator excelGenerator;
//
//	@Autowired
//	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;
//
//	@Autowired
//	private MisSummarySQLDumpToExcelReport misCallDetailsSummaryMTDReport;
//	@Autowired
//	private ConvertHTMLFormatToSendEmailBodyService convertHtmlService;
//	@Autowired
//	private SendEmail sendMail;
//
//	@Autowired
//	private UserIPMappingRepository userIPMappingRepository;
//
//	// @Scheduled(cron = "0 */5 * * * *")
//	//@Scheduled(cron = "0 15 8 * * *")
//	public void readFileFromFtpOrSmtp() throws Exception {
//		try {
//			Logger.info("***** Schedular Started For Writing Data To MisCallDetailSummaryReport");
//			File folder = new File(hostFilePath);
//
//			if (folder.listFiles() != null) {
//				for (File fileEntry : folder.listFiles()) {
//					System.out.println("File Name::" + fileEntry.getName());
//					misService.createMisReport(fileEntry);
//
//				}
//			} else {
//				Logger.info("***** Could Not Fould Any Record As Given " + hostFilePath);
//			}
//		} catch (Exception e) {
//			Logger.info("***** Got Exception:: " + e.getMessage());
//			e.printStackTrace();
//		}
//		Logger.info("***** Schedular Successfully Ended For Writing Data To MisCallDetailSummaryReport");
//
//	}
//	//@Scheduled(cron = "0 30 10 * * *")
//	public void readsFileFromFtpOrSmtp() throws Exception {
//		try {
//			Logger.info("***** Schedular Started For Writing Data To MisCallDetailSummaryReport");
//			File folder = new File(hostFilePath);
//
//			if (folder.listFiles() != null) {
//				for (File fileEntry : folder.listFiles()) {
//					System.out.println("File Name::" + fileEntry.getName());
//					misService.createMisReport(fileEntry);
//
//				}
//			} else {
//				Logger.info("***** Could Not Fould Any Record As Given " + hostFilePath);
//			}
//		} catch (Exception e) {
//			Logger.info("***** Got Exception:: " + e.getMessage());
//			e.printStackTrace();
//		}
//		Logger.info("***** Schedular Successfully Ended For Writing Data To MisCallDetailSummaryReport");
//
//	}
//	// @Scheduled(cron = "0 */1 * * * *")
//	//@Scheduled(cron = "0 45 10 * * *")
//	public void misToExcel() throws IOException, ParseException, InterruptedException, EntityNotFoundException {
//
//		String[] reportArray = { "FTD", "MTD", "LMTD" };
//
//		String[] ftdColumns = { "Account Name", "User Name", "Total MSISDN", "Valid MSISDN", "Attempted Calls",
//				"Connected Calls", "Total Bill Sec.", "Credits Used", "FTD % SC" };
//		LocalDate todaydate = LocalDate.now();
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//		if (reportArray[0].equalsIgnoreCase("FTD")) {
//			List<Object[]> summaryReportList = misCallDetailsSummaryRepository
//					.getDailySummaryMisCallDetailsByCurrentDate(DateUtils.getYesterdayDateString());// (DateUtils.getDateInStringYYYYMMddd());
//			if (summaryReportList.size() > 0) {
//				excelGenerator.misDbRecordToExcel(ftdColumns, summaryReportList, "FTD");
//			} else {
//				Logger.info("Data Not Found As Given Date For FTD::" + DateUtils.getYesterdayDateString());
//			}
//		}
//
//		if (reportArray[1].equalsIgnoreCase("MTD")) {
//			String[] columns = { "Account Name", "User Name", "Total MSISDN", "Valid MSISDN", "Attempted Calls",
//					"Connected Calls", "Total Bill Sec.", "Credits Used", "MTD % SC" };
//			List<Object[]> summaryReportList = misCallDetailsSummaryRepository.getDailySummaryMisCallDetaiMonthly(
//					dateTimeFormatter.format(todaydate.withDayOfMonth(1)), DateUtils.getYesterdayDateString());
//			if (summaryReportList.size() > 0) {
//				excelGenerator.misDbRecordToExcel(columns, summaryReportList, "MTD");
//			} else {
//				Logger.info(
//						"Data Not Found As Given Date For MTD::" + dateTimeFormatter.format(todaydate.withDayOfMonth(1))
//								+ " And " + DateUtils.getYesterdayDateString());
//			}
//		}
//
//		if (reportArray[2].equalsIgnoreCase("LMTD")) {
//			String[] columns = { "Account Name", "User Name", "Total MSISDN", "Valid MSISDN", "Attempted Calls",
//					"Connected Calls", "Total Bill Sec.", "Credits Used", "LMTD % SC" };
//			List<Object[]> summaryReportList = misCallDetailsSummaryRepository.getDailySummaryMisCallDetaiMonthly
//
//			(DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))),
//					DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));
//
//			if (summaryReportList.size() > 0) {
//				excelGenerator.misDbRecordToExcel(columns, summaryReportList, "LMTD");
//			} else {
//				Logger.info("Data Not Found As Given Date For LMTD::"
//						+ DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))
//								+ " And " + DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString())));
//			}
//
//			Thread.sleep(3000);
//			/**
//			 * Here First Will Be take dump then Create A Report From CurrentDate To First
//			 * Date Of Month
//			 */
//			misCallDetailsSummaryMTDReport.misCallDetailsSummaryMTDtoExcel();
//			/**
//			 * After Creating All Report As given above then mail will send on particular
//			 * mail
//			 */
//			Thread.sleep(3000);
//			sendMail.sendDailyReport(DateUtils.getYesterdayDateStringInyyyyMMddFormat(),
//					convertHtmlService.makeHtmlFormatForOBDReport());
//		}
//
//	}
//
//	public List<String> listFilesForFolder(final File folder) {
//		List<String> filenames = new LinkedList<String>();
//		System.out.println("****** listFilesForFolder ********");
//
//		for (final File fileEntry : folder.listFiles()) {
//			if (fileEntry.isDirectory()) {
//				listFilesForFolder(fileEntry);
//			} else {
//				if (fileEntry.getName().contains(".csv"))
//					filenames.add(fileEntry.getName());
//			}
//		}
//		System.out.println("**********************::" + filenames);
//		return filenames;
//	}
//
//	// @Scheduled(cron = "0 0 6 * * *")
//	//@Scheduled(cron = "0 30 11 * * *")
//	public void moveFolder() throws InterruptedException {
//		/**
//		 * This Code is useful for move file one folder to another folder
//		 */
//		final File folder = new File(hostFilePath);
//		for (final File fileEntry : folder.listFiles()) {
//			System.out.println("File is moving ..............From:" + hostFilePath + ".....To::" + moveXlxFileFolder);
//
//			String fromFile = hostFilePath + fileEntry.getName();
//			String toFile = moveXlxFileFolder + fileEntry.getName();
//
//			Path source = Paths.get(fromFile);
//			Path target = Paths.get(toFile);
//
//			// rename or move a file to other path
//			// if target exists, throws FileAlreadyExistsException
//			try {
//				Files.move(source, target);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Thread.sleep(2000);
//		}
//	}
//
//	//@Scheduled(cron = "0 40 8 * * *")
//	public void movesFolder() throws InterruptedException {
//		/**
//		 * This Code is useful for move file one folder to another folder
//		 */
//		final File folder = new File(hostFilePath);
//		for (final File fileEntry : folder.listFiles()) {
//			System.out.println("File is moving ..............From:" + hostFilePath + ".....To::" + moveXlxFileFolder);
//
//			String fromFile = hostFilePath + fileEntry.getName();
//			String toFile = moveXlxFileFolder + fileEntry.getName();
//
//			Path source = Paths.get(fromFile);
//			Path target = Paths.get(toFile);
//
//			// rename or move a file to other path
//			// if target exists, throws FileAlreadyExistsException
//			try {
//				Files.move(source, target);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Thread.sleep(2000);
//		}
//	}
//
//	//@Scheduled(cron = "0 30 8 * * *")
//	public void sendAlertReceivedOBDPanelReport() throws Exception {
//		List<String> ipAddressList = null;
//		List<String> ipAddress = null;
//		List<String> receivedIP = new ArrayList();
//		try {
//			Logger.info("***** Schedular Started For Writing Data To sendAlertReceivedOBDPanelReport");
//			ipAddressList = userIPMappingRepository.findIpAddressByGroup();
//			ipAddress = misCallDetailsSummaryRepository.getIpAddressList(DateUtils.getYesterdayDateStringInyyyyMMddFormat());
//			for (int i = 0; i < ipAddressList.size(); i++) {
//				Logger.info("***** Schedular Started For Writing Data To sendAlertReceivedOBDPanelReport"+i);
//				if (ipAddress.contains(ipAddressList.get(i)))
//					continue;
//				else
//					receivedIP.add(ipAddressList.get(i));
//			}
//			if (ipAddress.size() > 0) {
//				Logger.info("***** Excepted Panel Report::" + Arrays.toString(ipAddressList.toArray()));
//				Logger.info("***** Received:: " + Arrays.toString(ipAddress.toArray()));
//				Logger.info("***** Mis Panel:: " + Arrays.toString(receivedIP.toArray()));
//
//				sendMail.sendAlertOBDPanelReceivedReport(receivedIP);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Logger.info("***** Schedular Successfully Ended For Writing Data To sendAlertReceivedOBDPanelReport");
//
//	}
//}
