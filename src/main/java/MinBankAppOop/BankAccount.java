package MinBankAppOop;

import MinBankAppOop.Repositories.ITransactionRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SuppressWarnings("CallToPrintStackTrace")
public class BankAccount {

    private final ITransactionRepository repository;


    public BankAccount(ITransactionRepository repository) {
        this.repository = repository;
    }

    public void addIncome(String description, Date date, double amount) {
        try {

            int nextId = repository.getNextId();
            IncomeTransaction transaction = new IncomeTransaction(nextId, description, date, amount);
            repository.save(transaction);
        } catch (Exception e) {
            System.err.println("Fel vid tillägg av inkomst:");
            e.printStackTrace();
        }
    }

    public void addExpense(String description, Date date, double amount) {
        try {

            int nextId = repository.getNextId();
            ExspenseTransaction transaction = new ExspenseTransaction(nextId, description, date, amount);
            repository.save(transaction);
        } catch (Exception e) {
            System.err.println("Fel vid tillägg av utgift:");
            e.printStackTrace();
        }
    }

    public boolean removeTransaction(int id) {
        try {
            repository.delete(id);
            return true;

        } catch (Exception e) {
            System.err.println("Fel vid borttagning av transaktion:");
            e.printStackTrace();
            return false;
        }
    }

    public double getBalance() {
        try {

            List<AbstractTransaction> transactions = repository.findAll();
            double balance = 0;
            for (AbstractTransaction t : transactions) {
                balance += t.getAmount();
            }
            return balance;
        } catch (Exception e) {
            System.err.println("Fel vid beräkning av balans:");
            e.printStackTrace();
            return 0;
        }
    }

    public void showAllTransactions() {
        try {

            List<AbstractTransaction> transactions = repository.findAll();
            if (transactions.isEmpty()) {
                System.out.println("Inga transaktioner finns.");
            } else {
                System.out.println("Alla transaktioner:");
                for (AbstractTransaction t : transactions) {
                    System.out.println(t);
                }
            }
        } catch (Exception e) {
            System.err.println("Fel vid visning av transaktioner:");
            e.printStackTrace();
        }
    }

    public int getTransactionCount() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            return transactions.size();
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av antal transaktioner:");
            e.printStackTrace();
            return 0;
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isThisWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(date);
        int dateWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int dateYear = cal.get(Calendar.YEAR);
        return currentWeek == dateWeek && currentYear == dateYear;
    }

    private boolean isThisMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(date);
        int dateMonth = cal.get(Calendar.MONTH);
        int dateYear = cal.get(Calendar.YEAR);
        return currentMonth == dateMonth && currentYear == dateYear;
    }

    private boolean isThisYear(Date date) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(date);
        int dateYear = cal.get(Calendar.YEAR);
        return currentYear == dateYear;
    }

    public double getExpensesToday() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double expenses = 0;
            Date today = new Date();

            for (AbstractTransaction t : transactions) {
                if (isSameDay(t.getDate(), today) && t.getAmount() < 0) {
                    expenses += t.getAmount();
                }
            }
            return expenses;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av dagens utgifter:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getExpensesThisWeek() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double expenses = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisWeek(t.getDate()) && t.getAmount() < 0) {
                    expenses += t.getAmount();
                }
            }
            return expenses;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av veckans utgifter:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getExpensesThisMonth() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double expenses = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisMonth(t.getDate()) && t.getAmount() < 0) {
                    expenses += t.getAmount();
                }
            }
            return expenses;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av månadens utgifter:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getExpensesThisYear() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double expenses = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisYear(t.getDate()) && t.getAmount() < 0) {
                    expenses += t.getAmount();
                }
            }
            return expenses;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av årets utgifter:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getIncomeToday() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double income = 0;
            Date today = new Date();

            for (AbstractTransaction t : transactions) {
                if (isSameDay(t.getDate(), today) && t.getAmount() > 0) {
                    income += t.getAmount();
                }
            }
            return income;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av dagens inkomster:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getIncomeThisWeek() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double income = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisWeek(t.getDate()) && t.getAmount() > 0) {
                    income += t.getAmount();
                }
            }
            return income;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av veckans inkomster:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getIncomeThisMonth() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double income = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisMonth(t.getDate()) && t.getAmount() > 0) {
                    income += t.getAmount();
                }
            }
            return income;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av månadens inkomster:");
            e.printStackTrace();
            return 0;
        }
    }

    public double getIncomeThisYear() {
        try {
            List<AbstractTransaction> transactions = repository.findAll();
            double income = 0;

            for (AbstractTransaction t : transactions) {
                if (isThisYear(t.getDate()) && t.getAmount() > 0) {
                    income += t.getAmount();
                }
            }
            return income;
        } catch (Exception e) {
            System.err.println("Fel vid hämtning av årets inkomster:");
            e.printStackTrace();
            return 0;
        }
    }
}