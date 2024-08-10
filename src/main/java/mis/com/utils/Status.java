/*
 * @author Rahul Kumar
 * @since 2022/06/20
 * @version 1.0
 */
package mis.com.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public enum Status {

	INACTIVE(0), ACTIVE(1);

	private Integer id;

	Status(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public static Status fromId(Integer id) {
		if (Objects.isNull(id))
			return null;
		for (Status queue : Status.values()) {
			if (queue.getId().equals(id)) {
				return queue;
			}
		}
		return null;
	}

	public static Status fromString(String status) {
		if (Boolean.TRUE.equals(StringUtils.isBlank(status)))
			return null;
		for (Status queue : Status.values()) {
			if (queue.name().equalsIgnoreCase(status)) {
				return queue;
			}
		}
		return null;
	}

}
