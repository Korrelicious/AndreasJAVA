package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

public class ShowStatisticsCommand extends Command{


    public ShowStatisticsCommand(BankAccount account) {
        super(account);
    }

    @Override
    public void execute() {

            System.out.println("\n=== STATISTIK ===");

            System.out.println("\nUTGIFTER:");
            System.out.println("Idag: " + account.getExpensesToday() + " kr");
            System.out.println("Denna vecka: " + account.getExpensesThisWeek() + " kr");
            System.out.println("Denna månad: " + account.getExpensesThisMonth() + " kr");
            System.out.println("Detta år: " + account.getExpensesThisYear() + " kr");

            System.out.println("\nINKOMSTER:");
            System.out.println("Idag: " + account.getIncomeToday() + " kr");
            System.out.println("Denna vecka: " + account.getIncomeThisWeek() + " kr");
            System.out.println("Denna månad: " + account.getIncomeThisMonth() + " kr");
            System.out.println("Detta år: " + account.getIncomeThisYear() + " kr");

    }

    @Override
    public String getDescription() {
        return "Visa statistik";
    }
}
