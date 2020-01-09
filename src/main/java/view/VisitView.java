package view;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import view.HttpUtils.HttpRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class VisitView {
    private HttpRequestHandler requestHandler = new HttpRequestHandler();
    private ObjectMapper objectMapper = new ObjectMapper();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void fireRegisterVisit() {
        int cNumber = 0;
        String jsonResponse = null;
        Client client = null;
        System.out.println("Введите номер карты клиента.");
        while (cNumber == 0) {
            try {
                cNumber = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                System.out.println("Ошибка ввода, попробуйте ещё раз.");
                e.printStackTrace();
            }
        }
        String url = "/visit/" + cNumber;

        Map<String, String> response = requestHandler.sendPost(url, null);
        String statusCode = response.get("statusCode");

        if (statusCode.startsWith("2")) {
            jsonResponse = response.get("json");

            try {
                client = objectMapper.readValue(jsonResponse, Client.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (client.getLastVisit().getEnd() == null) {
                System.out.printf("Клиент %s %s приступил к занятиям %tc",
                        client.getFirstName(), client.getLastName(), client.getLastVisit().getStart());
            } else {
                System.out.printf("Клиент %s %s завершил занятия %tc",
                        client.getFirstName(), client.getLastName(), client.getLastVisit().getEnd());
            }
        } else if (statusCode.equals("403")) {
            System.out.println("Клиент пришёл не в своё время");
        } else if (statusCode.equals("404")) {
            System.out.printf("Клиент с номером карты %d не найден", cNumber);
        }


    }
}
