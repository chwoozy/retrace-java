package com.stackify.sandbox.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.print.attribute.standard.JobKOctets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SQLService {

    private DriverManagerDataSource dataSource = new DriverManagerDataSource();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SQLService() {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://retrace_mysql:3306/northwind");
        dataSource.setUsername("retrace");
        dataSource.setPassword("BlackPanther@0405");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Map<String, Object> executeSP(String storeProcedure) {
        try {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName(storeProcedure);
            return simpleJdbcCall.execute();
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("Error accessing indicated stored procedure!");
            return Collections.emptyMap();
        }
    }

    public List<Map<String, Object>> callQuery(String query) {
        return jdbcTemplate.queryForList(query);
    }

    public void updateQuery(String query) {
        jdbcTemplate.execute(query);
    }
}
