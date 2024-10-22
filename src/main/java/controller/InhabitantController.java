package controller;

import entity.Inhabitant;
import exception.InhabitantNotFoundException;
import exception.InvalidPassNumberException;
import service.InhabitantService;

import java.util.Map;
import java.util.Optional;

public class InhabitantController {
    private final InhabitantService inhabitantService;

    public InhabitantController(InhabitantService inhabitantManager) {
        this.inhabitantService = inhabitantManager;
    }

    public Inhabitant addInhabitant(Inhabitant inhabitant) {
        return inhabitantService.addInhabitant(inhabitant);
    }

    public void removeInhabitantByPassNumber(String passNumber) throws InvalidPassNumberException, InhabitantNotFoundException {
        if (!passNumber.toUpperCase().matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        if (!inhabitantService.removeInhabitantByPassNumber(passNumber.toUpperCase())) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
    }

    public Map<String, Inhabitant> getInhabitants() {
        return inhabitantService.getInhabitants();
    }

    public Inhabitant getInhabitantByPassNumber(String passNumber) throws InvalidPassNumberException, InhabitantNotFoundException {
        if (!passNumber.toUpperCase().matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        Optional<Inhabitant> inhabitant = inhabitantService.getInhabitantByPassNumber(passNumber);
        if (inhabitant.isEmpty()) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
        return inhabitant.get();
    }
}