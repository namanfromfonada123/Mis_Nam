/**
 * @author Rahul
 */
package mis.com.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * The Class EmailValidator.
 *
 * @author Ankush Goyal
 * 
 *         Validates following sceniro
 * 
 *         must contains “@” symbol 2. mkyong@.com.my – tld can not start with dot “.” 3.
 *         mkyong123@gmail.a – “.a” is not a valid tld, last tld must contains at least two
 *         characters 4. mkyong123@.com – tld can not start with dot “.” 5. mkyong123@.com.com – tld
 *         can not start with dot “.” 6. .mkyong@mkyong.com – email’s first character can not start
 *         with dot “.” 7. mkyong()*@gmail.com – email’s is only allow character, digit, underscore
 *         and dash 8. mkyong@%*.com – email’s tld is only allow character and digit 9.
 *         mkyong..2002@gmail.com – double dots “.” are not allow 10. mkyong.@gmail.com – email’s
 *         last character can not end with dot “.” 11. mkyong@mkyong@gmail.com – double “@” is not
 *         allow 12. mkyong@gmail.com.1a -email’s tld which has two characters can not contains
 *         digit
 */
public class EmailValidator {

  /** The Constant EMAIL_PATTERN. */
  public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  /**
   * Instantiates a new email validator.
   */
  public EmailValidator() {

  }

  /**
   * Validate.
   *
   * @param email the email
   * @return true, if successful
   */
  public static boolean validate(String email) {

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    if (StringUtils.isEmpty(email)) {
      return false;
    }
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

}
