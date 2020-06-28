package ControllerMowers.config;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ControllerMowers.domain")
public class DatabaseConfig
{
    @Bean
    EmbeddedDatabase appDataSource()
    {
        // create a unique database name to avoid conflicts during tests
        final String dbName = String.join(";", UUID.randomUUID().toString());

        final EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder()
            .setName(dbName)
            .setType(H2)
            .addScript("classpath:/data.sql");

        return embeddedDatabaseBuilder.build();
    }

    @Bean(name = "transactionManager")
    PlatformTransactionManager applicationTransactionManager(final DataSource appDataSource)
    {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(appDataSource);
        return transactionManager;
    }

}
