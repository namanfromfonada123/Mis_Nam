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
public class ObdSummaryFtdMtdLmtdMisSummaryExcelGenerator2 {
	public static final Logger Logger = LoggerFactory.getLogger(ObdSummaryFtdMtdLmtdMisSummaryExcelGenerator2.class);

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
			String excelsheet = saveDailyReport + "/SummaryMISReport"
					+ DateUtils.getYesterdayDateStringInyyyyMMddFormat() + ".xls";
			Logger.info("***** Excel Creating Path inside misDbRecordToExcel::" + excelsheet);
			if (sheetName.contains("FTD")) {
				out = new FileOutputStream(excelsheet);
				sheet = workbook.createSheet("OBD Summary");
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
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 7));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 13));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 19));

			sheet.setHorizontallyCenter(true);
//			sheet.autoSizeColumn(6);

			// Row for Header-->
			Row headerRow1 = sheet.createRow(0);
		/*	Cell userCell = headerRow1.createCell(00);
			userCell.setCellValue("User Details");
			userCell.setCellStyle(headerCellStyle);

			Cell ftd = headerRow1.createCell(02);

			ftd.setCellValue("FTD");//
			ftd.setCellStyle(headerCellStyle);

			Cell mtd = headerRow1.createCell(03);
			mtd.setCellValue("MTD");//
			mtd.setCellStyle(headerCellStyle);

			Cell lmtd = headerRow1.createCell(04);
			lmtd.setCellValue("LMTD");//
			lmtd.setCellStyle(headerCellStyle); */
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
							Integer ftdtotlalMsisdn = 0;
							Integer ftdtotalValidMsisdn = 0;
							Integer ftdtotlaAttemptedCalls = 0;
							Integer ftdtotalConnectedCalls = 0;
							Integer ftdtotalBillSec = 0;
							Integer ftdtotalCreditsUser = 0;
							Integer mtdtotlalMsisdn = 0;
							Integer mtdtotalValidMsisdn = 0;
							Integer mtdtotlaAttemptedCalls = 0;
							Integer mtdtotalConnectedCalls = 0;
							Integer mtdtotalBillSec = 0;
							Integer mtdtotalCreditsUser = 0;
							Integer lmtdtotlalMsisdn = 0;
							Integer lmtdtotalValidMsisdn = 0;
							Integer lmtdtotlaAttemptedCalls = 0;
							Integer lmtdtotalConnectedCalls = 0;
							Integer lmtdtotalBillSec = 0;
							Integer lmtdtotalCreditsUser = 0;
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
								row.createCell(8).setCellValue(Double.valueOf(String.valueOf(summary[8])));
								row.createCell(9).setCellValue(Double.valueOf(String.valueOf(summary[9])));
								row.createCell(10).setCellValue(Double.valueOf(String.valueOf(summary[10])));
								row.createCell(11).setCellValue(Double.valueOf(String.valueOf(summary[11])));
								row.createCell(12).setCellValue(Double.valueOf(String.valueOf(summary[12])));
								row.createCell(13).setCellValue(Double.valueOf(String.valueOf(summary[13])));
								row.createCell(14).setCellValue(Double.valueOf(String.valueOf(summary[14])));
								row.createCell(15).setCellValue(Double.valueOf(String.valueOf(summary[15])));
								row.createCell(16).setCellValue(Double.valueOf(String.valueOf(summary[16])));
								row.createCell(17).setCellValue(Double.valueOf(String.valueOf(summary[17])));
								row.createCell(18).setCellValue(Double.valueOf(String.valueOf(summary[18])));
								row.createCell(19).setCellValue(Double.valueOf(String.valueOf(summary[19])));
								row.createCell(20).setCellValue("");
								// For FTD %
								if (summary[3].equals(0.0)) {
									row.createCell(21).setCellValue(0.0 + "%");
								} else {
									row.createCell(21).setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
								}
								// For MTD %
								if (summary[9].equals(0.0)) {
									row.createCell(22).setCellValue(0.0 + "%");
								} else {
									row.createCell(22).setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[11]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[9]))) * 100)
											+ "%"));
								}
								// For Difference b/w (mtdCredit-lmtdCreadit )/lmtdCredit
								if (summary[19].equals(0.0)) {
									row.createCell(23).setCellValue(0.0 + "%");
								} else {
									row.createCell(23).setCellValue(String.valueOf(value
											.format((Float.valueOf(String.valueOf(summary[13]))
													- Float.valueOf(String.valueOf(summary[19])))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[19]))))
											+ "%"));
								}
								// For FTD Answered
								if (summary[3].equals(0.0)) {
									row.createCell(24).setCellValue(0.0 + "%");
								} else {
									row.createCell(24).setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
								}
								// For MTD Answered
								if (summary[9].equals(0.0)) {
									row.createCell(25).setCellValue(0.0 + "%");
								} else {
									row.createCell(25).setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[11]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[9]))) * 100)
											+ "%"));
								}
								ftdtotlalMsisdn = ftdtotlalMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
								ftdtotalValidMsisdn = ftdtotalValidMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
								ftdtotlaAttemptedCalls = ftdtotlaAttemptedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
								ftdtotalConnectedCalls = ftdtotalConnectedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
								ftdtotalBillSec = ftdtotalBillSec
										+ (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
								ftdtotalCreditsUser = ftdtotalCreditsUser
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

								mtdtotlalMsisdn = mtdtotlalMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[8])));
								mtdtotalValidMsisdn = mtdtotalValidMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[9])));
								mtdtotlaAttemptedCalls = mtdtotlaAttemptedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[10])));
								mtdtotalConnectedCalls = mtdtotalConnectedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[11])));
								mtdtotalBillSec = mtdtotalBillSec
										+ (int) Math.round(Float.valueOf(String.valueOf(summary[12])));
								mtdtotalCreditsUser = mtdtotalCreditsUser
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[13])));

								lmtdtotlalMsisdn = lmtdtotlalMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[14])));
								lmtdtotalValidMsisdn = lmtdtotalValidMsisdn
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[15])));
								lmtdtotlaAttemptedCalls = lmtdtotlaAttemptedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[16])));
								lmtdtotalConnectedCalls = lmtdtotalConnectedCalls
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[17])));
								lmtdtotalBillSec = lmtdtotalBillSec
										+ (int) Math.round(Float.valueOf(String.valueOf(summary[18])));
								lmtdtotalCreditsUser = lmtdtotalCreditsUser
										+ (int) Math.round(Double.valueOf(String.valueOf(summary[19])));

								rowIdx = rowIdx++;
							}

							affiliateRow = sheet.createRow(rowIdx);

							Cell affiliateCell = affiliateRow.createCell(1);
							affiliateCell.setCellValue(account+" Total::");
							affiliateCell.setCellStyle(headerCellStyle);
							// For FTD
							affiliateRow.createCell(2).setCellValue(ftdtotlalMsisdn);
							affiliateRow.createCell(3).setCellValue(ftdtotalValidMsisdn);
							affiliateRow.createCell(4).setCellValue(ftdtotlaAttemptedCalls);
							affiliateRow.createCell(5).setCellValue(ftdtotalConnectedCalls);
							affiliateRow.createCell(6).setCellValue(ftdtotalBillSec);
							affiliateRow.createCell(7).setCellValue(ftdtotalCreditsUser);
							// For MTD
							affiliateRow.createCell(8).setCellValue(mtdtotlalMsisdn);
							affiliateRow.createCell(9).setCellValue(mtdtotalValidMsisdn);
							affiliateRow.createCell(10).setCellValue(mtdtotlaAttemptedCalls);
							affiliateRow.createCell(11).setCellValue(mtdtotalConnectedCalls);
							affiliateRow.createCell(12).setCellValue(mtdtotalBillSec);
							affiliateRow.createCell(13).setCellValue(mtdtotalCreditsUser);
							// For LMTD
							affiliateRow.createCell(14).setCellValue(lmtdtotlalMsisdn);
							affiliateRow.createCell(15).setCellValue(lmtdtotalValidMsisdn);
							affiliateRow.createCell(16).setCellValue(lmtdtotlaAttemptedCalls);
							affiliateRow.createCell(17).setCellValue(lmtdtotalConnectedCalls);
							affiliateRow.createCell(18).setCellValue(lmtdtotalBillSec);
							affiliateRow.createCell(19).setCellValue(lmtdtotalCreditsUser);

							affiliateRow.createCell(20).setCellValue("");

							// For Calculation regarding FTD , MTD and LMTD

							if (ftdtotalConnectedCalls.equals(0)) {
								affiliateRow.createCell(21).setCellValue(0.0 + "%");

							} else {
								affiliateRow.createCell(21)
										.setCellValue(Float.valueOf(String.valueOf(value.format(
												Float.valueOf(ftdtotalConnectedCalls) / ftdtotalValidMsisdn * 100)))
												+ "%");
							}
							if (mtdtotalValidMsisdn.equals(0)) {
								affiliateRow.createCell(22).setCellValue(0.0 + "%");

							} else {
								affiliateRow.createCell(22)
										.setCellValue(Float.valueOf(String.valueOf(value.format(
												Float.valueOf(mtdtotalConnectedCalls) / mtdtotalValidMsisdn * 100)))
												+ "%");
							}
							if (lmtdtotalCreditsUser.equals(0)) {
								affiliateRow.createCell(23).setCellValue(0.0 + "%");

							} else {
								affiliateRow.createCell(23)
										.setCellValue(Float.valueOf(String.valueOf(
												value.format((mtdtotalCreditsUser - lmtdtotalCreditsUser)
														/ lmtdtotalCreditsUser * 100)))
												+ "%");
							}
							if (ftdtotalValidMsisdn.equals(0)) {
								affiliateRow.createCell(24).setCellValue(0.0 + "%");

							} else {
								affiliateRow.createCell(24)
										.setCellValue(Float.valueOf(String.valueOf(value.format(
												Float.valueOf(ftdtotalConnectedCalls) / ftdtotalValidMsisdn * 100)))
												+ "%");
							}
							if (mtdtotalValidMsisdn.equals(0)) {
								affiliateRow.createCell(25).setCellValue(0.0 + "%");

							} else {
								affiliateRow.createCell(25)
										.setCellValue(Float.valueOf(String.valueOf(value.format(
												Float.valueOf(mtdtotalConnectedCalls) / mtdtotalValidMsisdn * 100)))
												+ "%");
							}

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
