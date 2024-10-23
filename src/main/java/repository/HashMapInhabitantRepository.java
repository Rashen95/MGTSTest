package repository;

import entity.Inhabitant;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий для работы с перечнем жителей
 */
public class HashMapInhabitantRepository implements InhabitantRepository, Serializable {
    private final Map<String, Inhabitant> inhabitants;
    private static File SAVE_BASE_FILE = new File("src/main/resources/save/repositorySave.bin");
    private static File SAVE_LAST_GENERATED_PASS_NUMBER_FILE =
            new File("src/main/resources/save/lastGeneratedPassNumber.bin");

    public HashMapInhabitantRepository() {
        //Проверяем наличие save-файла для базы данных
        if (!SAVE_BASE_FILE.exists()) {
            inhabitants = new HashMap<>();
            return;
        }

        //Если файл с сохранием базы обнаружен то выполняем чтение и десериализацию
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(SAVE_BASE_FILE))) {
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

    /**
     * Получение списка всех жителей
     *
     * @return список жителей
     */
    @Override
    public Map<String, Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Получение жителя по номеру пропуска
     *
     * @param passNumber номер пропуска
     * @return Optional<Inhabitant>
     */
    @Override
    public Optional<Inhabitant> getInhabitantByPassNumber(String passNumber) {
        return Optional.ofNullable(inhabitants.get(passNumber));
    }

    /**
     * Удаление жителя по номеру пропуска
     *
     * @param passNumber номер пропуска
     * @return факт наличия жителя для удаления
     */
    @Override
    public boolean removeInhabitantByPassNumber(String passNumber) {
        if (inhabitants.containsKey(passNumber)) {
            inhabitants.remove(passNumber);
            saveRepository();
            return true;
        }
        return false;
    }

    /**
     * Добавление нового жителя
     *
     * @param inhabitant житель
     * @return добавленный житель
     */
    @Override
    public Inhabitant addInhabitant(Inhabitant inhabitant) {
        inhabitants.put(inhabitant.getPassNumber(), inhabitant);
        saveRepository();
        return inhabitant;
    }

    /**
     * Сохранение изменений в save-файл
     */
    private void saveRepository() {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream(SAVE_BASE_FILE))) {
            objectOutputStream.writeObject(inhabitants);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getSaveLastGeneratedPassNumberFile() {
        return SAVE_LAST_GENERATED_PASS_NUMBER_FILE;
    }

    public static File getSaveBaseFile() {
        return SAVE_BASE_FILE;
    }

    public static void setSaveBaseFile(File saveBaseFile) {
        SAVE_BASE_FILE = saveBaseFile;
    }

    public static void setSaveLastGeneratedPassNumberFile(File saveLastGeneratedPassNumberFile) {
        SAVE_LAST_GENERATED_PASS_NUMBER_FILE = saveLastGeneratedPassNumberFile;
    }

    /**
     * Очищение базы данных, а также save-файлов
     */
    @Override
    public void clear() {
        if (SAVE_BASE_FILE.exists()) {
            SAVE_BASE_FILE.delete();
        }
        if (SAVE_LAST_GENERATED_PASS_NUMBER_FILE.exists()) {
            SAVE_LAST_GENERATED_PASS_NUMBER_FILE.delete();
        }
    }
}