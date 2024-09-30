package mis.com.Naman.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import mis.com.Naman.Modal.CallData;
import mis.com.Naman.pojos.ClientWiseReport;
import mis.com.Naman.pojos.NoMappingUser;
import mis.com.Naman.repository.ReportRepository;
import mis.com.Naman.repository.userClientRepository;
import mis.com.repository.EmailRepository;

@Component
public class Scheduler {

	@Autowired
	reportEntityService rEntityService;

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	userClientRepository userClientRepository;

	@Autowired
	ConvertQueryObjectDataToJavaObject convertQueryObjectDataToJavaObject;

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	ConvertClientDataToCsv convertClientDataToCsv;

	@Autowired
	ConvertAllClientDataToCsv2 convertAllClientDataToCsv2;

	@Autowired
	CsvService csvService;

	Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Scheduled(cron = "${misis.schedule.timeDb}")
//	@Scheduled(fixedDelay = 86000000)
	public void ReadAllFileFromAFolder() {
		try {

			logger.info("Inserting Files to dataBase  !!");
			try {
				rEntityService.insertFilesToDataBase();

			} catch (Exception e) {
				logger.error("Error while inserting file to DataBase : " + e.getLocalizedMessage());

				e.printStackTrace();
			}

			// alert for no mis data
//			for (username_client_mapping clientUsermapping : userClientRepository.findAll()) {
//				List<CallData> calldate = reportRepository.findcallDatabyClientPanelUser(
//						clientUsermapping.getPanel_name(), clientUsermapping.getUsername(),
//						clientUsermapping.getClient_name());
//
//				if (calldate.size() == 0) {
//
//					logger.info("Calling SlackAlert Api for client : " + clientUsermapping.getClient_name()
//							+ " having username : " + clientUsermapping.getUsername() + " for panel : "
//							+ clientUsermapping.getPanel_name());
//					csvService.SlackAlertForNoMisDataService(clientUsermapping.getClient_name(),
//							clientUsermapping.getUsername(), clientUsermapping.getPanel_name());
//				}
//
//			}

			// show no mis data panel wise
			for (String PanelName : userClientRepository.findAllPanelName()) {
				List<CallData> callData = reportRepository.findcallDatabyPanel(PanelName);

				if (callData.size() == 0) {
					logger.info("No Mis Data Found for panel : "+ PanelName);
					csvService.SlackAlertForNoMisDataService(PanelName);
				}
			}

			// Alert for no mapping user
			List<NoMappingUser> NoMappingCallData = convertQueryObjectDataToJavaObject.getNoMappingUserList();

			if (!NoMappingCallData.isEmpty()) {
				for (NoMappingUser noMappingUser : NoMappingCallData) {
					logger.info("Calling SlackAlert Api for client : " + noMappingUser.getclientName()
							+ " having username : " + noMappingUser.getUserName() + " for panel : "
							+ noMappingUser.getPanelName());
					csvService.SlaclAlertForNoClienUserMapping(noMappingUser.getclientName(),
							noMappingUser.getUserName(), noMappingUser.getPanelName());

				}
			}

		} catch (Exception e) {

			logger.info(e.getMessage());
			e.printStackTrace();
		}

	}

	 @Scheduled(cron = "${misis.schedule.timeEmail}")
//	 @Scheduled(fixedDelay = 86000000)
	public void ExtractDataFromDbAndSendEmail() {
		try {

			// Make an email for all clients
			for (String client : userClientRepository.findAllClients()) {
				List<ClientWiseReport> clientwisemisisReportlist = convertQueryObjectDataToJavaObject
						.getClientWiseReports(client);

				if (!clientwisemisisReportlist.isEmpty()) {
					
					logger.info("Sending mail to client has Stopped !!");
//					convertClientDataToCsv.generateCsv(clientwisemisisReportlist, client);
				}

			}

			// make email to opertion of all clients
			List<ClientWiseReport> clientWiseReportsOfAllClient = convertQueryObjectDataToJavaObject.getAllClient();

			if (!clientWiseReportsOfAllClient.isEmpty()) {
				convertAllClientDataToCsv2.generateAllClientCsv(clientWiseReportsOfAllClient, "Operation");
			} else {
				logger.info("No Client data Found of Date: {} For Sending to operation :", LocalDate.now().minusDays(1));
			}

		} catch (Exception e) {

			logger.info(e.getMessage());
			e.printStackTrace();
		}

	}

}
