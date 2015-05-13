package pl.allegro.promo.geecon2015.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStatisticsRepository;
import pl.allegro.promo.geecon2015.domain.transaction.TransactionRepository;
import pl.allegro.promo.geecon2015.domain.transaction.UserTransaction;
import pl.allegro.promo.geecon2015.domain.user.UserRepository;

import java.math.BigDecimal;
import java.util.UUID;

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

        financialStatisticsRepository.listUsersWithMinimalIncome(request.getMinimalIncome(), request.getUsersToCheck())
                .getUserIds().stream()
                .map(x -> new ReportedUser(x, getUserName(x), getUserTransactions(x)))
                .forEach(report::add);

        return report;
    }

    private BigDecimal getUserTransactions(UUID userId) {
        try {
            return transactionRepository.transactionsOf(userId).getTransactions().stream()
                    .map(UserTransaction::getAmount)
                    .reduce(new BigDecimal(0), (x, y) -> x.add(y));
        } catch (Exception e) {
            return null;
        }
    }

    private String getUserName(UUID userId) {
        try {
            return userRepository.detailsOf(userId).getName();
        } catch (Exception e) {
            return "<failed>";
        }
    }

}

