/**
 Aceasta este clasa ClientBll care se ocupa de logica clasei
 */
package org.example.Business_Logic;
import org.example.Data_Access.ClientDAO;
import org.example.Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
public class ClientBLL {
    private static List<Validator<Client>> validators;
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new PhoneValidator());
    }

    public static Client findClientById(int id) {
        Client client = ClientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }
    /**
     Metoda asta adauga clienti
     */
    public static void addClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        ClientDAO.insert(client);
    }
    /**
     Metoda asta editeaza baza de date a clientilor
     */
    public static void editClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        if (client.getId() == 0) {
            throw new IllegalArgumentException("Client ID cannot be zero!");
        }
        ClientDAO.update(client);
    }
    /**
     Metoda asta sterge clienti
     */
    public void deleteClient(int clientId) {
        if (clientId <= 0) {
            throw new IllegalArgumentException("Invalid client ID!");
        }
        ClientDAO.delete(clientId);
    }

    public List<Client> getAllClients() {
        return ClientDAO.getAllClients();
    }

    public static class EmailValidator implements Validator<Client> {
        private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        public void validate(Client client) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            if (!pattern.matcher(client.getEmail()).matches()) {
                throw new IllegalArgumentException("Email is not a valid email!");
            }
        }
    }

    public static class PhoneValidator implements Validator<Client> {
        private static final String PHONE_PATTERN = "^\\d{10}$"; // Telefonul trebuie să conțină exact 10 cifre

        public void validate(Client client) {
            String phone = client.getPhone();
            if (!phone.matches("\\d+")) {
                throw new IllegalArgumentException("Phone number must contain only digits!");
            }
            if (!Pattern.matches(PHONE_PATTERN, phone)) {
                throw new IllegalArgumentException("Phone number is not valid!");
            }
        }
    }
}
