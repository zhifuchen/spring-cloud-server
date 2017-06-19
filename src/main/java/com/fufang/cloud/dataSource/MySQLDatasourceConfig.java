package com.fufang.cloud.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.fufang.cloud.mapper.mysql", sqlSessionFactoryRef = MySQLDatasourceConfig.SQL_SESSION_FACTORY_NAME)
public class MySQLDatasourceConfig {

    public static final String SQL_SESSION_FACTORY_NAME = "mysqlSessionFactory";

    @Bean(name = "mysqlDatasource")
    public DataSource crdDatasource() throws SQLException {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource("dataSource_mysql");
    }

    @Bean(name = MySQLDatasourceConfig.SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(crdDatasource());
        return sqlSessionFactoryBean.getObject();
    }
}