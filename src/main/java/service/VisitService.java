package service;

import dao.ClientDao;
import dao.VisitDao;
import model.Client;
import model.Visit;

import java.util.Date;

public class VisitService {
    private VisitDao visitDao = new VisitDao();
    private ClientDao clientDao = new ClientDao();

    public void createVisit(Client client) {
        Visit lastVisit = new Visit(new Date(), null, client.getCardNumber());
        client.setLastVisit(lastVisit);
        int lastVisitId = visitDao.createVisit(lastVisit);
        lastVisit.setId(lastVisitId);
        clientDao.updateClient(client);
    }

    public void closeVisit(Client client) {
        Visit lastVisit = client.getLastVisit();
        lastVisit.setEnd(new Date());
        visitDao.updateVisit(lastVisit);
        clientDao.updateClient(client);
    }

    public void registerVisit(Client client) {

        Visit lastVisit = client.getLastVisit();

        if (lastVisit == null ) {
            createVisit(client);
            System.out.println("Клиент " + client.getLastName() + " " + client.getFirstName() + " приступил к занятиям в " + client.getLastVisit().getStart());
        } else if (lastVisit.getStart() != null && lastVisit.getEnd() == null) {
            closeVisit(client);
            System.out.println("Клиент " + client.getLastName() + " " + client.getFirstName() + " закончил занятия в " + client.getLastVisit().getEnd());
        } else if (lastVisit.getStart() != null && lastVisit.getEnd() != null) {
            createVisit(client);
            System.out.println("Клиент " + client.getLastName() + " " + client.getFirstName() + " приступил к занятиям в " + client.getLastVisit().getStart());
        }
    }
}
