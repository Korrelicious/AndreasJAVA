package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

public class ShowBalanceCommand extends Command{

    public ShowBalanceCommand(BankAccount account) {
        super(account);

    }

    @Override
    public void execute() {
        System.out.println("Din balans är: " + account.getBalance());

    }

    @Override
    public String getDescription() {
        return "Visa balans";
    }
}
