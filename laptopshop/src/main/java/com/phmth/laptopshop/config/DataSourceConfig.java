//package com.phmth.laptopshop.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.phmth.laptopshop.utils.AES;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//public class DataSourceConfig  {
//	
//	@Value("${db.datasource.driver}")
//	private String driver;
//	 
//	@Value("${db.datasource.url}")
//    private String url;
// 
//    @Value("${db.datasource.username}")
//    private String username;
// 
//    @Value("${db.datasource.password}")
//    private String password;
// 
//    @Bean(name = "dataSource")
//    public DataSource dataSource() {
//        HikariConfig dataSource = new HikariConfig();
//        dataSource.setDriverClassName(AES.decrypt(driver, "driver"));
//        dataSource.setJdbcUrl(AES.decrypt(url, "url"));
//        dataSource.setUsername(AES.decrypt(username, "username"));
//        dataSource.setPassword(AES.decrypt(password, "password"));
//        return new HikariDataSource(dataSource);
//    }
//    
//	
//}
