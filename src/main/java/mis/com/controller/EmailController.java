package mis.com.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import mis.com.bean.DataContainer;
import mis.com.common.Constants;
import mis.com.entity.EmailEntity;
import mis.com.repository.EmailRepository;
import mis.com.service.ConvertHTMLFormatToSendEmailBodyService;
import mis.com.service.SendEmail;
import mis.com.utils.DateUtils;

@RestController
public class EmailController {

	@Autowired
	private EmailRepository emailRepo;


	
	@Autowired
	private ConvertHTMLFormatToSendEmailBodyService convertHtmlService;
	@Autowired
	private SendEmail sendEmail;

	@RequestMapping(value = "/createEmail", method = RequestMethod.POST)
	public String createEmail(@RequestBody EmailEntity emailEntity) {
		DataContainer data = new DataContainer();
		EmailEntity saveEmail = null;
		if (emailEntity.getMail_to() == null) {
			data.setMsg("Email To Cannot Be Empty.");
			data.setStatus(200);
		} else {
			emailEntity.setCreateDate(DateUtils.convertDateToString(new Date()));
			saveEmail = emailRepo.save(emailEntity);
			data.setMsg("Success");
			data.setStatus(200);
			data.setData(saveEmail);
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findByEmailId", method = RequestMethod.POST)
	public String findByEmailId(@RequestParam("id") Integer id) {
		DataContainer data = new DataContainer();
		Optional<EmailEntity> saveEmail = null;
		saveEmail = emailRepo.findById(id);
		if (saveEmail.isPresent()) {
			data.setMsg("Success");
			data.setStatus(200);
			data.setData(saveEmail);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(201);
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findByAll", method = RequestMethod.GET)
	public String findByAll() {
		DataContainer data = new DataContainer();
		List<EmailEntity> saveEmail = null;
		saveEmail = emailRepo.findAll();
		if (saveEmail.size() > 0) {
			data.setMsg("Success");
			data.setStatus(200);
			data.setData(saveEmail);
		} else {
			data.setMsg(Constants.RECORD_NOT_EXISTS_STRING);
			data.setStatus(201);
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public String deleteAll() {
		DataContainer data = new DataContainer();
		emailRepo.deleteAll();
		data.setMsg("Success");
		data.setStatus(200);
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String sendEmail(@RequestParam("yyyyMMddDate") String yyyyMMddDate) throws ParseException {

		// return convertExcel.convertExcelToHtml();
		return sendEmail.sendDailyReport(yyyyMMddDate, convertHtmlService.makeHtmlFormatForOBDReport());
	}


	
}
