/**
 * @author Rahul
 */
package mis.com.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * Minimum eight characters, at least one letter and one number:
 * "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
 * 
 * Minimum eight characters, at least one letter, one number and one special
 * character: "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
 * 
 * Minimum eight characters, at least one uppercase letter, one lowercase letter
 * and one number: "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
 * 
 * Minimum eight characters, at least one uppercase letter, one lowercase
 * letter, one number and one special character:
 * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"
 * 
 * Minimum eight and maximum 10 characters, at least one uppercase letter, one
 * lowercase letter, one number and one special character:
 * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$"
 */
public class PasswordValidator {

	/** The Constant PASSWORD_PATTERN. */
//  Minimum 8 and maximum 15 characters, at least one uppercase letter, one lowercase letter and one special character:
	public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,15}$";

	/**
	 * Validate.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	public static boolean validate(String password) {

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		if (StringUtils.isEmpty(password)) {
			return false;
		}
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
