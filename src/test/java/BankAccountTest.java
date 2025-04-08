import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.bank.BankAccount;

public class BankAccountTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount(1000, "Primary account");
        bankAccount.setProvider("GTBank");
    }

    @Test
    public void testDeposit() throws Exception {
        bankAccount.deposit(500, "Salary deposit");
        assertEquals(1475, bankAccount.getBalance());
    }

    @Test
    public void testWithdraw() throws Exception {
        bankAccount.withdraw(200, "Buy groceries");
        assertEquals(800, bankAccount.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bankAccount.withdraw(2000, "Overdraw");
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    public void testTransferBetweenAccounts() throws Exception {
        BankAccount secondaryAccount = new BankAccount(500, "Secondary Account");
        secondaryAccount.setProvider("Warpspeed");
        bankAccount.linkAccount(secondaryAccount);

        bankAccount.transferBetweenAccounts("Warpspeed", 300);

        assertEquals(700, bankAccount.getBalance(), "Primary account should have 700 after transfer");
        assertEquals(785, secondaryAccount.getBalance(), "Secondary account should have 785 after transfer");

    }
}
