package mis.com.Naman.ApiCall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BackendApiCall {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${slack.urlString}")
	String slackurl;
	
	@Value("${slack.urlStringObd}")
	String slackurlObd;
	
//	slack.urlString=https://app.flash49.com/AlertManagmentSystem/save?appName=liveVideo&error=[error]&time=[time]&description=[description]&priority=low

	Logger logger = LoggerFactory.getLogger(BackendApiCall.class);
	
	public void SlackAlert(String error, String Description) {
		if (!slackurl.isEmpty()) {

			String dataString ="{\"text\" :\" AppName : Mis -\n error:   " + error
					+ "\n  Description: "+ Description + "  \" }";
			
			logger.info("Inside Slack: "+ dataString);

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<Object> httpEntity = new HttpEntity<Object>(dataString, headers);
				logger.info("Trying to Generate Alert over slack !! ");
				ResponseEntity<String> response = restTemplate.exchange(slackurl,
						HttpMethod.POST, httpEntity, String.class);
				logger.info("Alert over slack Generated Successfully !! | StatusCode: " + response.getStatusCodeValue());
				

			} catch (Exception e) {
				logger.error("Getting error During Alert Generation !!" + e.getLocalizedMessage() + " " + dataString);
			}
		}else {
			logger.info("Please Configure Slack url conversationaldata_Request_slackUrlApi");
		}
	}
	
	public void SlackAlertforObd(String error, String Description) {
		if (!slackurl.isEmpty()) {

			String dataString ="{\"text\" :\" AppName : Mis -\n error:   " + error
					+ "\n  Description: "+ Description + "  \" }";
			
			logger.info("Inside Slack: "+ dataString);

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<Object> httpEntity = new HttpEntity<Object>(dataString, headers);
				logger.info("Trying to Generate Alert over slack !! ");
				ResponseEntity<String> response = restTemplate.exchange(slackurlObd,
						HttpMethod.POST, httpEntity, String.class);
				logger.info("Alert over slack Generated Successfully !! | StatusCode: " + response.getStatusCodeValue());
				

			} catch (Exception e) {
				logger.error("Getting error During Alert Generation !!" + e.getLocalizedMessage() + " " + dataString);
			}
		}else {
			logger.info("Please Configure Slack url conversationaldata_Request_slackUrlApi");
		}
	}

}
