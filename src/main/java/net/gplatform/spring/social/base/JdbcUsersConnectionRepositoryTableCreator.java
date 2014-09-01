package net.gplatform.spring.social.base;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
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
				rdp.populate(dataSource.getConnection());
			} catch (Exception e1) {
				LOG.error("Error create table UserConnection", e1);
			}
		}
	}
}
