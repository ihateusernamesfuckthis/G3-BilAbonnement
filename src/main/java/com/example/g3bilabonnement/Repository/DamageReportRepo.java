package com.example.g3bilabonnement.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DamageReportRepo {
    @Autowired
    JdbcTemplate template;

}