package mis.com.Naman.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mis.com.Naman.pojos.ClientWiseReport;
import mis.com.Naman.repository.ReportRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ClientWiseReportService {

    @Autowired
    private ReportRepository repository;

    public List<ClientWiseReport> getClientWiseReports(String clientName) {
        List<Object[]> results = repository.getClientWistReportofyesterDay(clientName);
        List<ClientWiseReport> reports = new ArrayList<>();
        
   	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        
        for (Object[] row : results) {
        	
        	Date formatteDate=null;
			try {
				formatteDate = dateFormat.parse(dateFormat.format(((Date) row[0])));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            ClientWiseReport report = new ClientWiseReport(
            		formatteDate, // execution_date
                (String) row[1], // clientname
                (String) row[2], // username
                ((Number) row[3]).intValue(), // totalmsisdn
                ((Number) row[4]).intValue(), // validmsisdn
                ((Number) row[5]).intValue(), // attemptedcalls
                ((Number) row[6]).intValue(), // connectedcalls
                ((Number) row[7]).intValue(), // digitpressed
                ((Number) row[8]).doubleValue(), // listenrate
                ((Number) row[9]).intValue(), // totalbillsec
                ((Number) row[10]).intValue(), // creditused
                (String) row[11] // panelname
            );
            reports.add(report);
        }

        return reports;
    }
}
