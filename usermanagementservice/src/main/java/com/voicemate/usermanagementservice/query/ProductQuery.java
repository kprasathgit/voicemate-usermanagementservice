package com.voicemate.usermanagementservice.query;

import org.springframework.stereotype.Component;

@Component
public class ProductQuery {

	public String findByProductName() {

		StringBuilder sb = new StringBuilder("");
		sb.append("select * from product as p where productname =:productname");
		return sb.toString();
	}
}
