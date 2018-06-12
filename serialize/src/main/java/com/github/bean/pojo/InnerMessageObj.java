package com.github.bean.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-11
 * Time: 6:18 pm
 */
@Data
@NoArgsConstructor
public class InnerMessageObj implements Serializable {
	private int id;
	private String name;
	private EnumTypeObj enumTypeObj;
}
