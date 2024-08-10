package mis.com.Naman.Service;

import mis.com.Naman.Modal.CallData;
import mis.com.Naman.Modal.username_client_mapping;
import mis.com.Naman.repository.ReportRepository;
import mis.com.Naman.repository.userClientRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CsvService {

    @Autowired
    private ReportRepository ReportRepository;

    @Autowired
    private userClientRepository uClientRepository;

    public void readCsvAndSaveToDatabase(String filePath) throws IOException, ParseException {
        File file = new File(filePath);
        Path sourcePath = file.toPath();
if (file.exists()) {
        try (FileReader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (CSVRecord csvRecord : csvParser) {
                CallData callData = new CallData();
                callData.setExecutionDate(dateFormat.parse( csvRecord.get("EXECUTION_DATE")));
                
                callData.setUserName(csvRecord.get("USER_NAME"));
                callData.setTotalMsisdn(Integer.parseInt(csvRecord.get("TOTAL_MSISDN")));
                callData.setValidMsisdn(Integer.parseInt(csvRecord.get("VALID_MSISDN")));
                callData.setAttemptedCalls(Integer.parseInt(csvRecord.get("ATTEMPTED_CALLS")));
                callData.setConnectedCalls(Integer.parseInt(csvRecord.get("CONNECTED_CALLS")));
                callData.setDigitPressed(Integer.parseInt(csvRecord.get("DIGIT_PRESSED")));
                callData.setListenRate(Double.parseDouble(csvRecord.get("LISTEN_RATE")));
                callData.setTotalBillSec(Integer.parseInt(csvRecord.get("TOTAL_BILL_SEC")));
                callData.setCreditUsed(Integer.parseInt(csvRecord.get("CREDIT_USED")));
                callData.setPanelName(csvRecord.get("PANEL_NAME"));

                String panelName = csvRecord.get("PANEL_NAME");
                String username = csvRecord.get("USER_NAME");
               Optional<username_client_mapping> clientUserNameMapping = uClientRepository.findByPanel_nameAndUsername(panelName, username);
//             callData.setClientName(clientName.map(username_client_mapping::getClient_name).orElse("NO_MAPPING"));
             
               if (clientUserNameMapping.isPresent()) {
            	   callData.setClientName(clientUserNameMapping.get().getClient_name());
			}
               else {
				callData.setClientName("NO_MAPPING");
			}

                ReportRepository.save(callData);
            }
            
        }
        
        moveFile(sourcePath, file.getParentFile().getPath());

}
else {
	System.out.println("No File Found !! ");
}
    }

    private void moveFile(Path sourcePath, String archiveDir) throws IOException {
        // Create archive directory if it doesn't exist
        Path archiveFolder = Paths.get(archiveDir, "Misis_"+LocalDate.now().toString()+"_backup");
        if (Files.notExists(archiveFolder)) {
            Files.createDirectories(archiveFolder);
        }

        // Define the destination path
        Path destinationPath = archiveFolder.resolve(sourcePath.getFileName());

        // Check if the destination file already exists
        if (Files.exists(destinationPath)) {
            throw new IOException("File already exists in the destination directory.");
        }

        // Move the file
        Files.move(sourcePath, destinationPath);
    }
}
