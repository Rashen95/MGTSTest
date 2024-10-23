package repository;

import entity.Inhabitant;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapInhabitantRepository implements InhabitantRepository, Serializable {
    private final Map<String, Inhabitant> inhabitants;
    private static long lastGeneratedPassNumber;
    private final static String SAVE_FILE_PATH = "repositorySave.bin";

    public HashMapInhabitantRepository() {
        File file = new File(SAVE_FILE_PATH);
        if (!file.exists()) {
            inhabitants = new HashMap<>();
            lastGeneratedPassNumber = 0;
            return;
        }
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {
            Object o = objectInputStream.readObject();
            if (o instanceof HashMap) {
                inhabitants = (HashMap<String, Inhabitant>) o;
            } else {
                inhabitants = new HashMap<>();
                lastGeneratedPassNumber = 0;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Inhabitant> getInhabitants() {
        return inhabitants;
    }

    @Override
    public Optional<Inhabitant> getInhabitantByPassNumber(String passNumber) {
        return Optional.ofNullable(inhabitants.get(passNumber));
    }

    @Override
    public boolean removeInhabitantByPassNumber(String passNumber) {
        if (inhabitants.containsKey(passNumber)) {
            inhabitants.remove(passNumber);
            saveRepository();
            return true;
        }
        return false;
    }

    @Override
    public Inhabitant addInhabitant(Inhabitant inhabitant) {
        inhabitants.put(inhabitant.getPassNumber(), inhabitant);
        saveRepository();
        return inhabitant;
    }

    private void saveRepository() {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream("repositorySave.bin"))) {
            objectOutputStream.writeObject(inhabitants);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getLastGeneratedPassNumber() {
        return lastGeneratedPassNumber;
    }
}