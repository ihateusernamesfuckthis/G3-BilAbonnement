package Service;
import Repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {
    @Autowired
    DamageReportRepo damageReportRepo;
}