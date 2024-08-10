
package mis.com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * The Class EntityNotFoundException.
 */
public class EntityNotFoundException extends BaseException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8784883146060992893L;

  /** The Constant DEFAULT_HTTP_STATUS. */
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

  /** The Constant DEFAULT_MESSAGE. */
  public static final String DEFAULT_MESSAGE = "User Not Found.";

  /** The http status. */
  private HttpStatus httpStatus;

  /** The message. */
  private String message;

  /**
   * Gets the http status.
   *
   * @return the http status
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  /**
   * Sets the http status.
   *
   * @param httpStatus the new http status
   */
  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message.
   *
   * @param message the new message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Instantiates a new car exception.
   *
   * @param httpStatus the http status
   * @param message the message
   */
  public EntityNotFoundException(HttpStatus httpStatus, String message) {
    super();
    this.httpStatus = httpStatus == null ? DEFAULT_HTTP_STATUS : httpStatus;
    this.message = StringUtils.isEmpty(message) ? DEFAULT_MESSAGE : message;
  }

  /**
   * Instantiates a new car exception.
   *
   * @param httpStatus the http status
   * @param defaultStatus the default status
   * @param message the message
   * @param defaultMessage the default message
   */
  public EntityNotFoundException(HttpStatus httpStatus, HttpStatus defaultStatus, String message,
      String defaultMessage) {
    super();
    this.httpStatus = httpStatus == null ? defaultStatus : httpStatus;
    this.message = StringUtils.isEmpty(message) ? defaultMessage : message;
  }

  /**
   * Instantiates a new car exception.
   *
   * @param message the message
   */
  public EntityNotFoundException(String message) {
    this(DEFAULT_HTTP_STATUS, message);
  }

  /**
   * Instantiates a new car exception.
   */
  public EntityNotFoundException() {
    this(DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
  }

}
