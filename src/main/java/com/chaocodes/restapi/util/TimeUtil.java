package com.chaocodes.restapi.util;

import java.sql.Timestamp;

public class TimeUtil
{
	private TimeUtil () {}

	public static Timestamp getTimestampNow() {
		return new Timestamp(new java.util.Date().getTime());
	}
}
