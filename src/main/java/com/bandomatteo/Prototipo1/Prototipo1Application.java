package com.bandomatteo.Prototipo1;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class Prototipo1Application {

    public static void main(String[] args) {
		SpringApplication.run(Prototipo1Application.class, args);
	}

}
