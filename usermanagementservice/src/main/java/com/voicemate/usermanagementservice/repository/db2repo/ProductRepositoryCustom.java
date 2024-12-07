package com.voicemate.usermanagementservice.repository.db2repo;

import com.voicemate.usermanagementservice.common.Result;

public interface ProductRepositoryCustom {
	public Result findByProductName(String productname) throws Exception;
}
