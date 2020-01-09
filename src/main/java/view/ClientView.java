package view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.AccessLevel;
import model.Client;
import view.HttpUtils.HttpRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class ClientView {
    private HttpRequestHandler requestHandler = new HttpRequestHandler();
    private ObjectMapper objectMapper = new ObjectMapper();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void fireAddClient() {

        String firstName = "";
        String lastName = "";
        AccessLevel accessLevel = null;
        String jsonResponse = null;

        System.out.println("Регистрация нового клиента.");

        while (firstName.equals("")) {
            System.out.println("\tВведите имя:");
            try {
                firstName = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (lastName.equals("")) {
            System.out.println("\tВведите фамилию:");

            try {
                lastName = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (accessLevel == null) {
            System.out.println("Введите тип карты.");
            for (AccessLevel level : AccessLevel.values()) {
                System.out.println(level.ordinal() + " " + level);
            }

            try {
                accessLevel = AccessLevel.values()[Integer.parseInt(reader.readLine().trim())];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Client newClient = new Client(firstName, lastName, accessLevel);
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(newClient);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(jsonRequest);
        Map<String, String> response = requestHandler.sendPost("/client", jsonRequest);
        String statusCode = response.get("statusCode");

        if (statusCode.startsWith("2")) {
            jsonResponse = response.get("json");
            try {
                newClient = objectMapper.readValue(jsonResponse, Client.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Новый клиент добавлен.");
            System.out.println("\tИмя: " + newClient.getFirstName());
            System.out.println("\tФамилия: " + newClient.getLastName());
            System.out.println("\tНомер карты: " + newClient.getCardNumber());
            System.out.println("\tТип карты: " + newClient.getAccessLevel());
        }
    }
}
