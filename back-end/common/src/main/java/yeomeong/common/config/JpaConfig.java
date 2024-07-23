package yeomeong.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "yeomeong.common.entity.jpa",
                "yeomeong.common.repository.jpa"
        }
)
public class JpaConfig {
        @Primary
        @Bean(name = "mysqlDataSource")
        @ConfigurationProperties(prefix = "spring.datasource")
        public DataSource mysqlDataSource() {
                return new DriverManagerDataSource();
        }
}