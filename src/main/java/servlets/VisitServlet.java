package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import service.ClientService;
import service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class VisitServlet extends HttpServlet {
    ClientService clientService = new ClientService();
    VisitService visitService = new VisitService();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String numberString = httpServletRequest.getRequestURI().replaceAll("/visit/", "");
        int cNumber = Integer.parseInt(numberString);
        Client client = clientService.getClient(cNumber);
        if (client == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (!clientService.validateClient(client)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            visitService.registerVisit(client);
            String jsonString = objectMapper.writeValueAsString(client);
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(jsonString);
        }
    }
}
