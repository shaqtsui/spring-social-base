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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class SimpleConnectionSignUp implements ConnectionSignUp {
	final Logger logger = LoggerFactory.getLogger(SimpleConnectionSignUp.class);

	/**
	 * here use random generated id to act as localUserId, localUserId will be the key to save connection & sign
	 */
	@Override
	public String execute(Connection<?> connection) {
		String localUserId = UUID.randomUUID().toString();
		logger.debug("Local User ID is: {}", localUserId);
		return localUserId;
	}
}
