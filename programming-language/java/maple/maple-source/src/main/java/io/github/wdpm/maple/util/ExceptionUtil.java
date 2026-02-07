package io.github.wdpm.maple.util;

import java.util.Arrays;

public class ExceptionUtil {

	public static void makeRunTimeWhen(boolean flag, String message, Object... args) {
		if (flag) {
			message = String.format(message, args);
			throw correctStackTrace(new RuntimeException(message));
		}
	}
	
	public static void makeRuntime(Throwable cause) {
		throw correctStackTrace(new RuntimeException(cause));
	}

	/**
	 * 移除 Lang层堆栈信息
	 * @param e
	 * @return
	 */
	private static RuntimeException correctStackTrace(RuntimeException e) {
		StackTraceElement[] s = e.getStackTrace();
		if(null != s && s.length > 0){
			// remove s[0]
			e.setStackTrace(Arrays.copyOfRange(s, 1, s.length));
		}
		return e;
	}
	
}
