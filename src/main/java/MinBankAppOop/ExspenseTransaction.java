package MinBankAppOop;

import java.util.Date;

public class ExspenseTransaction extends AbstractTransaction {
    public ExspenseTransaction(int id, String description, Date date, double amount) {
        super(id, description, date, -Math.abs(amount));
    }

    @Override
    public String getType(){
        return "EXPENSE";
    }

    @Override
    public String getFormattedAmount() {
        return String.format("%.2f kr", amount);
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
