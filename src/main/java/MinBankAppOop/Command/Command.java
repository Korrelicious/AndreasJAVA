package MinBankAppOop.Command;

import MinBankAppOop.BankAccount;

public abstract class Command {
     protected BankAccount account;


     public Command(BankAccount account){
          this.account = account;
     }


     public abstract void execute();
     public abstract String getDescription();



}
