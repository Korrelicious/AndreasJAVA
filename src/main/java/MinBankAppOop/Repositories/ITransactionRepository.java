package MinBankAppOop.Repositories;

import MinBankAppOop.AbstractTransaction;

import java.util.List;

public interface ITransactionRepository {

    AbstractTransaction findById(int id) throws Exception;
    List<AbstractTransaction> findAll() throws Exception;
    void save(AbstractTransaction transaction) throws Exception;
    void delete(int id) throws Exception;
    int getNextId() throws Exception;

}
