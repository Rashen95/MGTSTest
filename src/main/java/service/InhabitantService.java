package service;

import entity.Inhabitant;
import exception.InhabitantNotFoundException;
import exception.InvalidPassNumberException;
import repository.InhabitantRepository;

import java.util.Map;
import java.util.Optional;

public class InhabitantService {
    private final InhabitantRepository inhabitantRepository;

    public InhabitantService(InhabitantRepository inhabitantRepository) {
        this.inhabitantRepository = inhabitantRepository;
    }

    public Inhabitant addInhabitant(Inhabitant inhabitant) {
        return inhabitantRepository.addInhabitant(inhabitant);
    }

    public void removeInhabitantByPassNumber(String passNumber)
            throws InvalidPassNumberException, InhabitantNotFoundException {
        passNumber = passNumber.strip().toUpperCase();
        if (!passNumber.matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        if (!inhabitantRepository.removeInhabitantByPassNumber(passNumber)) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
    }

    public Map<String, Inhabitant> getInhabitants() {
        return inhabitantRepository.getInhabitants();
    }

    public Inhabitant getInhabitantByPassNumber(String passNumber)
            throws InvalidPassNumberException, InhabitantNotFoundException {
        passNumber = passNumber.strip().toUpperCase();
        if (!passNumber.matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        Optional<Inhabitant> inhabitant = inhabitantRepository.getInhabitantByPassNumber(passNumber);
        if (inhabitant.isEmpty()) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
        return inhabitant.get();
    }
}