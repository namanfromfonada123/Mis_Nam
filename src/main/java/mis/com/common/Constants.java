package mis.com.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mis.com.entity.EmailEntity;

public interface Constants {

	// public static final String PROPERTIES_FILE_PATH = "";

	/**
	 * Request Status Codes
	 */
	public static final int REQUEST_SUCCESS = 200;
    public static HashMap<String, EmailEntity> emailEntity = new HashMap<String, EmailEntity>();

	public static final int RECORD_EXISTS = 201;

	public static final int RECORD_NOT_EXISTS = 202;

	public static final int REQUEST_FAILED = 400;
	public static final int NOT_FOUND = 404;

	public static final int INVALID_REQUEST = 401;

	public static final int USER_DISABLED = 402;

	public static final int USER_SESSION_OUT = 403;

	public static final int USER_REGISTERED_ALREADY = 405;

	public static final int BLOCKED_USER = 406;

	public static final int INVALID_OLD_PASSWORD = 407;

	public static final int MAX_LIMIT_EXCEEDED = 408;

	public static final int SQL_FAILED = 409;

	public static final int DELETION_FAILED = 410;

	public static final int INVALID_TOEKN_ID = 411;

	public static final int MISSING_MANDATORY_PARAMS = 412;

	public static final int CONTAINER_SCAN_FAILED = 413;

	public static final int MAIL_NOT_SENT_CODE = 414;

	public static final int TOKEN_TIME_OUT_CODE = 415;

	public static final int EMAIL_ID_NOT_FOUND_CODE = 416;

	/**
	 * Response description String
	 */
	public static final String MIN_COUNT = "<MIN_COUNT>";
	public static final String MAX_COUNT = "<MAX_COUNT";
	public static final String USER_MAX_LOGIN_ATTEMPT = "user_max_login_attempt";

	public static final String REQUEST_PROCESSED = "Request Processed.";

	public static final String REQUEST_PROCESSING_FAILED = "Request Failed : ";

	public static final String RECORD_EXISTS_STRING = "Requested details already available.";

	public static final String RECORD_NOT_EXISTS_STRING = "Requested detail/record not available.";

	public static final String USER_LOGIN_SUCCESS = "Login successful.";
	public static final String USER_SUCCESS = "User Added Successfully.";

	public static final String INVALID_USER = "Invalid Username or Password.";

	public static final String INVALID_USERNAME = "Invalid Username.";

	public static final String WRONG_PASSWORD = "You have attempted " + MIN_COUNT
			+ " time with wrong password.Your max count is " + MAX_COUNT + ".";

	public static final String BLOCKED_USER_STRING = "User temporary blocked. Max login attempt failed.";

	public static final String USER_NOT_REGISTERED = "User is not registered with us.";

	public static final String USER_DISABLED_MESSAGE = "User disabled.";

	public static final String INVALID_REQUEST_STRING = "Invalid Request.";

	public static final String INVALID_PASSWORD = "Invalid  password.";

	public static final String GENERIC_ERROR = "There is some error, please try after some time.";

	public static final String REQUEST_MAX_MESSENGERS_STRING = "Max messengers added.";

	public static final String PACK_NOT_FOUND_STRING = "Pack not found";

	public static final String UPDATE_SUBSCRIPTION_FAILED = "update subscription failed";

	public static final String UPDATE_USAGE_FAILED = "update usage failed";

	public static final String INVALID_MSG_RECEIVER = "Invalid message receiver";

	public static final String INVALID_MAIL_TO = "Invalid mail receiver address";

	public static final String INVALID_SENDER = "Invalid sender address";

	public static final String MAX_LIMIT_EXCEED = "Max limit exceeded";

	public static final String INVALID_TOKEN_STRING = "User token not found";

	public static final String RECORD_ALREADY_EXISTS_STRING = "Record already exist.";

	public static final String SQL_CONSTRAINT_VIOLATED = "Unique or Null constraint violated";

	public static final String INVALID_EMAIL = "Invalid Email address.";
	public static final String INVALID_MOBILE = "Invalid Mobile/Phone number.";
	public static final String RECORD_FOUND = "Record found.";
	public static final String MAIL_NOT_SENT_STRING = "Email does not sent.";
	public static final String EMAIL_ID_NOT_FOUND_STRING = "Email id not found.";

	public static final String RECORDS_UPDATED = "Records Updated Successfully.";

	public static final String TEMPLATE_DETAILS_MISSING = "Template details required";

	public static final String CONTAINER_SCAN_FAILED_SRING = "Unable to scan";

	public static final String CONTAINER_NOT_OPEN = "Container is not open";

	public static final String TOKEN_TIME_OUT_STRING = "Token time expired";

	/**
	 * Database Related
	 */
	public static final String ASCENDING_ORDER = "asc";

	public static final String DESCENDING_ORDER = "desc";

	public static final String defaultDateFormat = "yyyy-MM-dd";
	public static final String defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String defaultTimezone = "Asia/Kolkata";
	public static final long accessTokenExpiryTimeInMs = 1800000;
	public static final long otpExpiryTimeInMs = 300000;

	public static final String SUCCESS_MSG = "Success";
	public static final String SUCCESSADD_MSG = "Added Successfully";
	public static final String FAIL_MSG = "Failed";
	public static final String DEFAULT_ERROR = "Error";
	public static final String USER_NOT_ACTIVE = "Account Is Not Active.";
	public static final String USER_NOT_AVAILABLE = "Invalid User Name";
	public static final String USER_DOSE_NOT_AVAILABLE = "User Dose Not Exists.";
	public static final String DELETE_MSG = "Records Deleted Successfully.";
	public static final String PASSWORD_VALIDATION = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,}";
	public static final String PASSWORD_MSG = "Password Not Valid(min 8 and max 15)for Ex: Aa123@ascg";
	public static final String MOBILE_MSG = "Mobile Number Not Valid For Ex:: 1234567890";
	public static final String EMAIL_MSG = "Email Should Be Contains Capital Letter and Small Letter ,Special Character and Numeric Number.";

	public static final String FILE_NOT_UPLOAD = "Could not upload the file";
	public static final String FILE_UPLOAD_SUCCESSFULLY = "Uploaded the file successfully";
	public static final String FILE_CANNOT_EMPTY = "File Cannot Empty.";

	public static final String INVALID_FORMAT = "Invalid Format";

	public static final Integer ZERO_INT = 0;

	public static final List<String> MIS_MAPPING_SCHEMA = Arrays.asList("EXECUTION_DT", "USER_NAME", "CAMPAIGN_NAME",
			"LEAD_NAME", "TOTAL_MSISDN", "VALID_MSISDN", "NO_OF_RETRY", "ATTEMPTED_CALLS", "CONNECTED_CALLS",
			"DIGIT_PRESSED", "LISTEN_RATE", "TOTAL_BILL_SEC", "CREDIT_USED");
	public static final Integer MIS_MAPPING_SCHEMA_LENGTH = MIS_MAPPING_SCHEMA.size();

	public static final List<String> USER_IP_MAPPING_SCHEMA = Arrays.asList("USERNAME", "CAMPAIGNNAME", "IPADDRESS",
			"USER2", "ACCOUNTNAME");
	public static final Integer USER_IP_MAPPING_SCHEMA_LENGTH = USER_IP_MAPPING_SCHEMA.size();

	public static final String REQUIRED_COLUMN_MSG = "Given File Does Not Exist Required Column";

	public static final int MOBILE_LENGTH = 10;

	public static final String UNAUTHORIZED = "Unauthorized";

	public static final String DATANOTSAVED = "Data Not Saved.";

}
