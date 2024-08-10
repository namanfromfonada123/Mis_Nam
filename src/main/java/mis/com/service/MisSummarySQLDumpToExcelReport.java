package mis.com.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mis.com.entity.MisCallDetailSummaryReport;
import mis.com.repository.MisCallDetailsSummaryRepository;
import mis.com.utils.DateUtils;

@Service
public class MisSummarySQLDumpToExcelReport {
	public static final Logger Logger = LoggerFactory.getLogger(MisSummarySQLDumpToExcelReport.class);

	@Value("${ftp.host.saveDailReport}")
	String saveDailyReport;

	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	@Transactional
	public void misCallDetailsSummaryMTDtoExcel() throws IOException {
		Logger.info("**** Inside MisSummarySQLDumpToExcelReport.misCallDetailsSummaryMTDtoExcel() *****");
		List<MisCallDetailSummaryReport> lastMonthSummaryReportList = null;
		List<MisCallDetailSummaryReport> currentMonthSummaryReportList = null;

		int rowIdx = 0;
		Sheet sheet = null;
		Workbook workbook = null;
		OutputStream out = null;
		LocalDate todaydate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String[] columns = { "Execution Date", "User Name", "Campaign Name", "Lead Name", "Total MSISDN",
				"Valid MSISDN", "No. Of Retry", "Attempted Calls", "Connected Calls", "Digit Pressed", "Listen Rate",
				"Total Bill Sec.", "Credits Used", "Panel", "user name", "Account Name" };
		try {
			currentMonthSummaryReportList = misCallDetailsSummaryRepository.getMonthlyByGivenStartAndEndDate(
					dateTimeFormatter.format(todaydate.withDayOfMonth(1)), DateUtils.getYesterdayDateString());

			if (currentMonthSummaryReportList.size() > 0) {
				Logger.info("****MisCallDetailSummaryReport List Size For Current Month:: "
						+ currentMonthSummaryReportList.size());

				/*
				 * String folderPath = "C:\\MIS Report"; File theDir = new File(folderPath);
				 * theDir.mkdir(); theDir.createNewFile();
				 */
				String excelsheet = saveDailyReport + "/SummaryMISReport" + DateUtils.getYesterdayDateStringInyyyyMMddFormat() + ".xls";
				Logger.info("***** Creating Excel :: " + excelsheet);
				InputStream inputStream = new FileInputStream(excelsheet);
				workbook = WorkbookFactory.create(inputStream);
				out = new FileOutputStream(excelsheet);

				sheet = workbook.createSheet("MisSummarySQLDump");

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
				headerCellStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
				// headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
				headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

				// headerCellStyle.setVerticalAlignment();
				// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
				// sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));
				sheet.setHorizontallyCenter(true);

				int x = 0;
				Row headerRow = sheet.createRow(x);
				// headerRow.setHeight((short) 800);

				// Header

				for (int col = 0; col < columns.length; col++) {
					sheet.setColumnWidth(col, 4000);
					Cell cell = headerRow.createCell(col);
					cell.setCellValue(columns[col]);
					cell.setCellStyle(headerCellStyle);
				}

				rowIdx = 1;
				for (MisCallDetailSummaryReport mis : currentMonthSummaryReportList) {
					Row row = sheet.createRow(rowIdx++);
					row.createCell(0).setCellValue(mis.getExecutionDt());
					row.createCell(1).setCellValue(mis.getUserName());
					row.createCell(2).setCellValue(mis.getCampaignName());
					row.createCell(3).setCellValue(mis.getLeadName());
					row.createCell(4).setCellValue(mis.getTotalMSISDN());
					row.createCell(5).setCellValue(mis.getValidMSISDN());
					row.createCell(6).setCellValue(mis.getNoOfRetry());
					row.createCell(7).setCellValue(mis.getAttemptedCalls());
					row.createCell(8).setCellValue(mis.getConnectedCalls());
					row.createCell(9).setCellValue(mis.getDigitPressed());
					row.createCell(10).setCellValue(mis.getListenRate());
					row.createCell(11).setCellValue(mis.getTotalBillSec());
					row.createCell(12).setCellValue(mis.getCreditUsed());
					row.createCell(13).setCellValue(mis.getIpAddress());
					row.createCell(14).setCellValue(mis.getUserType());
					row.createCell(15).setCellValue(mis.getAccountName());

					rowIdx = rowIdx++;
				}
			} else {
				Logger.info("Data Not Found As Given Current Month Date For MisSQLDump::"
						+ dateTimeFormatter.format(todaydate.withDayOfMonth(1)) + " And "
						+ DateUtils.getYesterdayDateString());
			}
			lastMonthSummaryReportList = misCallDetailsSummaryRepository.getMonthlyByGivenStartAndEndDate(
					DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))),
					DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));

			if (lastMonthSummaryReportList.size() > 0) {
				Logger.info("****MisCallDetailSummaryReport List Size For Last Month:: "
						+ lastMonthSummaryReportList.size());
				for (MisCallDetailSummaryReport mis : lastMonthSummaryReportList) {
					Row row = sheet.createRow(rowIdx++);
					row.createCell(0).setCellValue(mis.getExecutionDt());
					row.createCell(1).setCellValue(mis.getUserName());
					row.createCell(2).setCellValue(mis.getCampaignName());
					row.createCell(3).setCellValue(mis.getLeadName());
					row.createCell(4).setCellValue(mis.getTotalMSISDN());
					row.createCell(5).setCellValue(mis.getValidMSISDN());
					row.createCell(6).setCellValue(mis.getNoOfRetry());
					row.createCell(7).setCellValue(mis.getAttemptedCalls());
					row.createCell(8).setCellValue(mis.getConnectedCalls());
					row.createCell(9).setCellValue(mis.getDigitPressed());
					row.createCell(10).setCellValue(mis.getListenRate());
					row.createCell(11).setCellValue(mis.getTotalBillSec());
					row.createCell(12).setCellValue(mis.getCreditUsed());
					row.createCell(13).setCellValue(mis.getIpAddress());
					row.createCell(14).setCellValue(mis.getUserType());
					row.createCell(15).setCellValue(mis.getAccountName());

					rowIdx = rowIdx++;
				}
			} else {
				Logger.info("Data Not Found As Given Previous Month Date For MisSQLDump::"
						+ DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1)))
						+ " And " + DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));
			}
			workbook.write(out);

		} catch (Exception e) {
			Logger.info("**** Inside MisSummarySQLDumpToExcelReport.misCallDetailsSummaryMTDtoExcel() Got Exception::"
					+ e.getMessage());

			e.printStackTrace();
		}
	}
}
