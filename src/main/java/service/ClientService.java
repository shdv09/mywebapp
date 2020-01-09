package service;

import dao.ClientDao;
import model.Client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientService {
    private ClientDao clientDao = new ClientDao();

    public Client addClient(Client client) {
        return clientDao.createClient(client);
    }

    public Client getClient(int cNumber) {
        return clientDao.getClient(cNumber);
    }

    public boolean validateClient(Client client) {
        DateFormat df = new SimpleDateFormat("H:mm");
        Date start = null;
        Date end = null;
        Date current = null;
        try {
            start = df.parse(client.getAccessLevel().getStart());
            end = df.parse(client.getAccessLevel().getEnd());
            current = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (current.after(start) && current.before(end));
    }
}
