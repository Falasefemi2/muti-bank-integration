import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.ai.AiService;
import com.example.transaction.Transaction;

public class AIServiceTest {
    private AiService aiService;

    @BeforeEach
    public void setUp() {
        aiService = new AiService();
        System.out.println("setUp() called, aiService initialized: " + (aiService != null));
    }

    // Test fraud detection
    @Test
    public void testFraudDection() {
        System.out.println("Running testFraudDection, aiService is null? " + (aiService == null));
        assertTrue(aiService.fraudDetection(1000, 150), "Transaction should be flagged as fraud");
        assertFalse(aiService.fraudDetection(500, 150), "Transaction should not be flagged as fraud");
    }

    @Test
    public void testSmartBudgeting() {
        System.out.println("Running testSmartBudgeting, aiService is null? " + (aiService == null));
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(100, "Food", "WITHDRAW"));
        transactions.add(new Transaction(200, "Bills", "WITHDRAW"));
        transactions.add(new Transaction(50, "Coffee", "WITHDRAW"));

        String result = aiService.smartBudgeting(transactions);

        assertEquals("You made 3 withdraws this period", result, "Budgeting analysis should count 3 withdrawals");
    }
}