import entity.Inhabitant;
import exception.PassNumbersOverflowException;
import repository.HashMapInhabitantRepository;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class HashMapInhabitantRepositoryTest {
    private final static HashMapInhabitantRepository hashMapInhabitantRepository = new HashMapInhabitantRepository();

    public static void main(String[] args) throws PassNumbersOverflowException {
        HashMapInhabitantRepository.setSaveBaseFile(new File("testSaveBaseFile.bin"));
        HashMapInhabitantRepository.setSaveLastGeneratedPassNumberFile(
                new File("testSaveLastGeneratedPassNumberFile.bin"));
        hashMapInhabitantRepository.clear();
        testAddInhabitant();
        hashMapInhabitantRepository.clear();
        testGetInhabitantByPassNumber();
        hashMapInhabitantRepository.clear();
        testRemoveInhabitantByPassNumber();
        hashMapInhabitantRepository.clear();
    }

    public static void testAddInhabitant() throws PassNumbersOverflowException {
        Inhabitant inhabitant = new Inhabitant("Артем", "Привалов");
        hashMapInhabitantRepository.addInhabitant(inhabitant);

        Map<String, Inhabitant> inhabitants = hashMapInhabitantRepository.getInhabitants();

        if (!inhabitants.containsKey(inhabitant.getPassNumber())
                || !inhabitants.get(inhabitant.getPassNumber()).equals(inhabitant)) {
            System.out.println("Тест не пройден (добавленный житель не найден)");
        } else {
            System.out.println("testAddInhabitant passed");
        }
    }

    public static void testGetInhabitantByPassNumber() throws PassNumbersOverflowException {
        Inhabitant inhabitant = new Inhabitant("Артем", "Привалов");
        hashMapInhabitantRepository.addInhabitant(inhabitant);

        Optional<Inhabitant> result = hashMapInhabitantRepository.getInhabitantByPassNumber(inhabitant.getPassNumber());
        if (result.isEmpty() || !result.get().equals(inhabitant)) {
            System.out.println("Тест не пройден (житель по номеру пропуска не найден)");
        } else {
            System.out.println("testGetInhabitantByPassNumber passed");
        }
    }

    public static void testRemoveInhabitantByPassNumber() throws PassNumbersOverflowException {
        Inhabitant inhabitant = new Inhabitant("Артем", "Привалов");
        hashMapInhabitantRepository.addInhabitant(inhabitant);
        boolean isRemoved = hashMapInhabitantRepository.removeInhabitantByPassNumber(inhabitant.getPassNumber());

        if (!isRemoved) {
            System.out.println("Тест не пройден (житель по номеру пропуска не удален)");
        } else {
            System.out.println("testRemoveInhabitantByPassNumber passed");
        }
    }
}