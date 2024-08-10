//package mis.com.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
////import com.spire.ms.System.Collections.ArrayList;
//
//import mis.com.entity.MisCallDetailSummaryReport;
//import mis.com.entity.UserIpMapping;
//import mis.com.exceptions.EntityNotFoundException;
//import mis.com.repository.MisCallDetailsSummaryRepository;
//import mis.com.repository.UserIPMappingRepository;
//import mis.com.utils.DateUtils;
//
//@Service
//public class ObdSummaryFormatSchedular {
//	public static final Logger Logger = LoggerFactory.getLogger(ObdSummaryFormatSchedular.class);
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
//	private ObdSummaryFtdMtdLmtdMisSummaryExcelGenerator2 excelGenerator;
//
//	@Autowired
//	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;
//
//	@Autowired
//	private MisSummarySQLDumpToExcelReport misCallDetailsSummaryMTDReport;
//	@Autowired
//	private ObdFormatConvertHTMLFormatToSendEmailBodyService2 convertHtmlService;
//	@Autowired
//	private SendEmail sendMail;
//
//	@Autowired
//	private UserIPMappingRepository userIPMappingRepository;
//
//	@Scheduled(cron = "0 25 8 * * *")
//	public void updateZeroAccountRecrod() {
//		List<MisCallDetailSummaryReport> zeroAccountList = null;
//		UserIpMapping userIpMapping = null;
//		try {
//			zeroAccountList = misCallDetailsSummaryRepository.getZeroAccountList();
//			if (Objects.nonNull(zeroAccountList)) {
//				Logger.info("***** UpdateZeroAccountRecrod() Got Zero Account List ******" + zeroAccountList.size());
//
//				for (MisCallDetailSummaryReport zeroAccount : zeroAccountList) {
//					if (zeroAccount.getUserName().equalsIgnoreCase("user2")
//							|| zeroAccount.getUserName().contains("admin")) {
//						userIpMapping = userIPMappingRepository.findFirstByIpAddressAndUserNameAndCampaignName(
//								zeroAccount.getIpAddress(), zeroAccount.getUserName(), zeroAccount.getCampaignName());
//					} else {
//						userIpMapping = userIPMappingRepository
//								.findFirstByIpAddressAndUserName(zeroAccount.getIpAddress(), zeroAccount.getUserName());
//					}
//					if (Objects.nonNull(userIpMapping)) {
//						if (zeroAccount.getIpAddress().equalsIgnoreCase("Panel-UAT70")) {
//							misCallDetailsSummaryRepository.updateZeroAccountList(zeroAccount.getIpAddress(),
//									zeroAccount.getUserName(), zeroAccount.getCampaignName(), "UAT70", "UAT70");
//						} else {
//
//							misCallDetailsSummaryRepository.updateZeroAccountList(zeroAccount.getIpAddress(),
//									zeroAccount.getUserName(), zeroAccount.getCampaignName(),
//									userIpMapping.getAccountUser(), userIpMapping.getUserType());
//						}
//					}
//				}
//			}
//		} catch (
//
//		Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Scheduled(cron = "0 20 8 * * *")
//	public void sendZeroAccountAlert() {
//		List<MisCallDetailSummaryReport> zeroAccountList = null;
//		List<Map<String, String>> receivedIP = new ArrayList();
//		Map<String, String> map = null;
//
//		zeroAccountList = misCallDetailsSummaryRepository.getZeroAccountList();
//		if (zeroAccountList.size() > 0) {
//			Logger.info("***** UpdateZeroAccountRecrod() Got Zero Account List ******" + zeroAccountList.size());
//			for (MisCallDetailSummaryReport zeroAccount : zeroAccountList) {
//				map = new HashMap<String, String>();
//				map.put("ip_ddress", zeroAccount.getIpAddress());
//				map.put("user_name", zeroAccount.getUserName());
//				map.put("campaign_name", zeroAccount.getCampaignName());
//				receivedIP.add(map);
//			}
//			sendMail.sendZeroAccountAlert(receivedIP);
//		}
//	}
//
//	@Scheduled(cron = "0 15 8 * * *")
//	public void readFileFromFtp() throws Exception {
//		readFileFromFtpOrSmtp();
//	}
//
//	@Scheduled(cron = "0 15 6 * * *")
//	public void readFileFromSmtp() throws Exception {
//		readFileFromFtpOrSmtp();
//	}
//
//	// @Scheduled(cron = "0 */1 * * * *")
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
//
//	// @Scheduled(cron = "0 */1 * * * *")
//	@Scheduled(cron = "0 30 8 * * *")
//	public void misToExcel() throws IOException, ParseException, InterruptedException, EntityNotFoundException {
//		LocalDate todaydate = LocalDate.now();
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String[] ftdColumns = { "Account Name", "User Name", "Total MSISDN", "Valid MSISDN", "Attempted Calls",
//				"Connected Calls", "Total Bill Sec.", "Credits Used", "MTD Total MSISDN", "MTD Valid MSISDN",
//				"MTD Attempted Calls", "MTD Connected Calls", "MTD Total Bill Sec.", "MTD Credits Used",
//				"LMTD Total MSISDN", "LMTD Valid MSISDN", "LMTD  Attempted Calls", "LMTD Connected Calls",
//				"LMTD Total Bill Sec.", "LMTD Credits Used", "", "FTD % SC", "MTD % SC",
//				"Diff in Credit MTD-LMTD / LMTD", "FTD Answered %", "MTD Answered %" };
//
//		System.out.println("Current Date, Current Month First Date, Last Month First Date And Last Month Last Date::"
//				+ DateUtils.getYesterdayDateString() + " And " + dateTimeFormatter.format(todaydate.withDayOfMonth(1))
//				+ " And " + DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1)))
//				+ " And " + DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));
//
//		List<Object[]> summaryReportList = misCallDetailsSummaryRepository.getObdSummaryReportOfFTDandMTDandLMTD(
//				DateUtils.getYesterdayDateString(), dateTimeFormatter.format(todaydate.withDayOfMonth(1)),
//				DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))),
//				DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));// (DateUtils.getDateInStringYYYYMMddd());
//		excelGenerator.misDbRecordToExcel(ftdColumns, summaryReportList, "FTD");
//
//		Thread.sleep(3000);
//		/**
//		 * Here First Will Be take dump then Create A Report From CurrentDate To First
//		 * Date Of Month
//		 */
//		misCallDetailsSummaryMTDReport.misCallDetailsSummaryMTDtoExcel();
//		/**
//		 * After Creating All Report As given above then mail will send on particular
//		 * mail
//		 */
//
//		Thread.sleep(3000);
//		sendMail.sendDailyReport(DateUtils.getYesterdayDateStringInyyyyMMddFormat(),
//				convertHtmlService.makeHtmlFormatForOBDReport());
//
//	}
//
//	// @Scheduled(cron = "0 0 6 * * *")
//	@Scheduled(cron = "0 20 8 * * *")
//	public void moveSummaryRecord() throws InterruptedException {
//		moveSummaryFolder();
//
//	}
//
//	@Scheduled(cron = "0 30 7 * * *")
//	public void sendAlertReceivedOBDPanelReport() throws Exception {
//		List<String> ipAddressList = null;
//		List<String> ipAddress = null;
//		List<String> receivedIP = new ArrayList();
//		try {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//			Logger.info("***** Schedular Started For Writing Data To sendAlertReceivedOBDPanelReport");
//			ipAddressList = userIPMappingRepository.findIpAddressByGroup();
//			ipAddress = misCallDetailsSummaryRepository.getIpAddressList(formatter.format(DateUtils.yesterday()));
//			for (int i = 0; i < ipAddressList.size(); i++) {
//				Logger.info("***** Schedular Started For Writing Data To sendAlertReceivedOBDPanelReport" + i);
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
//
//	@Scheduled(cron = "0 20 7 * * *")
//	public void moveSummaryDump() throws InterruptedException {
//		moveSummaryFolder();
//
//	}
//
//	public void moveSummaryFolder() throws InterruptedException {
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
//}
