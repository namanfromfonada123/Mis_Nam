package mis.com.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import mis.com.bean.DataContainer;
import mis.com.common.Constants;
import mis.com.service.UserMappingUploadService;

@RestController
public class UserIpMappingFileUploadController {
	public static final Logger Logger = LoggerFactory.getLogger(UserIpMappingFileUploadController.class);
	@Value("${uploadUserIpMappingFileKey}")
	String uploadUserIpMappingFileKey;

	@Autowired
	private UserMappingUploadService userMappingUploadService;

	@PostMapping(value = "/uploadUserIpMappingFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String uploadUserIpMappingFile(@RequestPart("file") MultipartFile file,
			@RequestParam("apiKey") String apiKey) throws IOException {
		// List<InComingDidEntity> saveIncomingDidList = null;
		Logger.info("**** Inside FileUploadController.uploadInComingDidFile() *****");

		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		if (!apiKey.equals(uploadUserIpMappingFileKey)) {
			Logger.info("**** Api Key Not Found *****" + apiKey);

			data.setStatus(Constants.REQUEST_FAILED);
			data.setMsg("Api Key Not Valid.");
			return new Gson().toJson(data).toString();
		}
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = userMappingUploadService.uploadUserIpMappingFile(file);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Success*****"
							+ data.toString());
				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg + ":" + file.getOriginalFilename());
					Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Faild*****"
							+ data.toString());
				}
				// return ResponseEntity.status(HttpStatus.OK).body(new
				// ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!");
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);
				Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Exception*****"
						+ data.toString());
				// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
				// ResponseMessage(message));
			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);
			Logger.info("**** Inside  FileUploadController.uploadInComingDidFile() Got Invalid Format*****"
					+ data.toString());
		}
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
		// ResponseMessage(message));
		Logger.info("**** Successfully Executed Inside  FileUploadController.uploadInComingDidFile() *****");
		return new Gson().toJson(data).toString();

	}

}
