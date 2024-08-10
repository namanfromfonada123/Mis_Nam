package mis.com.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import mis.com.bean.DataContainer;
import mis.com.common.Constants;
import mis.com.entity.MisCallDetailSummaryReport;
import mis.com.repository.MisCallDetailsSummaryRepository;
import mis.com.service.MisUploadService;

@RestController
public class MISReportController {

	@Autowired
	private MisCallDetailsSummaryRepository misCallDetailsSummaryRepository;
	@Autowired
	private MisUploadService misUploadService;

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public String findAllRecord() {
		DataContainer data = new DataContainer();
		List<MisCallDetailSummaryReport> misList = misCallDetailsSummaryRepository.findAll();
		if (misList.size() > 0) {
			data.setMsg("Success");
			data.setStatus(200);
			data.setData(misList);
		} else {
			data.setMsg("Data Not Found.");
			data.setStatus(200);
		}

		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteAllRecord", method = RequestMethod.GET)
	public String DeleteAllRecord() {
		DataContainer data = new DataContainer();
		misCallDetailsSummaryRepository.deleteAll();
		data.setMsg("All Record Successfully Deleted.");
		data.setStatus(200);
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findRecordbyGivenDate", method = RequestMethod.GET)
	public String findByGivenDate(@RequestParam(required = false) String startFrom,
			@RequestParam(required = false) String endTo) {
		DataContainer data = new DataContainer();
		List<MisCallDetailSummaryReport> misList = null;

		if (Objects.isNull(endTo)) {
			misList = misCallDetailsSummaryRepository.getMisCallDetailByGivenDate(startFrom);
		}
		if (Objects.nonNull(endTo) && Objects.nonNull(startFrom)) {
			misList = misCallDetailsSummaryRepository.getMisCallDetaiByGivenStartAndEndDate(startFrom, endTo);
		}
		if (misList.size() > 0) {
			data.setMsg("All Record Successfully Deleted.");
			data.setStatus(200);
			data.setData(misList);
		} else {
			data.setMsg("Data Not Found.");
			data.setStatus(200);
		}

		return new Gson().toJson(data).toString();
	}

	@PostMapping(value = "/uploadMISReportMappingFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String uploadUserIpMappingFile(@RequestPart("file") MultipartFile file) throws IOException {

		DataContainer data = new DataContainer();
		CompletableFuture<String> responseMsg = null;
		File convFile = new File(file.getOriginalFilename());
		if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "csv".equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))
				|| "xls".equals(
						file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1))) {

			try {
				responseMsg = misUploadService.createMisReport(convFile);
				if (responseMsg.get().contains(Constants.SUCCESS_MSG)) {
					data.setStatus(Constants.REQUEST_SUCCESS);
					data.setMsg(responseMsg.get() + ":" + file.getOriginalFilename());

				} else {
					data.setStatus(Constants.REQUEST_FAILED);
					data.setMsg(responseMsg + ":" + file.getOriginalFilename());

				}

			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg(Constants.FILE_NOT_UPLOAD + ":" + file.getOriginalFilename() + "!");
				data.setRequest_status(HttpStatus.EXPECTATION_FAILED.toString());
				data.setStatus(Constants.REQUEST_FAILED);

			}
		} else {
			data.setMsg(Constants.INVALID_FORMAT);
			data.setStatus(Constants.NOT_FOUND);

		}
		return new Gson().toJson(data).toString();

	}

	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
