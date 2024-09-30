package mis.com.Naman.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mis.com.Naman.Modal.username_client_mapping;
import mis.com.Naman.Service.ConvertUserClientMappingfromFile;
import mis.com.Naman.pojos.userClientMappingPojo;
import mis.com.Naman.repository.userClientRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/Mis/Report")
public class UserClientMapping {

	Logger logger = LoggerFactory.getLogger(UserClientMapping.class);

	@Autowired
	userClientRepository userClientRepository;

	@Autowired
	ConvertUserClientMappingfromFile clientMappingfromFile;

	@PostMapping("/setUserClientMapping")
	public ResponseEntity<?> SetUserClientMapping(@RequestBody userClientMappingPojo userclientMappingRequest) {

		try {

			if (userclientMappingRequest.getClient_name()==""||userclientMappingRequest.getPanel_name()==""||userclientMappingRequest.getUsername()=="") {
				return ResponseEntity.badRequest().body("Check the Body!!");
			}
			logger.info("Setting user Client Mapping !!");
//			username_client_mapping userClient_mapping = new username_client_mapping();
//			userClient_mapping.setClient_name(userclientMappingRequest.getClient_name());
//			userClient_mapping.setPanel_name(userclientMappingRequest.getPanel_name());
//			userClient_mapping.setUsername(userclientMappingRequest.getUsername());
//			return ResponseEntity.ok(userClientRepository.save(userClient_mapping)) ;
			
			userClientRepository.insertdataTouserClientMappingIgnorecase(userclientMappingRequest.getClient_name(), userclientMappingRequest.getPanel_name(), userclientMappingRequest.getUsername());
			
			return ResponseEntity.ok("UserMapping inserted Successfully !!");
			

		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}

	}

	@GetMapping("/getallClientMapping")
	public List<username_client_mapping> getUserClientMappingList() {

		return userClientRepository.findAll();
	}

	

}
