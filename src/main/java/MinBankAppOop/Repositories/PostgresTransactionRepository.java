package MinBankAppOop.Repositories;

import MinBankAppOop.AbstractTransaction;
import MinBankAppOop.ExspenseTransaction;
import MinBankAppOop.IncomeTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresTransactionRepository implements ITransactionRepository {

   private final Connection connection;

   public PostgresTransactionRepository(String url, String user, String password) throws Exception{

       connection = DriverManager.getConnection(url, user, password);
       System.out.println("Ansluten till databasen");

       try(Statement statement = connection.createStatement()){
           statement.execute(
                   "CREATE TABLE IF NOT EXISTS transactions (" +
                           "id SERIAL PRIMARY KEY," +
                           "description TEXT NOT NULL," +
                           "date TIMESTAMP NOT NULL," +
                           "amount DECIMAL(10, 2) NOT NULL," +
                           "type TEXT NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))" +
                           ");"
           );
           System.out.println("Tabell 'transactions' skapad");
       }
   }

    @Override
    public AbstractTransaction findById(int id) throws Exception {
       String sql = "SELECT * FROM transactions WHERE id = ?";

       try(PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.setInt(1, id);
           ResultSet set = statement.executeQuery();

           if (!set.next()) {
               return null;
           }

           int transactionId = set.getInt("id");
           String description = set.getString("description");
           Timestamp timestamp = set.getTimestamp("date");
           Date date = new Date(timestamp.getTime());
           double amount = set.getDouble("amount");
           String type = set.getString("type");

           if(type.equals("INCOME")){
               return new IncomeTransaction(transactionId,description,date,amount);
           }else {
               return new ExspenseTransaction(transactionId,description,date,amount);
           }
       }
    }

    @Override
    public List<AbstractTransaction> findAll() throws Exception {
       List<AbstractTransaction> transactions = new ArrayList<>();

       try(Statement statement = connection.createStatement()){
           ResultSet set = statement.executeQuery("SELECT * FROM transactions ORDER BY id");

           while (set.next()){
               int id = set.getInt("id");
               String description = set.getString("description");
               Timestamp timestamp = set.getTimestamp("date");
               Date date = new Date(timestamp.getTime());
               double amount = set.getDouble("amount");
               String type = set.getString("type");

               AbstractTransaction transaction;
               if(type.equals("INCOME")){
                   transaction = new IncomeTransaction(id,description,date,amount);
               }else {
                   transaction = new ExspenseTransaction(id,description,date,amount);
               }

               transactions.add(transaction);
           }
       }
        return transactions;
    }

    @Override
    public void save(AbstractTransaction transaction) throws Exception {
       AbstractTransaction existing = findById(transaction.getId());
       if(existing == null){

           String sql = "INSERT INTO transactions (id, description, date, amount, type) VALUES (?,?,?,?,?)";

           try(PreparedStatement statement = connection.prepareStatement(sql)){
               statement.setInt(1, transaction.getId());
               statement.setString(2, transaction.getDescription());
               statement.setTimestamp(3, new Timestamp(transaction.getDate().getTime()));
               statement.setDouble(4, transaction.getAmount());
               statement.setString(5, transaction.getType());
               statement.executeUpdate();
           }
       }else {
           String sql = "UPDATE transactions SET description = ?, date = ?, amount = ?, type = ? WHERE id = ?";

           try(PreparedStatement statement = connection.prepareStatement(sql)){

               statement.setString(1, transaction.getDescription());
               statement.setTimestamp(2, new Timestamp(transaction.getDate().getTime()));
               statement.setDouble(3, transaction.getAmount());
               statement.setString(4, transaction.getType());
               statement.setInt(5, transaction.getId());
               statement.executeUpdate();
           }
       }
    }

    @Override
    public void delete(int id) throws Exception {
       String sql = "DELETE FROM transactions WHERE id = ?";

       try(PreparedStatement statement = connection.prepareStatement(sql)){
           statement.setInt(1, id);
           statement.executeUpdate();
       }
    }

    @Override
    public int getNextId() throws Exception {
       String sql = "SELECT nextval('transactions_id_seq')";

       try(Statement statement = connection.createStatement()){
           ResultSet set = statement.executeQuery(sql);
           if(set.next()){
               return set.getInt(1);
           }
           return 1;
       }

    }

    public void closeConnection(){
       try{
           if(connection != null && !connection.isClosed()){
               connection.close();
               System.out.println("Anslutning stängd");
           }
       }catch (SQLException e){
           System.err.println("Fel vid avstängning av anslutning");
           e.printStackTrace();
       }
    }


}
