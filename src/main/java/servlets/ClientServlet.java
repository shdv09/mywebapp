package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientServlet extends HttpServlet {
    ClientService clientService = new ClientService();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = httpServletRequest.getReader();
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            stringBuilder.append(tempString);
        }
        Client client = objectMapper.readValue(stringBuilder.toString(), Client.class);
        System.out.println(client);
        client = clientService.addClient(client);
        if (client != null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            String jsonString = objectMapper.writeValueAsString(client);
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(jsonString);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
