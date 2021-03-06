package com.bank.repository;

import com.bank.model.Client;
import com.bank.repository.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.bank.ClientTestData.*;

@Slf4j
public class ClientRepositoryImplTest {

    private static ClientRepository repository;

    @BeforeClass
    public static void setup() {
        repository = new ClientRepositoryImpl();
    }

    @Before
    public void setUp() {
        try (Connection connection = DBUtils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() throws SQLException {
        Client client;
        client = repository.getById(CLIENT_1_ID);
        CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
    }

    @Test
    public void getAll() throws SQLException {
        List<Client> allClients = repository.getAll();
        Assert.assertEquals(allClients.size(), 2);
        CLIENTS_MATCHER.assertMatch(allClients, CLIENTS);
    }

    @Test
    public void add() throws SQLException {
        Client newClient = Client.builder()
                .name(CLIENT_3.getName())
                .email(CLIENT_3.getEmail())
                .build();
        repository.save(newClient);
        Client client1 = repository.getById(CLIENT_3.getId());
        CLIENTS_MATCHER.assertMatch(newClient, client1);

    }

    @Test
    public void update() throws SQLException {
        Client client = Client.builder()
                .id(CLIENT_1_ID)
                .name("update name")
                .email("update@mail.ru")
                .build();
        repository.save(client);
        Client client1 = repository.getById(CLIENT_1_ID);
        CLIENTS_MATCHER.assertMatch(client, client1);
    }

    @Test
    public void delete() throws SQLException {
        Client client = Client.builder()
                .name("update name")
                .email("update@mail.ru")
                .build();
        repository.save(client);
        Assert.assertEquals(3, repository.getAll().size());
        repository.delete(100006);
        List<Client> clients = repository.getAll();
        CLIENTS_MATCHER.assertMatch(clients, CLIENT_1, CLIENT_2);
    }
}