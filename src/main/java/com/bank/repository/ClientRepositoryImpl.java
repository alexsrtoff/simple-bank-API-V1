package com.bank.repository;

import com.bank.model.Client;
import com.bank.repository.utils.DBUtils;
import com.bank.repository.utils.SqlScripts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.*;

public class ClientRepositoryImpl implements ClientRepository {

//    private static final String SQL_GET_CLIENT_BY_ACCOUNT_ID = "SELECT * FROM clients WHERE id = " +
//            "(SELECT clients_id FROM accounts WHERE id = ?)";

    @Override
    public Client getById(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.GET_CLIENT_BY_ID.getPath());
        Client client = null;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = Client.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .email(rs.getString(3))
                        .registered(rs.getDate(4))
                        .build();
            }
        }
        if (client == null) {
            throw new SQLException("Client with Id=" + id + ", not found");
        } else {
            return client;
        }
    }

    @Override
    public List<Client> getAll() throws SQLException {
        String sql = getSQLPath(SqlScripts.SELECT_ALL_CLIENTS.getPath());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            List<Client> clientList = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setRegistered(resultSet.getTimestamp("registered"));
                clientList.add(client);
            }
            return clientList;
        }
    }

    @Override
    public Client save(Client client) throws SQLException {
        String sql;
        if (client.getId() < 1) {
            sql = getSQLPath(SqlScripts.SAVE_CLIENT.getPath());
            try (PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, client.getName());
                ps.setString(2, client.getEmail());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        client.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } else {
            sql = getSQLPath(SqlScripts.UPDATE_CLIENT.getPath());
            try (PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql)) {
                ps.setString(1, client.getName());
                ps.setString(2, client.getEmail());
                ps.setInt(3, client.getId());
                int success = ps.executeUpdate();
                if (success < 1) {
                    throw new SQLException("Updating client with Id = " + client.getId()
                            + ", was not found. Updating failed.");
                }
            }
        }
        return client;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.DELETE_CLIENT.getPath());
        Integer success = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            success = ps.executeUpdate();
        }
        if (success < 1) {
            throw new SQLException("Deleting client with Id = " + id
                    + ", was not found. Deleting failed.");
        } else {
            return true;
        }
    }

//    @Override
//    public void addClientAccount(Client client, Account account) throws SQLException {
//        String sql = getSQLPath(SqlScripts.ADD_CLIENT_ACCOUNT.getPath());
//        try (Connection connection = getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, account.getId());
//            ps.setInt(2, client.getId());
//            ps.setString(3, account.getNumber());
//            ps.setBigDecimal(4, account.getAmount());
//            ps.setString(5, account.getCurrency());
//            ps.execute();
//        }
//    }

//    @Override
//    public Client getByAccountId(int accountId) throws SQLException {
//        Connection connection = getConnection();
//        PreparedStatement ps = connection.prepareStatement(SQL_GET_CLIENT_BY_ACCOUNT_ID);
//        ps.setInt(1, accountId);
//        ResultSet resultSet = ps.executeQuery();
//        Client client = null;
//        if (resultSet.next()) {
//            client = new Client();
//            client.setId(resultSet.getInt("id"));
//            client.setName(resultSet.getString("name"));
//            client.setEmail(resultSet.getString("email"));
//            client.setRegistered(resultSet.getTimestamp("registered"));
//        }
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(connection);
//
//        if (client == null) {
//            throw new SQLException("Not found client with Account id = " + accountId);
//        } else return client;
//    }

}
