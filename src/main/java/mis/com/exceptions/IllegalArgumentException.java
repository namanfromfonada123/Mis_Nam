/*
 * @author Advatix
 * 
 * @since 2019
 * 
 * @version 1.0
 */
package mis.com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * The Class IllegalArgumentException.
 */
public class IllegalArgumentException extends BaseException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8101022040067726610L;

  /** The Constant DEFAULT_HTTP_STATUS. */
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

  /** The Constant DEFAULT_MESSAGE. */
  public static final String DEFAULT_MESSAGE = "Illegal argument passed.";

  /** The http status. */
  private HttpStatus httpStatus;

  /** The message. */
  private String message;

  public Object data = null;

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

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  /**
   * Instantiates a new car exception.
   *
   * @param httpStatus the http status
   * @param message the message
   */
  public IllegalArgumentException(HttpStatus httpStatus, String message) {
    super();
    this.httpStatus = httpStatus == null ? DEFAULT_HTTP_STATUS : httpStatus;
    this.message = StringUtils.isEmpty(message) ? DEFAULT_MESSAGE : message;
  }

  public IllegalArgumentException(HttpStatus httpStatus, String message, Object data) {
    super();
    this.httpStatus = httpStatus == null ? DEFAULT_HTTP_STATUS : httpStatus;
    this.message = StringUtils.isEmpty(message) ? DEFAULT_MESSAGE : message;
    this.data = data;
  }

  /**
   * Instantiates a new car exception.
   *
   * @param httpStatus the http status
   * @param defaultStatus the default status
   * @param message the message
   * @param defaultMessage the default message
   */
  public IllegalArgumentException(HttpStatus httpStatus, HttpStatus defaultStatus, String message,
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
  public IllegalArgumentException(String message) {
    this(DEFAULT_HTTP_STATUS, message);
  }

  public IllegalArgumentException(String message, Object data) {
    this(DEFAULT_HTTP_STATUS, message, data);
  }

  /**
   * Instantiates a new car exception.
   */
  public IllegalArgumentException() {
    this(DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
  }

}
