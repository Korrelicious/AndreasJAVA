package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

public class ShowTransactionsCommand extends Command {

    public ShowTransactionsCommand(BankAccount account) {
        super(account);

    }

    @Override
    public void execute() {

        account.showAllTransactions();

    }

    @Override
    public String getDescription() {
        return "Visa alla transaktioner";
    }
}
