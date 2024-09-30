package mis.com.Naman.Service;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mis.com.Naman.Modal.username_client_mapping;
import mis.com.Naman.repository.userClientRepository;


@Service
public class ConvertUserClientMappingfromFile {

	@Autowired
	userClientRepository userClientRepository;
	
	Logger logger = LoggerFactory.getLogger(ConvertUserClientMappingfromFile.class);
	
	public String uploaduserClientCsvToDatabase(InputStream userClientbyes) 
	{
		try { 
			
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(userClientbyes,
				 "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader() );
			
				System.out.println(csvParser.getHeaderNames());
				
				String csvHeader = "username,client_name,panel_name";
				
				for(String header : csvParser.getHeaderNames())
				{
					if (!csvHeader.contains(header)) {
						return "Invalid Header in csv file, "+csvParser.getHeaderNames().toString()+"\n Required Header : "+csvHeader;
					}
				}
				
			for (CSVRecord csvRecord : csvParser) {
				
				
				try {
					username_client_mapping userClient_mapping = new username_client_mapping();
					
					userClient_mapping.setUsername(csvRecord.get("username"));
					userClient_mapping.setClient_name(csvRecord.get("client_name"));
					userClient_mapping.setPanel_name(csvRecord.get("panel_name"));	
					userClientRepository.save(userClient_mapping);
				} catch (Exception e) {
					logger.info("Error : "+e.getLocalizedMessage());
				}
				
//				try {
////					userClientRepository.insertdataTouserClientMappingIgnorecase( csvRecord.get("client_name"), csvRecord.get("panel_name"),csvRecord.get("username"));
//
//					userClientRepository.insertdataTouserClientMapping( csvRecord.get("client_name"), csvRecord.get("panel_name"),csvRecord.get("username"));
//
//					
//				} catch (Exception e) {
//				
//					logger.info("Error : "+e.getLocalizedMessage());
//				}

			}
			
			csvParser.close();
			
			return "File Uploaded Successfully";

		}
		catch (Exception e) {
			logger.info(e.getLocalizedMessage());
			
			return "SomeException Occur : "+ e.getLocalizedMessage();
		}
	}
	
}
