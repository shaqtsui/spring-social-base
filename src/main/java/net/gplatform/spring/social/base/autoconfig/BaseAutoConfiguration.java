package net.gplatform.spring.social.base.autoconfig;

import javax.sql.DataSource;

import net.gplatform.spring.social.base.JdbcUsersConnectionRepositoryTableCreator;
import net.gplatform.spring.social.base.SimpleConnectionSignUp;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

@Configuration
@ConditionalOnClass(value = { SocialConfigurerAdapter.class })
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class BaseAutoConfiguration {

	@Configuration
	@EnableSocial
	@ConditionalOnWebApplication
	@ConditionalOnClass(value = { SocialConfigurerAdapter.class })
	protected static class BaseAutoConfigurationAdapter extends SocialConfigurerAdapter {
		private static final Logger LOG = LoggerFactory.getLogger(BaseAutoConfigurationAdapter.class);

		@Autowired(required = false)
		DataSource dataSource;

		@Autowired
		JdbcUsersConnectionRepositoryTableCreator jdbcUsersConnectionRepositoryTableCreator;

		@Autowired
		ConnectionSignUp connectionSignUp;

		@Override
		public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
			UsersConnectionRepository usersConnectionRepository = null;
			if (dataSource == null) {
				usersConnectionRepository = super.getUsersConnectionRepository(connectionFactoryLocator);
			} else {
				jdbcUsersConnectionRepositoryTableCreator.createTableIfNotExist();
				TextEncryptor textEncryptor = Encryptors.noOpText();
				usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
			}
			try {
				BeanUtils.setProperty(usersConnectionRepository, "connectionSignUp", connectionSignUp);
			} catch (Exception e) {
				LOG.error("Error config ConnectionSignUp", e);
			}
			return usersConnectionRepository;
		}

		@Bean
		public JdbcUsersConnectionRepositoryTableCreator jdbcUsersConnectionRepositoryTableCreator() {
			return new JdbcUsersConnectionRepositoryTableCreator();
		}

		@Bean
		@ConditionalOnMissingBean(ConnectionSignUp.class)
		public ConnectionSignUp connectionSignUp() {
			return new SimpleConnectionSignUp();
		}

	}
}
