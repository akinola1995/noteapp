package com.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.spring.model.Category;
import com.spring.model.Note;
import com.spring.model.Reminder;
import com.spring.model.User;


@Configuration
@ComponentScan("com.spring.*")
@EnableWebMvc
@EnableTransactionManagement


public class ApplicationContextConfig {


	
	@Bean
	@Autowired
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Datadb");
        dataSource.setUsername("root");
        dataSource.setPassword("Grandma500!");
		return dataSource;
	}
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
		sessionFactoryBean.setAnnotatedClasses(Category.class,Note.class,Reminder.class,User.class);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);		
		return sessionFactoryBean;
		
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager hibernatetransactionmanager = new HibernateTransactionManager();
		hibernatetransactionmanager.setSessionFactory(sessionFactory);
		return hibernatetransactionmanager;
		
	}
}


