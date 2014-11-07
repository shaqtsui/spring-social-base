package net.gplatform.spring.social.base;

/*
 * #%L
 * spring-social-base
 * %%
 * Copyright (C) 2013 - 2014 Shark Xu
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

public class SimpleConnectionSignUp implements ConnectionSignUp {
	final Logger logger = LoggerFactory.getLogger(SimpleConnectionSignUp.class);

	@Override
	public String execute(Connection<?> connection) {
		UserProfile userProfile = connection.fetchUserProfile();
		String localUserId = null;
		if (StringUtils.isNotEmpty(userProfile.getUsername())) {
			localUserId = userProfile.getUsername();
		} else {
			localUserId = UUID.randomUUID().toString();
		}
		logger.debug("Local User ID is: {}", localUserId);
		return localUserId;
	}
}
