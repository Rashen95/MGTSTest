package repository;

import entity.Inhabitant;

import java.util.Map;
import java.util.Optional;

public interface InhabitantRepository {
    Map<String, Inhabitant> getInhabitants();

    Optional<Inhabitant> getInhabitantByPassNumber(String passNumber);

    boolean removeInhabitantByPassNumber(String passNumber);

    Inhabitant addInhabitant(Inhabitant inhabitant);

    void clear();
}