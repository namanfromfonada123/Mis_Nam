/**
 * @author Rahul
 */
package mis.com.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Rahul
 *
 */
public class MobileNoValidator {

	/** The Constant MOBILE_NO_PATTERN. */
	public static final String MOBILE_NO_PATTERN = "regexp=\"([6-9]{1}[0-9]{2}[0-9]{3}[0-9]{4})\")";

	/**
	 * Instantiates a new mobile validator.
	 */
	public MobileNoValidator() {

	}

	/**
	 * Validate.
	 *
	 * @param mobileNo the mobileNo
	 * @return true, if successful
	 */
	public static boolean mobileNoValidate(String mobileNo) {

		Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		if (StringUtils.isEmpty(mobileNo)) {
			return false;
		}
		Matcher matcher = pattern.matcher(mobileNo);
		return matcher.matches();
	}
}
