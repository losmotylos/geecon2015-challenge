package pl.allegro.promo.geecon2015.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStatisticsRepository;
import pl.allegro.promo.geecon2015.domain.transaction.TransactionRepository;
import pl.allegro.promo.geecon2015.domain.user.UserRepository;

@Component
public class ReportGenerator {
    
    private final FinancialStatisticsRepository financialStatisticsRepository;
    
    private final UserRepository userRepository;
    
    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportGenerator(FinancialStatisticsRepository financialStatisticsRepository,
                           UserRepository userRepository,
                           TransactionRepository transactionRepository) {
        this.financialStatisticsRepository = financialStatisticsRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Report generate(ReportRequest request) {
        Report report = new Report();
        return report;
    }
    
}
