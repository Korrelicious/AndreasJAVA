package MinBankAppOop;

import java.util.Date;

public abstract class AbstractTransaction {
    protected int id;
    protected String description;
    protected Date date;
    protected double amount;


    public AbstractTransaction(int id, String description, Date date, double amount){
        this.id = id;
        this.description = description;
        this.date = date;
        this.amount = amount;

    }
    public int getId() {return id;}
    public Date getDate() {return date;}
    public double getAmount() {return amount;}
    public abstract String getType();
    public abstract String getFormattedAmount();
    public abstract String getDescription();
}

