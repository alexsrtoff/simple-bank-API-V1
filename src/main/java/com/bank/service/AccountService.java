package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.AccountRepositoryImpl;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;

import java.sql.SQLException;
import java.util.List;

public class AccountService implements ServiceIntf {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private TxManager txManager;


    public AccountService() {
        this.accountRepository = new AccountRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public List<Account> getAll(int id) {
        List<Account> allClientAccounts = null;
        try {
            allClientAccounts = accountRepository.getAllClientAccounts(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allClientAccounts;
    }

    @Override
    public Account getById(int id) {
        Account account = null;
        try {
            account = accountRepository.getAccountById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }


    @Override
    public void add(Object o, Object b) {
        int clientId = (int) o;
        Account account = (Account) b;
        try {
            accountRepository.addAccount(clientId, account);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void add(Object o) {

    }

    @Override
    public void update(Object o) {
        Account account = (Account) o;
        try {
            accountRepository.updateAccount(account);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean delete(Object o) {
        int id = (int) o;
        try {
            if (txManager.doInTransaction(() -> accountRepository.deletAccount(id))){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
