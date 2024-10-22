package repository;

import entity.Inhabitant;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InhabitantRepository implements Serializable {
    private final Map<String, Inhabitant> inhabitants;

    public InhabitantRepository() {
        File file = new File("repositorySave.bin");
        if (!file.exists()) {
            inhabitants = new HashMap<>();
            return;
        }
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream("repositorySave.bin"))) {
            Object o = objectInputStream.readObject();
            if (o instanceof HashMap) {
                inhabitants = (HashMap<String, Inhabitant>) o;
            } else {
                inhabitants = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public Optional<Inhabitant> getInhabitantByPassNumber(String passNumber) {
        return Optional.ofNullable(inhabitants.get(passNumber));
    }

    public boolean removeInhabitantByPassNumber(String passNumber) {
        if (inhabitants.containsKey(passNumber)) {
            inhabitants.remove(passNumber);
            repositorySaveUpdate();
            return true;
        }
        return false;
    }

    public Inhabitant addInhabitant(Inhabitant inhabitant) {
        inhabitants.put(inhabitant.getPassNumber(), inhabitant);
        repositorySaveUpdate();
        return inhabitant;
    }

    private void repositorySaveUpdate() {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream("repositorySave.bin"))) {
            objectOutputStream.writeObject(inhabitants);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}