package net.gplatform.spring.social.base;

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
		String localUserId = UUID.randomUUID().toString();
		String userName = userProfile.getUsername();
		if (StringUtils.isNotEmpty(userName)) {
			localUserId = userName;
		}
		String name = userProfile.getName();
		if (StringUtils.isNotEmpty(name)) {
			localUserId = name;
		}
		String email = userProfile.getEmail();
		if (StringUtils.isNotEmpty(email)) {
			localUserId = email;
		}
		logger.debug("Local User ID is: {}", localUserId);
		return localUserId;
	}
}
