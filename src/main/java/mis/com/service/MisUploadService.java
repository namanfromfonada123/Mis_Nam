package mis.com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;
import mis.com.common.Constants;
import mis.com.common.StringUtils;
import mis.com.entity.FileRecordCountLog;
import mis.com.entity.MisCallDetailSummaryReport;
import mis.com.entity.UserIpMapping;
import mis.com.repository.FileRecordCountRepository;
import mis.com.repository.MisCallDetailsSummaryRepository;
import mis.com.repository.UserIPMappingRepository;
import mis.com.utils.CSVHelper;
import mis.com.utils.CSVUtils;
import mis.com.utils.DateUtils;

@Service
public class MisUploadService {

	public static final Logger Logger = LoggerFactory.getLogger(MisUploadService.class);

	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	@Autowired
	private UserIPMappingRepository userIPMappingRepository;

	@Autowired
	private FileRecordCountRepository fileRecordCountRepository;

	@Async("processExecutor")
	public CompletableFuture<String> createMisReport(File file) {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started MisService.createMisReport() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getName().substring(file.getName().lastIndexOf('.') + 1))) {
				Logger.info("***** MisService.createMisReport() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(new FileInputStream(file));
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.MIS_MAPPING_SCHEMA_LENGTH);
			} else {
				Logger.info("***** MisService.createMisReport() Inside Excel ******");
				excelData = CSVUtils.excelToStringList(file, Constants.MIS_MAPPING_SCHEMA_LENGTH);
				response = CSVUtils.validateExcel(excelData, Constants.MIS_MAPPING_SCHEMA_LENGTH);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info("***** MisService.createMisReport() Got Size After ParseExcelFile******::"
							+ dataList.size());
					createMisCallDetailSummaryReport(dataList, file.getName());
					System.out.println("response::" + response);
					Logger.info("***** MisService.createMisReport() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.MIS_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store Given File." + ee.getMessage());
		}
		Logger.info("***** Successfully Executed MisService.createMisReport() Got Response ******" + response);

		return CompletableFuture.completedFuture(response);

	}

	private List<List<String>> parseExcelFile(List<List<String>> csvData) {
		return csvData.stream().filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	private List<List<String>> parseCsvFile(List<String[]> csvData) {
		return csvData.stream().map(row -> new ArrayList<>(Arrays.asList(row)))
				.filter(dataSet -> Boolean.FALSE.equals(dataSet.stream().allMatch(StringUtils::isBlank)))
				.collect(Collectors.toList());
	}

	@Transactional
	public void createMisCallDetailSummaryReport(List<List<String>> dataList, String fileName) {
		Logger.info("***** Started MisService.createMisCallDetailSummaryReport() ******");
		FileRecordCountLog fileRecordCount = null;
		MisCallDetailSummaryReport misCallDetailSummaryReport = null;
		List<MisCallDetailSummaryReport> listMisCall = new ArrayList<MisCallDetailSummaryReport>();
		int totalRecordCount = 0;
		int rowNumber = 0;
		// List<UserIpMapping> userIPMappingList = null;
		UserIpMapping userIpMapping = null;
		UserIpMapping mappingUserNameAndPaneluserIpMapping = null;
		try {
			// userIPMappingList = userIPMappingRepository.findAll();
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					//System.out.println(row.get(13).trim() + " IP  And Cam " + row.get(2).trim());
					if (row.get(1).trim().equalsIgnoreCase("user2")) {
						userIpMapping = userIPMappingRepository.findFirstByIpAddressAndUserNameAndCampaignName(
								row.get(13).trim(), "user2", row.get(2).trim());
					} else {
						userIpMapping = userIPMappingRepository.findFirstByIpAddressAndUserNameContainsAndCampaignName(
								row.get(13).trim(), "admin", row.get(2).trim());
					}
					// .collect(Collectors.toList());
					if (Objects.nonNull(userIpMapping)) {
						// System.out.println("Inserting UserIpMapping
						// Condition........................");

						misCallDetailSummaryReport = setMisCallDetailSummaryReport(row);
						misCallDetailSummaryReport.setUserType(userIpMapping.getUserType());
						misCallDetailSummaryReport.setAccountName(userIpMapping.getAccountUser());
						listMisCall.add(misCallDetailSummaryReport);
						totalRecordCount++;
					} else {
						if (Objects.isNull(userIpMapping)) {
							/*
							 * System.out.println(
							 * "Getting MappingUserNameAndPaneluserIpMapping Condition........................"
							 * );
							 */
							mappingUserNameAndPaneluserIpMapping = userIPMappingRepository
									.findFirstByIpAddressAndUserName(row.get(13).trim(), row.get(1).trim());

							if (Objects.nonNull(mappingUserNameAndPaneluserIpMapping)) {
								/*
								 * System.out.println(
								 * "Inserting MappingUserNameAndPaneluserIpMapping Present Condition........." +
								 * row.get(13).trim() + ":" + row.get(1).trim() + "...............");
								 */
								misCallDetailSummaryReport = setMisCallDetailSummaryReport(row);
								misCallDetailSummaryReport
										.setUserType(mappingUserNameAndPaneluserIpMapping.getUserType());
								misCallDetailSummaryReport
										.setAccountName(mappingUserNameAndPaneluserIpMapping.getAccountUser());
								listMisCall.add(misCallDetailSummaryReport);
								totalRecordCount++;
							} else {
								/*
								 * System.out.println(
								 * "Inserting MappingUserNameAndPaneluserIpMapping Zero Account........................"
								 * );
								 */ misCallDetailSummaryReport = setMisCallDetailSummaryReport(row);
								misCallDetailSummaryReport.setUserType("Zero User");
								misCallDetailSummaryReport.setAccountName("Zero Account");
								listMisCall.add(misCallDetailSummaryReport);
								totalRecordCount++;
							}

						}
					}

				}
			}
			if (listMisCall.size() > 0) {
				misCallDetailsSummaryRepository.saveAll(listMisCall);
			}
			if (totalRecordCount > 0) {
				Logger.info("*****MisService.createMisCallDetailSummaryReport() totalRecordCount::" + totalRecordCount);
				fileRecordCount = new FileRecordCountLog();
				fileRecordCount.setTotalCount(totalRecordCount);
				fileRecordCount.setCreatedDate(DateUtils.getStringDateInTimeZone(new Date()));
				fileRecordCount.setFileName(fileName);
				fileRecordCountRepository.save(fileRecordCount);
				Logger.info("***** Date Saved Successfully Inside File Record Count" + totalRecordCount);

			}
			Logger.info("*****MisService.createMisCallDetailSummaryReport() Response" + listMisCall.size());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	public List<String> listFilesForFolder(final File folder) throws IOException {
		List<String> filenames = new LinkedList<String>();
		System.out.println("****** listFilesForFolder ********");
		CSVReader reader = null;
		List<String[]> csvEntries = null;
		if (Objects.nonNull(folder.listFiles())) {
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry);
				} else {
					if (fileEntry.getName().contains(".csv"))
						filenames.add(fileEntry.getName());
					reader = new CSVReader(new InputStreamReader(new FileInputStream(fileEntry)),
							CSVReader.DEFAULT_SEPARATOR);
					csvEntries = reader.readAll();
					System.out.println(fileEntry.getName() + "::" + new Gson().toJson(csvEntries).toString());
				}
			}
		} else {
			System.out.println("File Does Not Exist.");
		}
		System.out.println("**********************::" + filenames);
		return filenames;
	}

	public MisCallDetailSummaryReport setMisCallDetailSummaryReport(List<String> row) {

		// Logger.info("***** Inside MisService.setMisCallDetailSummaryReport() *****");
		MisCallDetailSummaryReport misCallDetailSummaryReport = new MisCallDetailSummaryReport();
		try {
			misCallDetailSummaryReport.setCreateDate(DateUtils.getStringDateInTimeZone(new Date()));
			misCallDetailSummaryReport.setExecutionDt(row.get(0).trim());
			misCallDetailSummaryReport.setUserName(row.get(1).trim());
			misCallDetailSummaryReport.setCampaignName(row.get(02).trim());
			misCallDetailSummaryReport.setLeadName(row.get(3).trim());
			misCallDetailSummaryReport.setTotalMSISDN(row.get(4).trim());
			misCallDetailSummaryReport.setValidMSISDN(row.get(5).trim());
			misCallDetailSummaryReport.setNoOfRetry(row.get(6).trim());
			misCallDetailSummaryReport.setAttemptedCalls(row.get(7).trim());
			misCallDetailSummaryReport.setConnectedCalls(row.get(8).trim());
			misCallDetailSummaryReport.setDigitPressed(row.get(9).trim());
			misCallDetailSummaryReport.setListenRate(row.get(10).trim());
			misCallDetailSummaryReport.setTotalBillSec(row.get(11).trim());
			misCallDetailSummaryReport.setCreditUsed(row.get(12).trim());
			misCallDetailSummaryReport.setIpAddress(row.get(13).trim());
			// Logger.info("***** Successfully executed
			// MisService.setMisCallDetailSummaryReport() *****");
		} catch (Exception e) {
			Logger.info("Got Exception::" + e.getMessage());
		}
		return misCallDetailSummaryReport;

	}
}
