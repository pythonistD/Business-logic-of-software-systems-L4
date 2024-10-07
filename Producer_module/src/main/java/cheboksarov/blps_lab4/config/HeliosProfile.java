package cheboksarov.blps_lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("helios")
public class HeliosProfile {

    @Bean
    public DataSource dataSourceHelios() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/studs");
        dataSource.setUsername("s268819");
        dataSource.setPassword("0WZujn3KzBMeJF4k");
        return dataSource;
    }
}
