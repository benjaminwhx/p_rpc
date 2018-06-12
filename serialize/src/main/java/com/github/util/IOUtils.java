package com.github.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 吴海旭
 * Date: 2018-05-15
 * Time: 下午7:54
 */
public class IOUtils {

	public static void closeIO(Closeable... closeables) {
		if (closeables == null) {
			return;
		}
		try {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		} catch (IOException e) {
		}
	}
}
