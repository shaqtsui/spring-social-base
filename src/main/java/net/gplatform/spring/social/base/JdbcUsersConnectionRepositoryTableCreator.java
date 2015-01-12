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

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class JdbcUsersConnectionRepositoryTableCreator {
	private static final Logger LOG = LoggerFactory.getLogger(JdbcUsersConnectionRepositoryTableCreator.class);

	@Autowired
	DataSource dataSource;

	public void createTableIfNotExist() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			jdbcTemplate.queryForList("select count(*) from UserConnection");
		} catch (Exception e) {
			LOG.debug("Create table UserConnection");
			try {
				ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
				rdp.addScript(new ClassPathResource("/org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql"));
				DatabasePopulatorUtils.execute(rdp, dataSource);
			} catch (Exception e1) {
				LOG.error("Error create table UserConnection", e1);
			}
		}
	}
}
