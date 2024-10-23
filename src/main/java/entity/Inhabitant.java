package entity;

import exception.PassNumbersOverflowException;
import repository.HashMapInhabitantRepository;

import java.io.Serializable;
import java.util.Objects;

public class Inhabitant implements Serializable {
    private String firstName;
    private String lastName;
    private final String passNumber;
    private static long lastGeneratedPassNumber = HashMapInhabitantRepository.getLastGeneratedPassNumber();
    private final static long MAX_PASS_NUMBER = 0xffffffffL;

    public Inhabitant(String firstName, String lastName) throws PassNumbersOverflowException {
        if (lastGeneratedPassNumber >= MAX_PASS_NUMBER) {
            throw new PassNumbersOverflowException("Превышено возможное число пропусков");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        passNumber = String.format("%08x", ++lastGeneratedPassNumber);
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

    public String getPassNumber() {
        return passNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(passNumber, that.passNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, passNumber);
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Номер пропуска: %s
                        Фамилия: %s
                        Имя: %s
                        """,
                passNumber, lastName, firstName);
    }
}