
package mis.com.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Class BadRequestException.
 */
public class BadRequestException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7201815216491899773L;

	/** The Constant DEFAULT_MESSAGE. */
	private static final String DEFAULT_MESSAGE = "Bad Request !";

	/** The Constant DEFAULT_HTTP_STATUS. */
	private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

	/**
	 * Instantiates a new bad request exception.
	 */
	public BadRequestException() {
		this(DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
	}

	/**
	 * Instantiates a new bad request exception.
	 *
	 * @param httpStatus the http status
	 * @param message the message
	 */
	public BadRequestException(HttpStatus httpStatus, String message) {
		super(httpStatus, DEFAULT_HTTP_STATUS, message, DEFAULT_MESSAGE);

	}

	/**
	 * Instantiates a new bad request exception.
	 *
	 * @param message the message
	 */
	public BadRequestException(String message) {
		this(DEFAULT_HTTP_STATUS, message);
	}

}
