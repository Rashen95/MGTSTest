package repository;

import entity.Inhabitant;

import java.util.Map;
import java.util.Optional;

public interface InhabitantRepository {
    public Map<String, Inhabitant> getInhabitants();

    public Optional<Inhabitant> getInhabitantByPassNumber(String passNumber);

    boolean removeInhabitantByPassNumber(String passNumber);

    public Inhabitant addInhabitant(Inhabitant inhabitant);
}