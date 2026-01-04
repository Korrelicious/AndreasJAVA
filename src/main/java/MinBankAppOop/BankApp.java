package MinBankAppOop;
import MinBankAppOop.Command.*;
import MinBankAppOop.Repositories.PostgresTransactionRepository;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApp {
    private BankAccount account;
    private Scanner scanner;
    private Map<Integer, Command> commands;
    private PostgresTransactionRepository repository;

    public BankApp() {
        scanner = new Scanner(System.in);

        try {

            String URL = "jdbc:postgresql://localhost:5432/mydb";
            String USER = "postgres";
            String PASSWORD = "password";

            repository = new PostgresTransactionRepository(URL, USER, PASSWORD);
            account = new BankAccount(repository);

            initializeCommands();

        } catch (SQLException e) {
            System.err.println("Kunde inte ansluta till databasen!");
            System.err.println("Kontrollera att: ");
            System.err.println("1. Docker-containern körs");
            System.err.println("2. Databasen 'bankapp' finns (CREATE DATABASE bankapp;)");
            System.err.println("3. Användarnamn och lösenord stämmer");
            e.printStackTrace();
            System.exit(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put(1, new AddTransactionCommand(account, scanner));
        commands.put(2, new ShowTransactionsCommand(account));
        commands.put(3, new ShowBalanceCommand(account));
        commands.put(4, new RemoveTransactionCommand(account, scanner));
        commands.put(5, new ShowStatisticsCommand(account));
    }


    public static void main(String[] args) {
        BankApp app = new BankApp();
        app.run();
    }


    public void run() {
        System.out.println("=== Välkommen till Andreas oop bankapp med databas! ===");

        while (true) {
            showMenu();

            System.out.print("Välj alternativ (1-6): ");
            int choice = scanner.nextInt();

            if (choice == 6) {
                System.out.println("Stänger databasanslutning");
                repository.closeConnection();
                System.out.println("Tack för att du använde bankappen");
                scanner.close();
                return;
            }

            Command command = commands.get(choice);

            if (command != null) {
                command.execute();
            } else {
                System.out.println("Ogiltigt val! Välj (1-6)");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n--- MENY ---");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            int key = entry.getKey();
            Command command = entry.getValue();
            System.out.println(key + ". " + command.getDescription());
        }
        System.out.println("6. Avsluta");
    }
}