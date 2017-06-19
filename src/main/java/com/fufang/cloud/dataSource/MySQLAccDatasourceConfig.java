package com.fufang.cloud.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.fufang.cloud.mapper.mysql_acc", sqlSessionFactoryRef = MySQLAccDatasourceConfig.SQL_SESSION_FACTORY_NAME)
public class MySQLAccDatasourceConfig {
    public static final String SQL_SESSION_FACTORY_NAME = "mysqlAccSessionFactory";

    @Bean(name = "mysqlAccDatasource")
    @Primary
    public DataSource stagingDatasource() throws SQLException {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource("dataSource_mysql_acc");
    }

    @Bean(name = MySQLAccDatasourceConfig.SQL_SESSION_FACTORY_NAME)
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(stagingDatasource());
        return sqlSessionFactoryBean.getObject();
    }
}