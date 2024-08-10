package mis.com.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Class EntityAlreadyExistsException.
 */
public class CustomAppException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3077468772228075912L;

	/** The Constant DEFAULT_MESSAGE. */
	private static final String DEFAULT_MESSAGE = "Entity already exists !";

	/** The Constant DEFAULT_HTTP_STATUS. */
	private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.OK;

	/**
	 * Instantiates a new entity already exists exception.
	 */
	public CustomAppException() {
		this(DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
	}

	/**
	 * Instantiates a new entity already exists exception.
	 *
	 * @param httpStatus the http status
	 * @param errorMessage the error message
	 */
	public CustomAppException(HttpStatus httpStatus,
			String errorMessage) {
		super(httpStatus, DEFAULT_HTTP_STATUS, errorMessage, DEFAULT_MESSAGE);
	}

	public CustomAppException(String errorMessage) {
		super(DEFAULT_HTTP_STATUS, errorMessage);
	}
}
