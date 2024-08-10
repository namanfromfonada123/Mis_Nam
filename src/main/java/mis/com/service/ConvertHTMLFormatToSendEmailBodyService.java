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
public class ConvertHTMLFormatToSendEmailBodyService {
	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;

	public String makeHtmlFormatForOBDReport() throws ParseException {
		String[] reportArray = { "FTD", "MTD", "LMTD" };
		StringBuilder htmlTableBuilder = new StringBuilder();
		for (String arr : reportArray) {
			LocalDate todaydate = LocalDate.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			List<Object[]> summaryReportList = null;
			if (reportArray[0].equalsIgnoreCase(arr)) {
				summaryReportList = misCallDetailsSummaryRepository
						.getDailySummaryMisCallDetailsByCurrentDate(DateUtils.getYesterdayDateString());

			}
			if (reportArray[1].equalsIgnoreCase(arr)) {
				summaryReportList = misCallDetailsSummaryRepository.getDailySummaryMisCallDetaiMonthly(
						dateTimeFormatter.format(todaydate.withDayOfMonth(1)), DateUtils.getYesterdayDateString());
			}
			if (reportArray[2].equalsIgnoreCase(arr)) {
				summaryReportList = misCallDetailsSummaryRepository.getDailySummaryMisCallDetaiMonthly(
						DateUtils.getLastMonthDateByGivenDate(dateTimeFormatter.format(todaydate.withDayOfMonth(1))),
						DateUtils.getLastMonthDateByGivenDate(DateUtils.getYesterdayDateString()));
			}

			// open the table
			htmlTableBuilder.append("<head>").append("<h1>" + arr + " Report::<h1>").append("</head>").append("<body>");
			htmlTableBuilder.append("<table border=\"1\">").append(System.lineSeparator());
			// open a rown, header row here
			htmlTableBuilder.append("\t").append("<tr style=color:white;background-color:green>")
					.append(System.lineSeparator());
			// add the headers to the header row
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Account Name").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("User Name").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Total MSISDN").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Valid MSISDN").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Attempted Calls").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Connected Calls").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Total Bill Sec.").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append("Credits Used").append("</th>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<th>").append(arr + " % SC").append("</th>")
					.append(System.lineSeparator());

			// close the header row
			htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
			DecimalFormat value = new DecimalFormat("#.#");
			// add a data row for each PetrolData
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
						for (Object[] summary : accountList) {
							// open a row again

							htmlTableBuilder.append("\t").append("<tr>").append(System.lineSeparator());
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
							if (summary[3].equals(0.0)) {
								htmlTableBuilder.append("\t").append("\t").append("<td>").append(0 + "%")
										.append("</td>").append(System.lineSeparator());
								;
							} else {
								htmlTableBuilder.append("\t").append("\t").append("<td>")
										.append(String.valueOf(value.format(Float.valueOf(String.valueOf(summary[5]))
												/ (int) Math.round(Float.valueOf(String.valueOf(summary[3]))) * 100)
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
							htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
						}
						htmlTableBuilder.append("\t").append("<tr style=color:white;background-color:green>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append("").append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(account + " Total::")
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlalMsisdn).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalValidMsisdn)
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlaAttemptedCalls)
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalConnectedCalls)
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalBillSec).append("</td>")
								.append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalCreditsUser)
								.append("</td>").append(System.lineSeparator());
						htmlTableBuilder.append("\t").append("\t").append("<td>")
								.append(Float
										.valueOf(String.valueOf(value
												.format(Float.valueOf(totalConnectedCalls) / totalValidMsisdn * 100)))
										+ "%")
								.append("</td>");
						// close the row
						htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
					}
				}

			} // and close the table
			htmlTableBuilder.append("</table></body>");
		}
		htmlTableBuilder.append("<head>").append("<h1>Thanks & Regards<h1>").append("</head>").append("<body>");

		// then print the result
		// System.out.println(htmlTableBuilder.toString());
		return htmlTableBuilder.toString();

	}
}
