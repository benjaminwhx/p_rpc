package com.github.bean.pojo;

import java.io.Serializable;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-11
 * Time: 6:18 pm
 */
public enum EnumTypeObj implements Serializable {
	UNIVERSAL(0),
	WEB(1),
	IMAGES(2),
	LOCAL(3),
	NEWS(4),
	PRODUCTS(5),
	VIDEO(6);

	int value;

	EnumTypeObj(int value) {
		this.value = value;
	}
}
