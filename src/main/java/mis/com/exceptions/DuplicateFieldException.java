
package mis.com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * The Class DuplicateFieldException.
 */
public class DuplicateFieldException extends FileUploadException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -243909236486791164L;

  /** The Constant DEFAULT_MESSAGE. */
  private static String DEFAULT_MESSAGE = "File Cannot Uploaded due to DuplicateFields ";

  /** The Constant DEFAULT_HTTP_STATUS. */
  private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

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
  public DuplicateFieldException(HttpStatus httpStatus, String message) {
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
  public DuplicateFieldException(HttpStatus httpStatus, HttpStatus defaultStatus, String message,
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
  public DuplicateFieldException(String message) {
    this(DEFAULT_HTTP_STATUS, message);
  }

  /**
   * Instantiates a new car exception.
   */
  public DuplicateFieldException() {
    this(DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
  }

}
