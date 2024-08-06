package com.bandomatteo.Prototipo1;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class Prototipo1Application  implements CommandLineRunner {

	private final DataSource dataSource;

    public Prototipo1Application(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
		SpringApplication.run(Prototipo1Application.class, args);
	}

    @Override
    public void run( final String... args){
        log.info("Datasource: " + dataSource.toString());
        final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
        restTemplate.execute("select 1");

    }

}
