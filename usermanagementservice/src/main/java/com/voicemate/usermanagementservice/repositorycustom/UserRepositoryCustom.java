package com.voicemate.usermanagementservice.repositorycustom;

import com.voicemate.usermanagementservice.common.Result;

public interface UserRepositoryCustom {
	public Result findByEmail(String email) throws Exception;
}
