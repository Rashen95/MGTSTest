package service;

import repository.InhabitantRepository;
import entity.Inhabitant;

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

    public boolean removeInhabitantByPassNumber(String passNumber) {
        return inhabitantRepository.removeInhabitantByPassNumber(passNumber);
    }

    public Map<String, Inhabitant> getInhabitants() {
        return inhabitantRepository.getInhabitants();
    }

    public Optional<Inhabitant> getInhabitantByPassNumber(String passNumber) {
        return inhabitantRepository.getInhabitantByPassNumber(passNumber);
    }
}