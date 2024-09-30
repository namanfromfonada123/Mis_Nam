package mis.com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;
import mis.com.common.Constants;
import mis.com.common.StringUtils;
import mis.com.entity.UserIpMapping;
import mis.com.repository.UserIPMappingRepository;
import mis.com.upload.db.msg.FileStorageService;
import mis.com.utils.CSVHelper;
import mis.com.utils.CSVUtils;

@Service
public class UserMappingUploadService {

	public static final Logger Logger = LoggerFactory.getLogger(UserMappingUploadService.class);
	
	@Autowired
	private UserIPMappingRepository userIpMappingRepository;
	@Autowired
	private FileStorageService storageService;
	
	@Async("processExecutor")
	public CompletableFuture<String> uploadUserIpMappingFile(MultipartFile file) {
		List<List<String>> dataList = new ArrayList<>();
		List<String[]> csvData = null;
		Logger.info("***** Started UserMappingUploadService.createMisReport() ******");
		List<List<String>> excelData = null;
		String response = "";
		try {
			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {
				Logger.info("***** UserMappingUploadService.uploadUserIpMappingFile() Inside CSV ******");
				csvData = CSVHelper.convertToCSVStringList(file.getInputStream());
				dataList = parseCsvFile(csvData);
				response = CSVHelper.validateHeader(csvData.get(0), Constants.USER_IP_MAPPING_SCHEMA_LENGTH);
			} else {
				Logger.info("***** UserMappingUploadService.uploadUserIpMappingFile() Inside Excel ******");
				excelData = excelToStringList(file, Constants.USER_IP_MAPPING_SCHEMA_LENGTH);
				response = CSVUtils.validateExcel(excelData, Constants.USER_IP_MAPPING_SCHEMA_LENGTH);
				dataList = parseExcelFile(excelData);

			}
			if (response.contains("Success")) {
				if (dataList.size() > 0) {
					Logger.info(
							"***** UserMappingUploadService.uploadUserIpMappingFile() Got Size After ParseExcelFile******::"
									+ dataList.size());
					response = createUserIpMapping(dataList, file);
					System.out.println("response::" + response);
					Logger.info(
							"***** UserMappingUploadService.uploadUserIpMappingFile() After Saving  Incoming Did List ******");

				}
			} else {
				response = Constants.REQUIRED_COLUMN_MSG + Constants.MIS_MAPPING_SCHEMA_LENGTH;
			}

		} catch (Exception ee) {
			response = "Fail To Store Given File." + ee.getMessage();
			ee.printStackTrace();
			// throw new RuntimeException("Fail To Store Given File." + ee.getMessage());
		}
		Logger.info("***** Successfully Executed UserMappingUploadService.uploadUserIpMappingFile() Got Response ******"
				+ response);
		return CompletableFuture.completedFuture(response);

		// return CompletableFuture.completedFuture(response);

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

	public static String convertDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public String createUserIpMapping(List<List<String>> dataList, MultipartFile file) {
		Logger.info("***** Started UserMappingUploadService.createUserIpMapping() ******");
		String response = "";
		UserIpMapping userIpMapping = null;
		List<UserIpMapping> userIpMappingList = new ArrayList<UserIpMapping>();
		int rowNumber = 0;
		try {
			for (List<String> row : dataList) {
				if (rowNumber == 0) {
					row.removeAll(Collections.singleton(""));
					rowNumber++;
				} else {
					if (userIpMappingRepository.findFirstByIpAddressAndUserNameAndCampaignName(row.get(2).trim(),
							row.get(0).trim(), row.get(1).trim()) == null) {
						userIpMapping = new UserIpMapping();
						userIpMapping.setUserName(row.get(0).trim());
						userIpMapping.setCampaignName(row.get(1).trim());
						userIpMapping.setIpAddress(row.get(2).trim());
						userIpMapping.setUserType(row.get(3).trim());
						userIpMapping.setAccountUser(row.get(4).trim());
						userIpMapping.setCreated_date(convertDateToString(new Date()));
						userIpMappingList.add(userIpMapping);
					}else {
						Logger.info("Already Exist:=>"+" Ip Address "+row.get(2).trim()+" User Name "+
								row.get(0).trim()+ "Campaign Name "+ row.get(1).trim());

					}
				}
			}
			if (userIpMappingList.size() > 0) {
				storageService.store(file);
				userIpMappingRepository.saveAll(userIpMappingList);
				response = "UserIpMappingFile Successfully Added.";
			} else {
				response = "UserIpMappingFile Could Not Saved! Try Again.";
			}

			Logger.info("*****UserMappingUploadService.createUserIpMapping() Response"
					+ new Gson().toJson(userIpMappingList).toString());
		} catch (Exception e) {
			e.printStackTrace();
			response = "Got Exception::" + e.getMessage();

		}
		return response;

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

	@SuppressWarnings("unused")
	public static List<List<String>> excelToStringList(MultipartFile is, Integer noOfLength) throws IOException {
		List<List<String>> dataSet = new ArrayList<>();
		// List<String> rowArray=new ArrayList();
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook(is.getInputStream());

			if (workbook == null) {
				workbook = new XSSFWorkbook(is.getInputStream());
			}
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			// Iterating over Rows and Columns using Java 8 forEach with lambda
			sheet.forEach(row -> {
				List<String> rowArray = new ArrayList<>();
				if (row.getLastCellNum() > noOfLength / 2) {
					/*
					 * because my excel sheet has max 5 columns, in case last column is empty then
					 * row.getLastCellNum() will
					 */
					int lastColumn = Math.max(row.getLastCellNum(), noOfLength);
					for (int cn = 0; cn < lastColumn; cn++) {
						Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						if (cell.getCellType().equals(CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell)) {
							try {
								String cellValue = new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
								rowArray.add(cellValue);
							} catch (Exception e) {
								rowArray.add(cell.getDateCellValue().toString());
							}
						} else {
							String cellValue = dataFormatter.formatCellValue(cell);
							rowArray.add(cellValue);
						}
					}
					dataSet.add(rowArray);
				}
			});

			workbook.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return dataSet;
	}

}
