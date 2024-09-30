package mis.com.Naman.Controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mis.com.Naman.Service.ConvertUserClientMappingfromFile;

@RequestMapping("/Mis/Report")
@RestController
public class UserClientMappingFileUpload {

	@Autowired
	ConvertUserClientMappingfromFile clientMappingfromFile;

	Logger logger = LoggerFactory.getLogger(UserClientMappingFileUpload.class);

	@PostMapping(value = "/uploadUserClientMappingFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> uploadUserIpMappingFile(@RequestPart("file") MultipartFile file) throws IOException {

		try {
			logger.info("**** Inside FileUploadController.uploadInComingDidFile() *****");

			if ("csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

				String reString = clientMappingfromFile.uploaduserClientCsvToDatabase(file.getInputStream());

				if (reString.equals("File Uploaded Successfully"))
					return ResponseEntity.ok(reString);
				else
					return ResponseEntity.badRequest().body(reString);

			} else {
				return ResponseEntity.badRequest().body("Invalid File Format, Format Should be .csv !! ");
			}

		} catch (Exception e) {

			return ResponseEntity.internalServerError().body("SomeException Occur : " + e.getMessage());
		}
	}
}
