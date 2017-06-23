package com.fufang.cloud.dataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.fufang.cloud.mapper.mysql_acc", sqlSessionFactoryRef = MySQLAccDatasourceConfig.SQL_SESSION_FACTORY_NAME)
public class MySQLAccDatasourceConfig {
    public static final String SQL_SESSION_FACTORY_NAME = "mysqlAccSessionFactory";
    
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/dataSource_mysql_acc");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                resource.setProperty("url", "jdbc:mysql://localhost:3306/tvd");
                resource.setProperty("username", "root");
                resource.setProperty("password", "111111");
                context.getNamingResources().addResource(resource);
    
                ContextResource resource2 = new ContextResource();
                resource2.setName("jdbc/dataSource_mysql");
                resource2.setType(DataSource.class.getName());
                resource2.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                resource2.setProperty("url", "jdbc:mysql://172.16.88.112:8066/ASSET?useUnicode=true&amp;characterEncoding=UTF-8");
                resource2.setProperty("username", "root");
                resource2.setProperty("password", "root");
                context.getNamingResources().addResource(resource2);
            }
        };
    }
    @Bean(destroyMethod="")
    @Primary
    public DataSource mysqlAccDatasource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/dataSource_mysql_acc");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }
    
    @Bean(name = MySQLAccDatasourceConfig.SQL_SESSION_FACTORY_NAME)
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(mysqlAccDatasource());
        return sqlSessionFactoryBean.getObject();
    }
}