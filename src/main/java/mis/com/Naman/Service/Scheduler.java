package mis.com.Naman.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mis.com.Naman.pojos.ClientWiseReport;
import mis.com.Naman.repository.ReportRepository;
import mis.com.Naman.repository.userClientRepository;

@Component
public class Scheduler {
	
	@Autowired
	reportEntityService rEntityService;

	@Autowired
	ReportRepository reportRepository;
	
	@Autowired
	userClientRepository userClientRepository;
	
	@Autowired
	ClientWiseReportService clientWiseReportService;
	
	@Autowired
	ConvertClientDataToCsv convertClientDataToCsv;
	
	
	Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	
//	@Scheduled(fixedDelay = 84600000)
@Scheduled(cron = "${misis.schedule.time}")
	public void ReadAllFileFromAFolder() 
	{
		try {
			
			logger.info("Inserting Files to dataBase  !!");
					rEntityService.insertFilesToDataBase();
			
			for(String client : userClientRepository.findAllClients())
			{
		
				List<ClientWiseReport> clientwisemisisReportlist= clientWiseReportService.getClientWiseReports(client);
				
				if (!clientwisemisisReportlist.isEmpty()) {
					convertClientDataToCsv.generateCsv(clientwisemisisReportlist, client);

				}
				else {
					logger.info("No misis data present for this client : "+ client);
				}
				
			}	
		} catch (Exception  e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		}
	
	
	

	
	
}
