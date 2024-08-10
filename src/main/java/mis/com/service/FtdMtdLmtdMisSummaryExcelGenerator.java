package mis.com.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import mis.com.repository.MisCallDetailsSummaryRepository;
import mis.com.utils.DateUtils;

@Service
@EnableScheduling
public class FtdMtdLmtdMisSummaryExcelGenerator {
	public static final Logger Logger = LoggerFactory.getLogger(FtdMtdLmtdMisSummaryExcelGenerator.class);

	@Value("${ftp.host.saveDailReport}")
	String saveDailyReport;

	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	public void misDbRecordToExcel(String[] columns, List<Object[]> summaryReportList, String sheetName) {
		Logger.info("***** For Creating-- " + sheetName + " --Report.");
		Workbook workbook = null;
		Row affiliateRow = null;
		OutputStream out = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		try {
			DecimalFormat value = new DecimalFormat("#.#");
			workbook = new XSSFWorkbook();
			String excelsheet = saveDailyReport + "/SummaryMISReport" + DateUtils.getYesterdayDateStringInyyyyMMddFormat() + ".xls";
			Logger.info("***** Excel Creating Path inside misDbRecordToExcel::" + excelsheet);
			if (sheetName.contains("FTD")) {
				out = new FileOutputStream(excelsheet);
				sheet = workbook.createSheet(sheetName);
			} else {
				inputStream = new FileInputStream(excelsheet);
				workbook = WorkbookFactory.create(inputStream);
				out = new FileOutputStream(excelsheet);
				sheet = workbook.createSheet(sheetName);
			}
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
			headerCellStyle.setBorderLeft(BorderStyle.THIN);
			headerCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// headerCellStyle.setVerticalAlignment();
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));
			sheet.setHorizontallyCenter(true);
//			sheet.autoSizeColumn(6);

			// Row for Header-->
			Row headerRow1 = sheet.createRow(0);
			Cell userCell = headerRow1.createCell(00);
			userCell.setCellValue("User Details");
			userCell.setCellStyle(headerCellStyle);

			Cell dateCell = headerRow1.createCell(02);

			if (sheetName.equalsIgnoreCase("FTD")) {
				dateCell.setCellValue(DateUtils.getYesterdayDateString());//
			} else if (sheetName.equalsIgnoreCase("MTD")) {
				dateCell.setCellValue("MTD");//
			} else if (sheetName.equalsIgnoreCase("LMTD")) {
				dateCell.setCellValue("LMTD");//
			}

			dateCell.setCellStyle(headerCellStyle);

			int x = 1;
			Row headerRow = sheet.createRow(x);
			// headerRow.setHeight((short) 800);

			// Header
			for (int col = 0; col < columns.length; col++) {
				sheet.setColumnWidth(col, 4000);
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(columns[col]);
				cell.setCellStyle(headerCellStyle);
			}
			if (summaryReportList.size() > 0) {
				int rowIdx = 2;
				List<String> accountNameList = misCallDetailsSummaryRepository.getAccountList();
				if (accountNameList.size() > 0) {
					for (String account : accountNameList) {
						List<Object[]> accountList = summaryReportList.parallelStream()
								.filter(predicate -> predicate[0].equals(account)).collect(Collectors.toList());
						if (accountList.size() > 0) {
							Logger.info("***** Inserting " + account + " Record Inside Excel****");
							Integer totlalMsisdn = 0;
							Integer totalValidMsisdn = 0;
							Integer totlaAttemptedCalls = 0;
							Integer totalConnectedCalls = 0;
							Integer totalBillSec = 0;
							Integer totalCreditsUser = 0;
							for (Object[] summary : accountList) {
								Row row = sheet.createRow(rowIdx++);
								row.createCell(0).setCellValue(summary[0].toString());
								row.createCell(1).setCellValue(summary[1].toString());
								row.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
								row.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
								row.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
								row.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
								row.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
								row.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
								if (summary[3].equals(0.0)) {
									row.createCell(8).setCellValue(0 + "%");
								} else {
									row.createCell(8).setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
								}

								totlalMsisdn = totlalMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
								totalValidMsisdn = totalValidMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
								totlaAttemptedCalls = totlaAttemptedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
								totalConnectedCalls = totalConnectedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
								totalBillSec = totalBillSec
										+ (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
								totalCreditsUser = totalCreditsUser
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

								rowIdx = rowIdx++;
							}
							affiliateRow = sheet.createRow(rowIdx);
							Cell affiliateCell = affiliateRow.createCell(1);
							affiliateCell.setCellValue(account);
							affiliateCell.setCellStyle(headerCellStyle);
							affiliateRow.createCell(2).setCellValue(totlalMsisdn);
							affiliateRow.createCell(3).setCellValue(totalValidMsisdn);
							affiliateRow.createCell(4).setCellValue(totlaAttemptedCalls);
							affiliateRow.createCell(5).setCellValue(totalConnectedCalls);
							affiliateRow.createCell(6).setCellValue(totalBillSec);
							affiliateRow.createCell(7).setCellValue(totalCreditsUser);
							affiliateRow.createCell(8).setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
							rowIdx = 1 + affiliateRow.getRowNum();
						}

						Logger.info(
								"***** Successfully Executed FtdMtdLmtdMisSummaryExcelGenerator.misDbRecordToExcel() ****");
					}
				}
			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
