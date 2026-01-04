package MinBankAppOop;

import java.util.Date;

public class IncomeTransaction extends AbstractTransaction {

    public IncomeTransaction(int id, String description, Date date, double amount) {
        super(id, description, date, Math.abs(amount));
    }

    @Override
    public String getType(){
        return "INCOME";
    }

    @Override
    public String getFormattedAmount(){
        return String.format("+%.2f kr", amount);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return  getType() +
                "[id=" + id +
                "], Beskrivning = '" + description + '\'' +
                ", Belopp = " + getFormattedAmount() +
                ", Datum = " + date +
                '}';
    }
}

