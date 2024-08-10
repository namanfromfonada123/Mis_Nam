package mis.com.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The Class RestUtils.
 */
public class RestUtils {

	/**
	 * Success response.
	 *
	 * @param <T>        the generic type
	 * @param data       the data
	 * @param statusCode the status code
	 * @return the response entity
	 */
	public static <T> ResponseEntity<RestResponse<T>> successResponse(T data, String message, HttpStatus statusCode) {

		return new ResponseEntity<>(new RestResponse<T>(data, message), statusCode);
	}

	/**
	 * Success response.
	 *
	 * @param <T>  the generic type
	 * @param data the data
	 * @return the response entity
	 */
	public static <T> ResponseEntity<RestResponse<T>> successResponse(T data) {
		return successResponse(data, null, HttpStatus.OK);
	}

	public static <T> ResponseEntity<RestResponse<T>> successResponse(T data, String message) {
		return successResponse(data, message, HttpStatus.OK);
	}

	public static <T> ResponseEntity<T> successResponseNew(T data) {

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	/**
	 * Error response.
	 *
	 * @param <T>          the generic type
	 * @param errorMessage the error message
	 * @param statusCode   the status code
	 * @return the response entity
	 */
	public static <T> ResponseEntity<RestResponse<?>> errorResponse(String errorMessage, HttpStatus statusCode) {
		return new ResponseEntity<>(new RestResponse<T>(errorMessage, null, Boolean.FALSE, statusCode.value()),
				statusCode);
	}

	/**
	 * Error response.
	 *
	 * @param <T>              the generic type
	 * @param errorMessage     the error message
	 * @param errorDescription the error description
	 * @param statusCode       the status code
	 * @return the response entity
	 */
	public static <T> ResponseEntity<RestResponse<?>> errorResponse(String errorMessage, T errorDescription,
			HttpStatus statusCode) {
		return new ResponseEntity<>(
				new RestResponse<T>(errorMessage, errorDescription, Boolean.FALSE, statusCode.value()), statusCode);
	}
}
