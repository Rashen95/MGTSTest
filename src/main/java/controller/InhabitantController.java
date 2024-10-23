package controller;

import entity.Inhabitant;
import exception.InhabitantNotFoundException;
import exception.InvalidPassNumberException;
import service.InhabitantService;

import java.util.Map;

public class InhabitantController {
    private final InhabitantService inhabitantService;

    public InhabitantController(InhabitantService inhabitantManager) {
        this.inhabitantService = inhabitantManager;
    }

    public void addInhabitant(Inhabitant inhabitant) {
        Inhabitant addedInhabitant = inhabitantService.addInhabitant(inhabitant);
        System.out.printf("Житель %s %s успешно добавлен с номером пропуска %s\n\n",
                addedInhabitant.getFirstName(), addedInhabitant.getLastName(), addedInhabitant.getPassNumber());
    }

    public void removeInhabitantByPassNumber(String passNumber) {
        try {
            Inhabitant inhabitant = inhabitantService.getInhabitantByPassNumber(passNumber);
            inhabitantService.removeInhabitantByPassNumber(passNumber);
            System.out.printf("Житель %s %s с пропуском %s успешно удален\n\n",
                    inhabitant.getFirstName(), inhabitant.getLastName(), passNumber);
        } catch (InvalidPassNumberException | InhabitantNotFoundException e) {
            System.out.println("ОШИБКА: " + e.getMessage() + "\n");
        }
    }

    public void printAllInhabitants() {
        if (inhabitantService.getInhabitants().isEmpty()) {
            System.out.println(">>>Список пуст<<<");
            System.out.println();
            return;
        }
        System.out.println("Список всех жителей: ");
        for (Map.Entry<String, Inhabitant> entry : inhabitantService.getInhabitants().entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public void printAllPassNumbers() {
        if (inhabitantService.getInhabitants().isEmpty()) {
            System.out.println(">>>Список пуст<<<");
            System.out.println();
            return;
        }
        System.out.println("Список всех пропусков: ");
        for (Map.Entry<String, Inhabitant> entry : inhabitantService.getInhabitants().entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println();
    }

    public void printInhabitantByPassNumber(String passNumber) {
        try {
            System.out.println(inhabitantService.getInhabitantByPassNumber(passNumber));
        } catch (InvalidPassNumberException | InhabitantNotFoundException e) {
            System.out.println("ОШИБКА: " + e.getMessage() + "\n");
        }
    }
}