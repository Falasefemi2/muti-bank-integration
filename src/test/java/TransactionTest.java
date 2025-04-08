
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.transaction.Transaction;
import com.example.transaction.TransactionService;

public class TransactionTest {
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    public void testGetTransactionHistory() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(100, "Food", "WITHDRAW"));
        transactions.add(new Transaction(200, "Bills", "WITHDRAW"));
        transactions.add(new Transaction(50, "Coffee", "WITHDRAW"));

        String history = transactionService.getTransactionHistory(transactions);
        System.out.println(history);
    }
}
