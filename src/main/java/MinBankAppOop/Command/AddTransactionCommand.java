package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

import java.util.Date;
import java.util.Scanner;

public class AddTransactionCommand extends Command {

    private final Scanner scanner;


    public AddTransactionCommand(BankAccount account, Scanner scanner) {
        super(account);
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        scanner.nextLine();

        System.out.println("\nVälj transaktion:");
        System.out.println("1. Inkomst");
        System.out.println("2. Utgift");
        int type = scanner.nextInt();
        scanner.nextLine();


        System.out.print("Title: ");
        String description = scanner.nextLine();


        System.out.print("Belopp: ");
        double amount = scanner.nextDouble();

        Date currentDate = new Date();

        if (type == 1) {
            account.addIncome(description, currentDate, amount);
            System.out.println("Inkomst tillagd!");
        } else if (type == 2) {
            account.addExpense(description, currentDate, amount);
            System.out.println("Utgift tillagd!");
        } else {
            System.out.println("Ogiltigt val!");
        }

    }

    @Override
    public String getDescription() {
        return "Lägg till transaktion";
    }
}
