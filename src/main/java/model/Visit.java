package model;

import java.util.Date;

public class Visit {
    int id;
    private Date start;
    private Date end;
    int cardNumber;

    public Visit() {
    }

    public Visit(Date start, Date end, int cardNumber) {
        this.start = start;
        this.end = end;
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
