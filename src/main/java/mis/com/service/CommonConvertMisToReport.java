package mis.com.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class CommonConvertMisToReport {
	public static final Logger Logger = LoggerFactory.getLogger(CommonConvertMisToReport.class);

	@Value("${ftp.host.saveDailReport}")
	String saveDailyReport;
	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private MisSummarySQLDumpToExcelReport misCallDetailsSummaryMTDReport;

	@SuppressWarnings("resource")
	// @Scheduled(fixedRate = 25000)
	public void misToExcel(String[] columns, List<Object[]> summaryReportList, String sheetName) throws IOException {
		Logger.info("***** For Creating-- " + sheetName + " --Report.");
		Workbook workbook = null;
		List<Object[]> affiliate = null;
		List<Object[]> paisabazar = null;
		List<Object[]> shivtelSelf = null;
		List<Object[]> times = null;
		List<Object[]> shivTellSales = null;
		List<Object[]> zeroAccountList = null;
		List<Object[]> celetelList = null;
		Row affiliateRow = null;
		Row timesRow = null;
		Row totalTimeRow = null;
		Row paisaBazarRow = null;
		Row totalshivtelSalesRow = null;
		Row totalShivtelSelf = null;
		Row totalZeroAccount = null;
		Row zeroAccountRow = null;
		Row celtelRow = null;
		OutputStream out = null;
		Sheet sheet = null;
		InputStream inputStream = null;

		try {
			// (Utils.getDateInStringYYYYMMddd());

			DecimalFormat value = new DecimalFormat("#.#");
			workbook = new XSSFWorkbook();
			/*
			 * String folderPath = "C:\\MIS Report"; File theDir = new File(folderPath);
			 * theDir.mkdir(); theDir.createNewFile();
			 */
			String excelsheet = saveDailyReport + "/SummaryMISReport" + DateUtils.getDateInString() + ".xls";

			Logger.info("***** Excel Creating Path::" + excelsheet);

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
				dateCell.setCellValue(DateUtils.getDateInStringYYYYMMddd());//
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
				affiliate = summaryReportList.parallelStream().filter(predicate -> predicate[0].equals("Affiliate"))
						.collect(Collectors.toList());
				paisabazar = summaryReportList.parallelStream().filter(predicate -> predicate[0].equals("Paisabazar"))
						.collect(Collectors.toList());
				shivTellSales = summaryReportList.parallelStream()
						.filter(predicate -> predicate[0].equals("Shivtel Sales")).collect(Collectors.toList());

				shivtelSelf = summaryReportList.parallelStream()
						.filter(predicate -> predicate[0].equals("Shivtel Self")).collect(Collectors.toList());
				times = summaryReportList.parallelStream().filter(predicate -> predicate[0].equals("Times"))
						.collect(Collectors.toList());

				zeroAccountList = summaryReportList.parallelStream()
						.filter(predicate -> predicate[0].equals("Zero Account")).collect(Collectors.toList());

				celetelList = summaryReportList.parallelStream().filter(predicate -> predicate[0].equals("Celetel"))
						.collect(Collectors.toList());

				int rowIdx = 2;

				if (affiliate.size() > 0) {
					Logger.info("***** Inserting Affiliate Record Inside Excel****");

					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					for (Object[] summary : affiliate) {
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
							row.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

						rowIdx = rowIdx++;
					}
					affiliateRow = sheet.createRow(rowIdx);
					Cell affiliateCell = affiliateRow.createCell(1);
					affiliateCell.setCellValue("Affiliate");
					affiliateCell.setCellStyle(headerCellStyle);
					affiliateRow.createCell(2).setCellValue(totlalMsisdn);
					affiliateRow.createCell(3).setCellValue(totalValidMsisdn);
					affiliateRow.createCell(4).setCellValue(totlaAttemptedCalls);
					affiliateRow.createCell(5).setCellValue(totalConnectedCalls);
					affiliateRow.createCell(6).setCellValue(totalBillSec);
					affiliateRow.createCell(7).setCellValue(totalCreditsUser);
					affiliateRow.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
					rowIdx = 1 + affiliateRow.getRowNum();
				}
				/*****************************************
				 * Celtel
				 *********************************************/
				/*
				 * Row celetelRow = null; if (celetelList.size() > 0) {
				 * Logger.info("***** Inserting Celtel Record Inside Excel****");
				 * 
				 * Integer totlalMsisdn = 0; Integer totalValidMsisdn = 0; Integer
				 * totlaAttemptedCalls = 0; Integer totalConnectedCalls = 0; Integer
				 * totalBillSec = 0; Integer totalCreditsUser = 0; int afRow = 0; if
				 * (affiliateRow != null) { afRow = 1 + affiliateRow.getRowNum(); } else { afRow
				 * = rowIdx; } for (Object[] summary : celetelList) { celetelRow =
				 * sheet.createRow(afRow++);
				 * celetelRow.createCell(0).setCellValue(summary[0].toString());
				 * celetelRow.createCell(1).setCellValue(summary[1].toString());
				 * celetelRow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2
				 * ])));
				 * celetelRow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3
				 * ])));
				 * celetelRow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4
				 * ])));
				 * celetelRow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5
				 * ])));
				 * celetelRow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6
				 * ])));
				 * celetelRow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7
				 * ]))); if (summary[3].equals(0.0)) { celetelRow.createCell(8).setCellValue(0 +
				 * "%"); } else { celetelRow.createCell(8) .setCellValue(String.valueOf(value
				 * .format(Float.valueOf(String.valueOf(summary[5])) / (int)
				 * Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100) + "%")); }
				 * 
				 * totlalMsisdn = totlalMsisdn + (int)
				 * Math.round(Double.valueOf(String.valueOf(summary[2]))); totalValidMsisdn =
				 * totalValidMsisdn + (int)
				 * Math.round(Double.valueOf(String.valueOf(summary[3]))); totlaAttemptedCalls =
				 * totlaAttemptedCalls + (int)
				 * Math.round(Double.valueOf(String.valueOf(summary[4]))); totalConnectedCalls =
				 * totalConnectedCalls + (int)
				 * Math.round(Double.valueOf(String.valueOf(summary[5]))); totalBillSec =
				 * totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
				 * totalCreditsUser = totalCreditsUser + (int)
				 * Math.round(Double.valueOf(String.valueOf(summary[7])));
				 * 
				 * rowIdx = rowIdx + 1; } if (afRow >= rowIdx) { celtelRow =
				 * sheet.createRow(afRow++); } else { celtelRow = sheet.createRow(rowIdx++);
				 * 
				 * } // paisaBazarRow.createCell(1).setCellValue("Celtel"); Cell celtelCell =
				 * celtelRow.createCell(1); celtelCell.setCellValue("Celtel");
				 * celtelCell.setCellStyle(headerCellStyle);
				 * celtelRow.createCell(2).setCellValue(totlalMsisdn);
				 * celtelRow.createCell(3).setCellValue(totalValidMsisdn);
				 * celtelRow.createCell(4).setCellValue(totlaAttemptedCalls);
				 * celtelRow.createCell(5).setCellValue(totalConnectedCalls);
				 * celtelRow.createCell(6).setCellValue(totalBillSec);
				 * celtelRow.createCell(7).setCellValue(totalCreditsUser);
				 * celtelRow.createCell(8) .setCellValue(Float .valueOf(String.valueOf(
				 * value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100))) +
				 * "%");
				 * 
				 * rowIdx = afRow;
				 * 
				 * }
				 */
				/*******************************************
				 * Paisa Bazar
				 *******************************************/
				Row paisabazarrow = null;
				if (paisabazar.size() > 0) {
					Logger.info("***** Inserting PAISABAZAR Record Inside Excel****");

					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					int afRow = 0;
					if (affiliate != null) {
						afRow = 1 + affiliateRow.getRowNum();
					} else {
						afRow = rowIdx;
					}
					for (Object[] summary : paisabazar) {
						paisabazarrow = sheet.createRow(afRow++);
						paisabazarrow.createCell(0).setCellValue(summary[0].toString());
						paisabazarrow.createCell(1).setCellValue(summary[1].toString());
						paisabazarrow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
						paisabazarrow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
						paisabazarrow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
						paisabazarrow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
						paisabazarrow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
						paisabazarrow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
						if (summary[3].equals(0.0)) {
							paisabazarrow.createCell(8).setCellValue(0 + "%");
						} else {
							paisabazarrow.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

						rowIdx = rowIdx + 1;
					}
					if (afRow >= rowIdx) {
						paisaBazarRow = sheet.createRow(afRow++);
					} else {
						paisaBazarRow = sheet.createRow(rowIdx++);

					}
					// paisaBazarRow.createCell(1).setCellValue("Paisabazar");
					Cell paisaBazarCell = paisaBazarRow.createCell(1);
					paisaBazarCell.setCellValue("Paisabazar");
					paisaBazarCell.setCellStyle(headerCellStyle);
					paisaBazarRow.createCell(2).setCellValue(totlalMsisdn);
					paisaBazarRow.createCell(3).setCellValue(totalValidMsisdn);
					paisaBazarRow.createCell(4).setCellValue(totlaAttemptedCalls);
					paisaBazarRow.createCell(5).setCellValue(totalConnectedCalls);
					paisaBazarRow.createCell(6).setCellValue(totalBillSec);
					paisaBazarRow.createCell(7).setCellValue(totalCreditsUser);
					paisaBazarRow.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");

					rowIdx = afRow;

				}

				// Shivtel Sales
				Row shivtelSalesRow = null;
				if (shivTellSales.size() > 0) {
					Logger.info("***** Inserting SHIVTELLSALES Record Inside Excel****");

					int afRow = 0;
					if (paisaBazarRow != null) {
						afRow = 1 + paisaBazarRow.getRowNum();
					} else {
						if (paisabazar.isEmpty() && !affiliate.isEmpty()) {
							rowIdx = rowIdx + 1;
						}
						afRow = rowIdx;
					}
					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					for (Object[] summary : shivTellSales) {
						shivtelSalesRow = sheet.createRow(afRow++);
						shivtelSalesRow.createCell(0).setCellValue(summary[0].toString());
						shivtelSalesRow.createCell(1).setCellValue(summary[1].toString());
						shivtelSalesRow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
						shivtelSalesRow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
						shivtelSalesRow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
						shivtelSalesRow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
						shivtelSalesRow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
						shivtelSalesRow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
						if (summary[3].equals(0.0)) {
							shivtelSalesRow.createCell(8).setCellValue(0 + "%");
						} else {
							shivtelSalesRow.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

						rowIdx = rowIdx + 1;
					}
					if (afRow >= rowIdx) {
						totalshivtelSalesRow = sheet.createRow(afRow++);
					} else {
						totalshivtelSalesRow = sheet.createRow(rowIdx++);
					}
					// totalshivtelSalesRow.createCell(1).setCellValue("Shivtel Sales");
					Cell shivtelSalesCell = totalshivtelSalesRow.createCell(1);
					shivtelSalesCell.setCellValue("Shivtel Sales");
					shivtelSalesCell.setCellStyle(headerCellStyle);
					totalshivtelSalesRow.createCell(2).setCellValue(totlalMsisdn);
					totalshivtelSalesRow.createCell(3).setCellValue(totalValidMsisdn);
					totalshivtelSalesRow.createCell(4).setCellValue(totlaAttemptedCalls);
					totalshivtelSalesRow.createCell(5).setCellValue(totalConnectedCalls);
					totalshivtelSalesRow.createCell(6).setCellValue(totalBillSec);
					totalshivtelSalesRow.createCell(7).setCellValue(totalCreditsUser);
					totalshivtelSalesRow.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
					rowIdx = afRow;
				}

				/************************************
				 * Shivtel Self
				 *************************************************/

				Row shivtelSelfRow = null;
				if (shivtelSelf.size() > 0) {
					Logger.info("***** Inserting SHIVTELL SELF Record Inside Excel****");

					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					int afRow = 0;
					if (totalshivtelSalesRow != null) {
						afRow = 1 + totalshivtelSalesRow.getRowNum();
					} else {
						if (shivTellSales.isEmpty() && !paisabazar.isEmpty()) {
							rowIdx = rowIdx + 1;
						}
						afRow = rowIdx;
					}
					for (Object[] summary : shivtelSelf) {
						shivtelSelfRow = sheet.createRow(afRow++);
						shivtelSelfRow.createCell(0).setCellValue(summary[0].toString());
						shivtelSelfRow.createCell(1).setCellValue(summary[1].toString());
						shivtelSelfRow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
						shivtelSelfRow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
						shivtelSelfRow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
						shivtelSelfRow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
						shivtelSelfRow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
						shivtelSelfRow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
						if (summary[3].equals(0.0)) {
							shivtelSelfRow.createCell(8).setCellValue(0 + "%");
						} else {
							shivtelSelfRow.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));
						rowIdx = rowIdx + 1;

					}
					if (afRow >= rowIdx) {
						totalShivtelSelf = sheet.createRow(afRow++); //
					} else {
						totalShivtelSelf = sheet.createRow(rowIdx++); //
					}
					totalShivtelSelf.createCell(1).setCellValue("Shivtel Self");
					Cell shivtelSelfCell = totalShivtelSelf.createCell(1);
					shivtelSelfCell.setCellValue("Shivtel Self");
					shivtelSelfCell.setCellStyle(headerCellStyle);
					totalShivtelSelf.createCell(2).setCellValue(totlalMsisdn);
					totalShivtelSelf.createCell(3).setCellValue(totalValidMsisdn);
					totalShivtelSelf.createCell(4).setCellValue(totlaAttemptedCalls);
					totalShivtelSelf.createCell(5).setCellValue(totalConnectedCalls);
					totalShivtelSelf.createCell(6).setCellValue(totalBillSec);
					totalShivtelSelf.createCell(7).setCellValue(totalCreditsUser);
					totalShivtelSelf.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
					rowIdx = afRow;
				}

				/*****************************************
				 * Times
				 ********************************************/

				if (times.size() > 0) {
					Logger.info("***** Inserting TIMES Record Inside Excel****");

					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					int afRow = 0;
					if (totalShivtelSelf != null) {
						afRow = 1 + totalShivtelSelf.getRowNum();
					} else {
						if (shivTellSales.isEmpty()) {
							rowIdx = rowIdx + 1;
						}
						afRow = rowIdx;
					}

					for (Object[] summary : times) {
						timesRow = sheet.createRow(afRow++);
						timesRow.createCell(0).setCellValue(summary[0].toString());
						timesRow.createCell(1).setCellValue(summary[1].toString());
						timesRow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
						timesRow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
						timesRow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
						timesRow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
						timesRow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
						timesRow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
						if (summary[3].equals(0.0)) {
							timesRow.createCell(8).setCellValue(0 + "%");
						} else {
							timesRow.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

						rowIdx = rowIdx++;

					}
					if (afRow >= rowIdx) {
						totalTimeRow = sheet.createRow(afRow++); //
					} else {
						totalTimeRow = sheet.createRow(rowIdx); //
					}
					totalTimeRow.createCell(1).setCellValue("Times");
					Cell timesCell = totalTimeRow.createCell(1);
					timesCell.setCellValue("Times");
					timesCell.setCellStyle(headerCellStyle);
					totalTimeRow.createCell(2).setCellValue(totlalMsisdn);
					totalTimeRow.createCell(3).setCellValue(totalValidMsisdn);
					totalTimeRow.createCell(4).setCellValue(totlaAttemptedCalls);
					totalTimeRow.createCell(5).setCellValue(totalConnectedCalls);
					totalTimeRow.createCell(6).setCellValue(totalBillSec);
					totalTimeRow.createCell(7).setCellValue(totalCreditsUser);
					totalTimeRow.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
					rowIdx = afRow;
				}

				/*****************************************
				 * Zero Account and User Mapping
				 ********************************************/

				if (zeroAccountList.size() > 0) {
					Logger.info("***** Inserting ZERO ACCOUNT(NO MAPPING) Record Inside Excel****");
					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;
					int afRow = 0;
					if (totalTimeRow != null) {
						afRow = 1 + totalTimeRow.getRowNum();
					} else {
						afRow = rowIdx;
					}

					for (Object[] summary : zeroAccountList) {
						zeroAccountRow = sheet.createRow(afRow++);
						zeroAccountRow.createCell(0).setCellValue(summary[0].toString());
						zeroAccountRow.createCell(1).setCellValue(summary[1].toString());
						zeroAccountRow.createCell(2).setCellValue(Double.valueOf(String.valueOf(summary[2])));
						zeroAccountRow.createCell(3).setCellValue(Double.valueOf(String.valueOf(summary[3])));
						zeroAccountRow.createCell(4).setCellValue(Double.valueOf(String.valueOf(summary[4])));
						zeroAccountRow.createCell(5).setCellValue(Double.valueOf(String.valueOf(summary[5])));
						zeroAccountRow.createCell(6).setCellValue(Double.valueOf(String.valueOf(summary[6])));
						zeroAccountRow.createCell(7).setCellValue(Double.valueOf(String.valueOf(summary[7])));
						if (summary[3].equals(0.0)) {
							zeroAccountRow.createCell(8).setCellValue(0 + "%");
						} else {
							zeroAccountRow.createCell(8)
									.setCellValue(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"));
						}

						totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(String.valueOf(summary[2])));
						totalValidMsisdn = totalValidMsisdn
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[3])));
						totlaAttemptedCalls = totlaAttemptedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[4])));
						totalConnectedCalls = totalConnectedCalls
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[5])));
						totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(String.valueOf(summary[6])));
						totalCreditsUser = totalCreditsUser
								+ (int) Math.round(Double.valueOf(String.valueOf(summary[7])));

						rowIdx = rowIdx++;

					}
					if (afRow >= rowIdx) {
						totalZeroAccount = sheet.createRow(afRow++); //
					} else {
						totalZeroAccount = sheet.createRow(rowIdx); //
					}
					totalZeroAccount.createCell(1).setCellValue("Zero Account Mapping");
					Cell zeroAccountCell = totalZeroAccount.createCell(1);
					zeroAccountCell.setCellValue("Zero Account");
					zeroAccountCell.setCellStyle(headerCellStyle);
					totalZeroAccount.createCell(2).setCellValue(totlalMsisdn);
					totalZeroAccount.createCell(3).setCellValue(totalValidMsisdn);
					totalZeroAccount.createCell(4).setCellValue(totlaAttemptedCalls);
					totalZeroAccount.createCell(5).setCellValue(totalConnectedCalls);
					totalZeroAccount.createCell(6).setCellValue(totalBillSec);
					totalZeroAccount.createCell(7).setCellValue(totalCreditsUser);
					totalZeroAccount.createCell(8)
							.setCellValue(Float
									.valueOf(String.valueOf(
											value.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
									+ "%");
					rowIdx = afRow;
				}

				Logger.info("***** Successfully Executed FtdMtdLmtdMisSummaryExcelGenerator.misToExcel() ****");

			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
