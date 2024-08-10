package mis.com.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mis.com.repository.MisCallDetailsSummaryRepository;
import mis.com.utils.DateUtils;

@Service
public class ObdFormatConvertHTMLFormatToSendEmailBodyService2 {
	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	public String makeHtmlFormatForOBDReport() throws ParseException {
		String[] reportArray = { "FTD", "MTD", "LMTD" };
		StringBuilder htmlTableBuilder = new StringBuilder();
		LocalDate todaydate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<Object[]> summaryReportList = null;
		summaryReportList = misCallDetailsSummaryRepository.getObdSummaryReportOfFTDandMTDandLMTD(
				DateUtils.getYesterdayDateString(), dateTimeFormatter.format(todaydate.withDayOfMonth(1)),
				DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))),
				DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));

		// open the table
		htmlTableBuilder.append("<head>").append("<h1>" + "OBD Summary" + " Report::<h1><br>").append("</head>")
				.append("<body>");
		htmlTableBuilder.append("<table border=\"1\">").append(System.lineSeparator());
		// open a rown, header row here
		htmlTableBuilder.append("\t").append("<tr style=\"color:black;background-color:yellow\">")
				.append(System.lineSeparator());
		// add the headers to the header row
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("Account Name").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("User Name").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Total MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Valid MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Attempted Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Connected Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Total Bill Sec.").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Credits Used").append("</th>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD  Total MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Valid MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Attempted Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Connected Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Total Bill Sec.").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Credits Used").append("</th>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Total MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Valid MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Attempted Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Connected Calls").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Total Bill Sec.").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LMTD Credits Used").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append(" ").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD % SC").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD % SC").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>")
				.append("Diff in Credit (mtdCreditUsed-lmtdCreditUsed) / lmtdCreditUsed").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("FTD Answered").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("MTD Answered").append("</th>")
				.append(System.lineSeparator());

		// close the header row
		htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
		DecimalFormat value = new DecimalFormat("#.#");
		// add a data row for each PetrolData

		Integer grandTotlalMsisdn = 0;
		Integer grandTotalValidMsisdn = 0;
		Integer grandTotlaAttemptedCalls = 0;
		Integer grandTotalConnectedCalls = 0;
		Integer grandTotalBillSec = 0;
		Integer grandTotalCreditsUser = 0;

		Integer grandMtdTotlalMsisdn = 0;
		Integer grandMtdTotalValidMsisdn = 0;
		Integer grandMtdTotlaAttemptedCalls = 0;
		Integer grandMtdTotalConnectedCalls = 0;
		Integer grandMtdTotalBillSec = 0;
		Integer grandMtdTotalCreditsUser = 0;

		Integer grandLmtdTotalMsisdn = 0;
		Integer grandLmtdTotalValidMsisdn = 0;
		Integer grandLmtdTotalAttemptedCalls = 0;
		Integer grandLmtdTotalConnectedCalls = 0;
		Integer grandLmtdTotalBillSec = 0;
		Integer grandLmtdTotalCreditsUser = 0;

		List<String> accountNameList = misCallDetailsSummaryRepository.getAccountList();
		if (accountNameList.size() > 0) {

			for (String account : accountNameList) {
				List<Object[]> accountList = summaryReportList.parallelStream()
						.filter(predicate -> predicate[0].equals(account)).collect(Collectors.toList());
				if (accountList.size() > 0) {
					Integer totlalMsisdn = 0;
					Integer totalValidMsisdn = 0;
					Integer totlaAttemptedCalls = 0;
					Integer totalConnectedCalls = 0;
					Integer totalBillSec = 0;
					Integer totalCreditsUser = 0;

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
						// open a row again

						htmlTableBuilder.append("\t").append("<tr style=\"color:black\">").append(System.lineSeparator());
						// add the data
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(summary[0].toString())
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(summary[1].toString())
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[2])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[3])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[4])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[5])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[6])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[7])))).append("</td>")
								.append(System.lineSeparator());

						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[8])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[9])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[10])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[11])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[12])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[13])))).append("</td>")
								.append(System.lineSeparator());

						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[14])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[15])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[16])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[17])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[18])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append((int) Math.round(Float.valueOf(String.valueOf(summary[19])))).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append("");
						/* For FTD */
						if (summary[3].equals(0.0)) {
							htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
									.append(System.lineSeparator());

						} else {
							htmlTableBuilder.append("\t").append("\t").append("<td>")
									.append(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"))
									.append("</td>").append(System.lineSeparator());
						}

						/* For MTD */
						if (summary[9].equals(0.0)) {
							htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
									.append(System.lineSeparator());

						} else {
							htmlTableBuilder.append("\t").append("\t").append("<td>")
									.append(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[11]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[9]))) * 100)
											+ "%"))
									.append("</td>").append(System.lineSeparator());
						}

						/* For Differnece b/w (mtdCredit-lmtdCredit)/lmtdCredit */
						if (summary[19].equals(0.0)) {
							htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
									.append(System.lineSeparator());

						} else {
							htmlTableBuilder.append("\t").append("\t").append("<td>")
									.append(String.valueOf(value
											.format((Float.valueOf(String.valueOf(summary[13]))
													- Float.valueOf(String.valueOf(summary[19])))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[19]))))
											+ "%"))
									.append("</td>").append(System.lineSeparator());
						}

						/* For FTD Answered */
						if (summary[3].equals(0.0)) {
							htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
									.append(System.lineSeparator());

						} else {
							htmlTableBuilder.append("\t").append("\t").append("<td>")
									.append(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[5]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
											+ "%"))
									.append("</td>").append(System.lineSeparator());
						}

						/* For MTD Answered */
						if (summary[9].equals(0.0)) {
							htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
									.append(System.lineSeparator());

						} else {
							htmlTableBuilder.append("\t").append("\t").append("<td>")
									.append(String.valueOf(value
											.format(Float.valueOf(String.valueOf(summary[11]))
													/ (int) Math.round(Float.valueOf(String.valueOf(summary[9]))) * 100)
											+ "%"))
									.append("</td>").append(System.lineSeparator());
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

						
						htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
					}
					htmlTableBuilder.append("\t").append("<tr style=\"color:black;background-color:yellow\">")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append("       ").append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(account + " Total::")
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlalMsisdn).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalValidMsisdn).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlaAttemptedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalConnectedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalBillSec).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalCreditsUser).append("</td>")
							.append(System.lineSeparator());

					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotlalMsisdn).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotalValidMsisdn)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotlaAttemptedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotalConnectedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotalBillSec).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(mtdtotalCreditsUser)
							.append("</td>").append(System.lineSeparator());

					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotlalMsisdn).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotalValidMsisdn)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotlaAttemptedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotalConnectedCalls)
							.append("</td>").append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotalBillSec).append("</td>")
							.append(System.lineSeparator());
					htmlTableBuilder.append("\t").append("\t").append("<td>").append(lmtdtotalCreditsUser)
							.append("</td>").append(System.lineSeparator());

					htmlTableBuilder.append("\t").append("\t").append("<td>").append("       ").append("</td>")
							.append(System.lineSeparator());

					if (totalValidMsisdn.equals(0)) {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
								.append(System.lineSeparator());

					} else {
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append(String.valueOf(value.format(Float.valueOf(String.valueOf(totalConnectedCalls))
										/ (int) Math.round(Float.valueOf(String.valueOf(totalValidMsisdn))) * 100)
										+ "%"))
								.append("</td>").append(System.lineSeparator());
					}

					/* For MTD */
					if (mtdtotalValidMsisdn.equals(0)) {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
								.append(System.lineSeparator());

					} else {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(String.valueOf(value
								.format(Float.valueOf(String.valueOf(mtdtotalConnectedCalls))
										/ (int) Math.round(Float.valueOf(String.valueOf(mtdtotalValidMsisdn))) * 100)
								+ "%")).append("</td>").append(System.lineSeparator());
					}

					/* For Differnece b/w (mtdCredit-lmtdCredit)/lmtdCredit */
					if (lmtdtotalCreditsUser.equals(0)) {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
								.append(System.lineSeparator());

					} else {
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append(String.valueOf(value
										.format((Float.valueOf(String.valueOf(mtdtotalCreditsUser))
												- Float.valueOf(String.valueOf(lmtdtotalCreditsUser)))
												/ (int) Math.round(Float.valueOf(String.valueOf(lmtdtotalCreditsUser))))
										+ "%"))
								.append("</td>").append(System.lineSeparator());
					}

					/* For FTD Answered */
					if (totalValidMsisdn.equals(0)) {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
								.append(System.lineSeparator());

					} else {
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append(String.valueOf(value.format(Float.valueOf(String.valueOf(totalConnectedCalls))
										/ (int) Math.round(Float.valueOf(String.valueOf(totalValidMsisdn))) * 100)
										+ "%"))
								.append("</td>").append(System.lineSeparator());
					}

					/* For MTD Answered */
					if (mtdtotalValidMsisdn.equals(0)) {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
								.append(System.lineSeparator());

					} else {
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(String.valueOf(value
								.format(Float.valueOf(String.valueOf(mtdtotalConnectedCalls))
										/ (int) Math.round(Float.valueOf(String.valueOf(mtdtotalValidMsisdn))) * 100)
								+ "%")).append("</td>").append(System.lineSeparator());
					}
					grandTotlalMsisdn = grandTotlalMsisdn + totlalMsisdn;
					grandTotalValidMsisdn = grandTotalValidMsisdn + totalValidMsisdn;
					grandTotlaAttemptedCalls = grandTotlaAttemptedCalls + totlaAttemptedCalls;
					grandTotalConnectedCalls = grandTotalConnectedCalls + totalConnectedCalls;
					grandTotalBillSec = grandTotalBillSec + totalBillSec;
					grandTotalCreditsUser = grandTotalCreditsUser + totalCreditsUser;

					grandMtdTotlalMsisdn = grandMtdTotlalMsisdn + mtdtotlalMsisdn;
					grandMtdTotalValidMsisdn = grandMtdTotalValidMsisdn + mtdtotalValidMsisdn;
					grandMtdTotlaAttemptedCalls = grandMtdTotlaAttemptedCalls + mtdtotlaAttemptedCalls;
					grandMtdTotalConnectedCalls = grandMtdTotalConnectedCalls + mtdtotalConnectedCalls;
					grandMtdTotalBillSec = grandMtdTotalBillSec + mtdtotalBillSec;
					grandMtdTotalCreditsUser = grandMtdTotalCreditsUser + mtdtotalCreditsUser;

					grandLmtdTotalMsisdn = grandLmtdTotalMsisdn + lmtdtotlalMsisdn;
					grandLmtdTotalValidMsisdn = grandLmtdTotalValidMsisdn + lmtdtotalValidMsisdn;
					grandLmtdTotalAttemptedCalls = grandLmtdTotalAttemptedCalls + lmtdtotlaAttemptedCalls;
					grandLmtdTotalConnectedCalls = grandLmtdTotalConnectedCalls + lmtdtotalConnectedCalls;
					grandLmtdTotalBillSec = grandLmtdTotalBillSec + lmtdtotalBillSec;
					grandLmtdTotalCreditsUser = grandLmtdTotalCreditsUser + lmtdtotalCreditsUser;
					// close the row
					htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
				}
			}

		}

		htmlTableBuilder.append("\t").append("<tr style=\"color:black;background-color:yellow\">")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append("       ").append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append("Grand Total::").append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotlalMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotalValidMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotlaAttemptedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotalConnectedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotalBillSec).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandTotalCreditsUser).append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotlalMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotalValidMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotlaAttemptedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotalConnectedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotalBillSec).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandMtdTotalCreditsUser).append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalValidMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalAttemptedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalConnectedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalBillSec).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(grandLmtdTotalCreditsUser).append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append("       ").append("</td>")
				.append(System.lineSeparator());

		if (grandTotalValidMsisdn.equals(0)) {
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
					.append(System.lineSeparator());

		} else {
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(String.valueOf(value
							.format(Float.valueOf(String.valueOf(grandTotalConnectedCalls))
									/ (int) Math.round(Float.valueOf(String.valueOf(grandTotalValidMsisdn))) * 100)
							+ "%"))
					.append("</td>").append(System.lineSeparator());
		}

		/* For MTD */
		if (grandMtdTotalValidMsisdn.equals(0)) {
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
					.append(System.lineSeparator());

		} else {
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(String.valueOf(value
							.format(Float.valueOf(String.valueOf(grandMtdTotalConnectedCalls))
									/ (int) Math.round(Float.valueOf(String.valueOf(grandMtdTotalValidMsisdn))) * 100)
							+ "%"))
					.append("</td>").append(System.lineSeparator());
		}

		/* For Differnece b/w (mtdCredit-lmtdCredit)/lmtdCredit */
		if (grandLmtdTotalCreditsUser.equals(0)) {
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
					.append(System.lineSeparator());

		} else {
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(String.valueOf(value
							.format((Float.valueOf(String.valueOf(grandMtdTotalCreditsUser))
									- Float.valueOf(String.valueOf(grandLmtdTotalCreditsUser)))
									/ (int) Math.round(Float.valueOf(String.valueOf(grandLmtdTotalCreditsUser))))
							+ "%"))
					.append("</td>").append(System.lineSeparator());
		}

		/* For FTD Answered */
		if (grandTotalValidMsisdn.equals(0)) {
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
					.append(System.lineSeparator());

		} else {
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(String.valueOf(value
							.format(Float.valueOf(String.valueOf(grandTotalConnectedCalls))
									/ (int) Math.round(Float.valueOf(String.valueOf(grandTotalValidMsisdn))) * 100)
							+ "%"))
					.append("</td>").append(System.lineSeparator());
		}

		/* For MTD Answered */
		if (grandMtdTotalValidMsisdn.equals(0)) {
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%").append("</td>")
					.append(System.lineSeparator());

		} else {
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(String.valueOf(value
							.format(Float.valueOf(String.valueOf(grandMtdTotalConnectedCalls))
									/ (int) Math.round(Float.valueOf(String.valueOf(grandMtdTotalValidMsisdn))) * 100)
							+ "%"))
					.append("</td>").append(System.lineSeparator());
		}
		// and close the table
		htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());

		htmlTableBuilder.append("</table></body>");

		htmlTableBuilder.append("<head>").append("<br><br><br><br><br><h1>Thanks & Regards<h1>").append("</head>").append("<body>");

		// then print the result
		// System.out.println(htmlTableBuilder.toString());
		return htmlTableBuilder.toString();

	}
}
