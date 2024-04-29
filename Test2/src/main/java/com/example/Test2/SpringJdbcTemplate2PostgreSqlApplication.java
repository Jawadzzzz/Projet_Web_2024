package com.example.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SpringJdbcTemplate2PostgreSqlApplication implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcTemplate2PostgreSqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String sql = "DELETE FROM student WHERE 1 = 1; INSERT INTO student (id, name, email) VALUES (1, 'Nam Ha Minh', 'nam@codejava.net')";

        sql += "; INSERT INTO student (id, name, email) VALUES (2, 'Carmen', 'carmen.diaz@escuela.es')";
        sql += "; DELETE FROM student WHERE 1=1;";
        //int rows = jdbcTemplate.update(sql);
        //if (rows > 0) {
        //    System.out.println("A new row has been inserted.");
        // }
    }
}
