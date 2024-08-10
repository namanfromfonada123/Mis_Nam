package mis.com.Naman.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mis.com.Naman.repository.ReportRepository;

@Service
public class reportEntityService {

	@Autowired
	ReportRepository reportRepository;
	
	@Value("${Csv.filePath}")
	String folderPath;
	
	@Autowired
	CsvService csvService;

	Logger logger = LoggerFactory.getLogger(reportEntityService.class);

	
	public List<String> listFilesForFolder(final File folder) {
		List<String> filepath = new LinkedList<String>();
			    for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            continue;
			        } else {
			            if(fileEntry.getName().contains(".csv"))
			            	filepath.add(fileEntry.getPath());
			        }
			    }
			return filepath;
	}
	
	public void insertFilesToDataBase() throws IOException, ParseException
	{
		File folder = new File(folderPath);
		
		logger.info("Path of misis file : "+ folder.getPath());
		
		List<String> filepaths = this.listFilesForFolder(folder);
		
		for(String filepath: filepaths)
		{			
			csvService.readCsvAndSaveToDatabase(filepath);
		}
		
	}
	
}
