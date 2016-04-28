package org.order.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Resource
	private Environment env;

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "hibernate.connection.driver_class";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "hibernate.connection.username";
	private static final String PROPERTY_NAME_DATABASE_URL = "hibernate.connection.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "hibernate.connection.password";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_PACKAGES_SCAN = "hibernate.package.scan";
	private static final String PROPERTY_NAME_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Bean
	public EntityManagerFactory entityManagerFactory() throws ClassNotFoundException, IOException {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPersistenceProvider(new HibernatePersistenceProvider());
		factory.setDataSource(dataSource());
		factory.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_PACKAGES_SCAN));
		factory.setJpaProperties(hibernateProperties());

		factory.afterPropertiesSet();
		return factory.getObject();
	}

	/*@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_PACKAGES_SCAN));
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}*/

	@Bean
	public JdbcTemplate jdbcTemplate() throws ClassNotFoundException, IOException {
		DataSource dataSource = dataSource();
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
			throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	/*@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}*/

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HBM2DDL_AUTO));
		properties.put("hibernate.hbm2ddl.import_files", env.getRequiredProperty("hibernate.hbm2ddl.import_files"));
		return properties;
	}

}
