package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {


    /**
     * @param account
     * @throws SQLException add account
     */
    Account addAccount(int clientId, Account account) throws SQLException;

    /**
     * @return
     * @throws SQLException find all client's accounts
     */
    List<Account> getAllClientAccounts(int clientId) throws SQLException;

//    /**
//     *
//     * @param account
//     * @return
//     * @throws SQLException
//     * find account by id
//     */
//    Account getAccountById(Account account) throws SQLException;

    /**
     * @return
     * @throws SQLException find account by id
     */
    Account getAccountById(int id) throws SQLException;

//    /**
//     *
//     * @param client
//     * @return
//     * @throws SQLException
//     * find client's account
//     */
//    Account getAccountById(Client client) throws SQLException;

    /**
     * @param account update account
     */
    Account updateAccount(Account account) throws SQLException;

    /**
     * @return delete account
     */
    boolean deletAccount(int accountId) throws SQLException;


//    BigDecimal checkBalanceByAccountNumber(String accountNumber) throws SQLException;
//    BigDecimal checkBalanceByAccountId(Integer accountId) throws SQLException;
//
//    boolean depositFunds(String accountNumber, BigDecimal amount) throws SQLException;
//
//    List<Account> getAccountListByClientId (Integer clientId) throws SQLException;
//
//    List<CreditCard> getCreditCardListByAccountId(Integer accountId) throws SQLException;
//
//
//    boolean addCreditCard(Integer accountId, String cardNumber) throws SQLException;
//
//    boolean isCardNumberExists(String cardNumber) throws SQLException;
//

}
