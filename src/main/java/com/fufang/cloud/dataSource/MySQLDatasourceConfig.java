package com.fufang.cloud.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.fufang.cloud.mapper.mysql", sqlSessionFactoryRef = MySQLDatasourceConfig.SQL_SESSION_FACTORY_NAME)
public class MySQLDatasourceConfig {
    
    public static final String SQL_SESSION_FACTORY_NAME = "mysqlSessionFactory";

    @Bean(destroyMethod="")
    public DataSource mysqlDatasource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/dataSource_mysql");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }
    @Bean(name = MySQLDatasourceConfig.SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(mysqlDatasource());
        return sqlSessionFactoryBean.getObject();
    }
}