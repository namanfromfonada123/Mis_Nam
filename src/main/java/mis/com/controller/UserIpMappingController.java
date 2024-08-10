package mis.com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
//import com.spire.ms.System.Collections.ArrayList;

import mis.com.bean.DataContainer;
import mis.com.bean.UserIpMapPojo;
import mis.com.entity.UserIpMapping;
import mis.com.repository.UserIPMappingRepository;

@RestController
@RequestMapping("/userIpMapping")
public class UserIpMappingController {
	public static final Logger Logger = LoggerFactory.getLogger(UserIpMappingController.class);
	@Value("${apiKey}")
	String apiKey;
	@Autowired
	private UserIPMappingRepository userIpMappingRepository;

	@RequestMapping(value = "/createUserIpMapping", method = RequestMethod.POST)
	public String createUserIPMapping(@RequestBody List<UserIpMapPojo> userIpMapPojo,
			@RequestParam("apiKeys") String apiKeys) {
		List<DataContainer> dataList = new java.util.ArrayList<DataContainer>();
		List<UserIpMapping> addList = new java.util.ArrayList<UserIpMapping>();
		List<UserIpMapping> saveUserIPMap = null;
		UserIpMapping usrIpMap = null;
		DataContainer data = null;
		Logger.info("***** UserIPMapping Request Got Total Request:: ***" + userIpMapPojo.size() + " *** "
				+ userIpMapPojo.toString());
		if (apiKeys.equals(apiKey)) {

			try {
				if (userIpMapPojo.size() > 0) {
					for (UserIpMapPojo ip : userIpMapPojo) {
						usrIpMap = new UserIpMapping();
						usrIpMap.setCreated_date(convertDateToString(new Date()));
						usrIpMap.setAccountUser(ip.getAccountUser());
						usrIpMap.setCampaignName(ip.getCampaignName());
						usrIpMap.setIpAddress(ip.getIpAddress());
						usrIpMap.setUserName(ip.getUserName());
						usrIpMap.setUserType(ip.getUserType());
						addList.add(usrIpMap);
					}
					for (UserIpMapping uIM : addList) {
						if (userIpMappingRepository.findFirstByIpAddressAndUserNameAndCampaignName(uIM.getIpAddress(),
								uIM.getUserName(), uIM.getCampaignName()) == null) {
							data = new DataContainer();
							saveUserIPMap = userIpMappingRepository.saveAll(addList);
							Logger.info("***** Records Saved SuccessFully *****");
							data.setData(saveUserIPMap);
							data.setMsg("Added Successfully.");
							data.setStatus(200);
							dataList.add(data);
						} else {
							data = new DataContainer();
							data.setMsg("**Record Already Exists Given IPUserCamp*** " + uIM.getIpAddress() + " And "
									+ uIM.getUserName() + " And " + uIM.getCampaignName());
							data.setStatus(201);
							Logger.info("**Record Already Exists Given IPUserCamp*** " + uIM.getIpAddress() + " And "
									+ uIM.getUserName() + " And " + uIM.getCampaignName());
							dataList.add(data);
						}
					}
				} else {
					data = new DataContainer();
					Logger.info("***** UserIpMapping Object Cannot By Empty. *****");
					data.setStatus(404);
					data.setMsg("Failed");
					dataList.add(data);
				}
			} catch (Exception e) {
				data = new DataContainer();
				Logger.info("***** Got Exception:: " + e.getMessage());
				data.setMsg("Got Exception:: " + e.getMessage());
				data.setStatus(400);
				dataList.add(data);
				e.printStackTrace();
			}
		} else {
			data = new DataContainer();
			Logger.info("***** Api Key Does Not Exist. *****");
			data.setStatus(404);
			data.setMsg("Api Key Does Not Exist. Please Enter Correct Key");
			dataList.add(data);
		}
		return new Gson().toJson(dataList).toString();
	}

	@RequestMapping(value = "/findByIdUserMapping", method = RequestMethod.POST)
	public String findByIdUserMapping(@RequestParam("id") Long id, @RequestParam("apiKeys") String apiKeys) {
		Logger.info("***** Request For findByIdUserMapping ***" + id);

		DataContainer data = new DataContainer();
		Optional<UserIpMapping> userIpMapping = null;
		if (apiKeys.equals(apiKey)) {
			try {
				userIpMapping = userIpMappingRepository.findById(id);
				if (userIpMapping.isPresent()) {
					Logger.info("***** Response For findByIdUserMapping ***" + userIpMapping.get().toString());
					data.setData(userIpMapping);
					data.setMsg("Success");
					data.setStatus(200);
				} else {
					Logger.info("***** Records Doesn't Exits. *****" + id);

					data.setStatus(404);
					data.setMsg("Records Doesn't Exits.");
				}
			} catch (Exception e) {
				Logger.info("***** Got Exception:: " + e.getMessage());

				data.setStatus(404);
				data.setMsg("Got Exception::" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			data = new DataContainer();
			Logger.info("***** Api Key Does Not Exist. *****");
			data.setStatus(404);
			data.setMsg("Api Key Does Not Exist. Please Enter Correct Key");
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteByUserIpMappingId", method = RequestMethod.POST)
	public String deleteByUserIpMappingId(@RequestParam("id") Long id, @RequestParam("apiKeys") String apiKeys,
			@RequestParam(value = "deleteReason", required = false) String deleteReason) {
		Logger.info("***** Delete Request For deleteByUserIpMappingId and Reason ***" + id + " Delete Reason "
				+ deleteReason);
		DataContainer data = new DataContainer();
		Optional<UserIpMapping> userIpMapping = null;
		if (deleteReason == null || deleteReason.isEmpty() || deleteReason.isEmpty()) {
			data = new DataContainer();
			Logger.info("***** Enter Delete Reason *****");
			data.setStatus(404);
			data.setMsg("Enter Delete Reason");
			return new Gson().toJson(data).toString();
		}

		if (apiKeys.equals(apiKey)) {
			try {
				userIpMapping = userIpMappingRepository.findById(id);
				if (userIpMapping.isPresent()) {
					Logger.info("***** Delete Request Success ***" + userIpMapping.get().toString());

					userIpMappingRepository.deleteById(id);
					data.setMsg("Success");
					data.setStatus(200);
				} else {
					Logger.info("***** Records Doesn't Exits. *****" + id);

					data.setStatus(404);
					data.setMsg("Records Doesn't Exits.");
				}
			} catch (Exception e) {
				Logger.info("***** Got Exception:: " + e.getMessage());

				data.setStatus(404);
				data.setMsg("Got Exception::" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			data = new DataContainer();
			Logger.info("***** Api Key Does Not Exist. *****");
			data.setStatus(404);
			data.setMsg("Api Key Does Not Exist. Please Enter Correct Key");
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public String deleteAll(@RequestParam("apiKeys") String apiKeys,
			@RequestParam(value = "deleteReason", required = false) String deleteReason) {
		DataContainer data = new DataContainer();

		Logger.info("***** Request For deleteAll Mapping With Reason ***" + deleteReason);
		if (deleteReason == null || deleteReason.isEmpty() || deleteReason.isEmpty()) {
			data = new DataContainer();
			Logger.info("***** Enter Delete Reason *****");
			data.setStatus(404);
			data.setMsg("Enter Delete Reason");
			return new Gson().toJson(data).toString();
		}
		if (apiKeys.equals(apiKey)) {
			try {
				userIpMappingRepository.deleteAll();
				data.setMsg("Successfully Deleted.");
				data.setStatus(200);
			} catch (Exception e) {
				data.setStatus(404);
				data.setMsg("Got Exception::" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			data = new DataContainer();
			Logger.info("***** Api Key Does Not Exist. *****");
			data.setStatus(404);
			data.setMsg("Api Key Does Not Exist. Please Enter Correct Key");
		}
		return new Gson().toJson(data).toString();
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public String findAll(@RequestParam("apiKeys") String apiKeys) {
		Logger.info("***** Request For findAll Mapping ***");

		DataContainer data = new DataContainer();
		List<UserIpMapping> userIpList = null;
		if (apiKeys.equals(apiKey)) {
			userIpList = userIpMappingRepository.findAll();

			try {
				if (userIpList.size() > 0) {
					Logger.info("***** Success For findAll Mapping ***" + userIpList.size());
					data.setMsg("Success");
					data.setStatus(200);
					data.setData(userIpList);
				} else {
					Logger.info("***** Data Not Found For findAll Mapping***" + userIpList.size());
					data.setMsg("Data Not Found");
					data.setStatus(404);
				}
			} catch (Exception e) {
				Logger.info("***** Got Exception:: ***" + e.getMessage());
				data.setStatus(404);
				data.setMsg("Got Exception::" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			data = new DataContainer();
			Logger.info("***** Api Key Does Not Exist. *****");
			data.setStatus(404);
			data.setMsg("Api Key Does Not Exist. Please Enter Correct Key");
		}
		return new Gson().toJson(data).toString();
	}

	public static String convertDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

}
