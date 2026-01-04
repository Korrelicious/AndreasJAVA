package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

import java.util.Scanner;

public class RemoveTransactionCommand extends Command {

    private final Scanner scanner;

    public RemoveTransactionCommand(BankAccount account,Scanner scanner) {
        super(account);
        this.scanner = scanner;

    }

    @Override
    public void execute() {

        if (account.getTransactionCount() == 0) {
            System.out.println("Inga transaktioner att ta bort.");
            return;
        }

        System.out.println("Aktuella transaktioner:");
        account.showAllTransactions();

        System.out.print("Ange ID för transaktionen du vill ta bort: ");
        int id = scanner.nextInt();

        if (account.removeTransaction(id)) {
            System.out.println("Transaktion borttagen!");
        } else {
            System.out.println("Ingen transaktion med ID " + id + " hittades.");
        }

    }

    @Override
    public String getDescription() {
        return "Ta bort transaktion";
    }
}
