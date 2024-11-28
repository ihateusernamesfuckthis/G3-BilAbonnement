package com.example.g3bilabonnement.Service;
import com.example.g3bilabonnement.Repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {
    @Autowired
    DamageReportRepo damageReportRepo;
}