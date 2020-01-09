package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
@JsonAutoDetect
public class Client {


    private String firstName;
    private String lastName;
    private Visit lastVisit;
    private int cardNumber;
    private boolean isActive;
    private AccessLevel accessLevel;

    public Client() {}

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
    }

    public Client(String firstName, String lastName, AccessLevel accessLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessLevel = accessLevel;
        this.isActive = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Visit getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Visit lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastVisit=" + lastVisit +
                ", cardNumber=" + cardNumber +
                ", isActive=" + isActive +
                ", accessLevel=" + accessLevel +
                '}';
    }
}
