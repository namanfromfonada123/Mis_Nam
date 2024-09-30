package mis.com.Naman.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

import org.springframework.stereotype.Service;

import mis.com.Naman.pojos.ClientWiseReport;

@Service
public class ConvertAllClientMisToHtml {
//	@Autowired
//	private ClientWiseReportService clientWiseReportService;

	public String makeHtmlFormatForOBDReport(String Client, List<ClientWiseReport> ClientReportList)
			throws ParseException {
		StringBuilder htmlTableBuilder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Integer totlalMsisdn = 0;
		Integer totalValidMsisdn = 0;
		Integer totlaAttemptedCalls = 0;
		Integer totalConnectedCalls = 0;
		Integer totalBillSec = 0;
		Integer totalCreditsUser = 0;
		Integer totaldigitpressed = 0;
		double totallinstenrate = 0;
		String formatedDate = "";

		// open the table
		htmlTableBuilder.append("<html><head>").append("<style>")
				.append("table { width: 100%; border-collapse: collapse; }")
				.append("th, td { padding: 8px 12px; text-align: left; border: 1px solid #ddd; }")
				.append("th { background-color: #92a8d1; color: black; }").append("h4 { color: #333; }").append("p{ font-size: Bold; color : Black;}")
				.append("</style>").append("<h4>" + "Hi " + Client + ", </h4><h4> Collect Your Report::" + "</h4>")
				.append("</head><body>");

//	    htmlTableBuilder.append("<h4>" +"Hi "+Client+", </h4><br><h4> Collect Your Report::" +"</h4><br>");

		htmlTableBuilder.append("<table  >").append(System.lineSeparator());
		// open a rown, header row here
		htmlTableBuilder.append("\t").append("<tr >").append(System.lineSeparator());
		// add the headers to the header row
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("EXECUTION_DATE").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("CLIENT_NAME").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("USER_NAME").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("TOTAL_MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("VALID_MSISDN").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("ATTEMPTED_CALLS").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("CONNECTED_CALLS").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("DIGIT_PRESSED").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("LISTEN_RATE").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("TOTAL_BILL_SEC").append("</th>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<th>").append("CREDIT_USED").append("</th>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<th>").append("PANEL_NAME").append("</th>")
				.append(System.lineSeparator());

		// close the header row
		htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());
		DecimalFormat value = new DecimalFormat("#.#");
		// add a data row for each PetrolData
//			List<ClientWiseReport> ClientWiseReports = clientWiseReportService.getClientWiseReports(client);

		System.out.println("ClientWiseReports : " + ClientReportList.toString());
		for (ClientWiseReport clientWiseReport : ClientReportList) {

			// open a row again

			htmlTableBuilder.append("\t").append("<tr>").append(System.lineSeparator());
			// add the data
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(dateFormat.format(clientWiseReport.getExecution_date())).append("</td>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getClientname())
					.append("</td>").append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getUsername())
					.append("</td>").append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(Integer.valueOf(clientWiseReport.getTotalmsisdn())).append("</td>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(Integer.valueOf(clientWiseReport.getValidmsisdn())).append("</td>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(Integer.valueOf(clientWiseReport.getAttemptedcalls())).append("</td>")
					.append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(Integer.valueOf(clientWiseReport.getConnectedcalls())).append("</td>")
					.append(System.lineSeparator());

			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getDigitpressed())
					.append("</td>").append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>")
					.append(Math.round(clientWiseReport.getListenrate()) + "%").append("</td>")
					.append(System.lineSeparator());

			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getTotalbillsec())
					.append("</td>").append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getCreditused())
					.append("</td>").append(System.lineSeparator());
			htmlTableBuilder.append("\t").append("\t").append("<td>").append(clientWiseReport.getPanelname())
					.append("</td>").append(System.lineSeparator());

			htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());

			totlalMsisdn = totlalMsisdn + (int) Math.round(Double.valueOf(clientWiseReport.getTotalmsisdn()));
			totalValidMsisdn = totalValidMsisdn + (int) Math.round(Double.valueOf(clientWiseReport.getValidmsisdn()));
			totlaAttemptedCalls = totlaAttemptedCalls
					+ (int) Math.round(Double.valueOf(clientWiseReport.getAttemptedcalls()));
			totalConnectedCalls = totalConnectedCalls
					+ (int) Math.round(Double.valueOf(clientWiseReport.getConnectedcalls()));
			totalBillSec = totalBillSec + (int) Math.round(Float.valueOf(clientWiseReport.getTotalbillsec()));
			totalCreditsUser = totalCreditsUser + (int) Math.round(Double.valueOf(clientWiseReport.getCreditused()));

			totaldigitpressed = totaldigitpressed
					+ (int) Math.round(Double.valueOf(clientWiseReport.getDigitpressed()));
			totallinstenrate = totallinstenrate + clientWiseReport.getListenrate();

		}
		htmlTableBuilder.append("\t").append("<tr >").append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td colspan=\"3\">").append("Total").append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlalMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalValidMsisdn).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totlaAttemptedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalConnectedCalls).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totaldigitpressed).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>")
				.append(Math.round(totallinstenrate / ClientReportList.size()) + "%").append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalBillSec).append("</td>")
				.append(System.lineSeparator());
		htmlTableBuilder.append("\t").append("\t").append("<td>").append(totalCreditsUser).append("</td>")
				.append(System.lineSeparator());

		htmlTableBuilder.append("\t").append("\t").append("<td>").append("").append("</td>")
				.append(System.lineSeparator());

		// close the row
		htmlTableBuilder.append("\t").append("</tr>").append(System.lineSeparator());

		htmlTableBuilder.append("</table></body>");

		htmlTableBuilder.append("<p>This is an automated report and may differ slightly from the final monthly billing data</p>");
		
		htmlTableBuilder.append("<head>").append("<h4>Thanks & Regards<h4>").append("</head>").append("<body>");

		return htmlTableBuilder.toString();

	}
}
