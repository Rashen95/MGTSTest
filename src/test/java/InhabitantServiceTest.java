import entity.Inhabitant;
import exception.*;
import repository.HashMapInhabitantRepository;

import java.io.File;
import java.util.Optional;

public class InhabitantServiceTest {
    private final static HashMapInhabitantRepository hashMapInhabitantRepository = new HashMapInhabitantRepository();

    public static void main(String[] args) {
        HashMapInhabitantRepository.setSaveBaseFile(new File("testSaveBaseFile.bin"));
        HashMapInhabitantRepository.setSaveLastGeneratedPassNumberFile(
                new File("testSaveLastGeneratedPassNumberFile.bin"));
        boolean isPassed = false;

        //Тест на добавление жителя с заведомо неверной фамилией или именем
        try {
            testAddInhabitant(new Inhabitant("Артем", "привалов"));
        } catch (InvalidFirstNameException | InvalidLastNameException e) {
            isPassed = true;
        } catch (PassNumbersOverflowException e) {
            throw new RuntimeException(e);
        }
        System.out.println(isPassed ? "testAddInhabitant with invalid lastName is passed"
                : "testAddInhabitant with invalid lastName is failed");
        isPassed = false;
        try {
            testAddInhabitant(new Inhabitant("123", "Привалов"));
        } catch (InvalidFirstNameException | InvalidLastNameException e) {
            isPassed = true;
        } catch (PassNumbersOverflowException e) {
            throw new RuntimeException(e);
        }
        System.out.println(isPassed ? "testAddInhabitant with invalid firstName is passed"
                : "testAddInhabitant with invalid firstName is failed");
        isPassed = false;
        hashMapInhabitantRepository.clear();

        //Тест на удаление жителя с неверно введенным пропуском или ненайденного жителя
        try {
            testRemoveInhabitantByPassNumber("0000000L");
        } catch (InvalidPassNumberException e) {
            isPassed = true;
        } catch (InhabitantNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(isPassed ? "testRemoveInhabitantByPassNumber with invalid passNumber is passed"
                : "testRemoveInhabitantByPassNumber with invalid passNumber is failed");
        isPassed = false;
        try {
            testRemoveInhabitantByPassNumber("00000001");
        } catch (InvalidPassNumberException e) {
            throw new RuntimeException(e);
        } catch (InhabitantNotFoundException e) {
            isPassed = true;
        }
        System.out.println(isPassed ? "testRemoveInhabitantByPassNumber with notFound passNumber is passed"
                : "testRemoveInhabitantByPassNumber with notFound passNumber is failed");
        isPassed = false;

        //Тест на поиск жителя с неверно введенным пропуском или ненайденного жителя
        try {
            testGetInhabitantByPassNumber("0000000L");
        } catch (InvalidPassNumberException e) {
            isPassed = true;
        } catch (InhabitantNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(isPassed ? "testGetInhabitantByPassNumber with invalid passNumber is passed"
                : "testGetInhabitantByPassNumber with invalid passNumber is failed");
        isPassed = false;
        try {
            testGetInhabitantByPassNumber("00000001");
        } catch (InvalidPassNumberException e) {
            throw new RuntimeException(e);
        } catch (InhabitantNotFoundException e) {
            isPassed = true;
        }
        System.out.println(isPassed ? "testGetInhabitantByPassNumber with notFound passNumber is passed"
                : "testGetInhabitantByPassNumber with notFound passNumber is failed");
    }

    public static Inhabitant testAddInhabitant(Inhabitant inhabitant)
            throws InvalidFirstNameException, InvalidLastNameException {
        //Проверка на соответствие правилам ввода имени
        if (!inhabitant.getFirstName().matches("^[А-Я][а-я]{1,49}$")) {
            throw new InvalidFirstNameException("""
                    Неверный формат имени.
                    Имя должно состоять только из латинских букв,
                    первая должна быть заглавной, остальные прописные.
                    Минимум 2 буквы.""");
        }
        //Проверка на соответствие правилам ввода фамилии
        if (!inhabitant.getLastName().matches("^[А-Я][а-я]{1,49}$")) {
            throw new InvalidLastNameException("""
                    Неверный формат Фамилии.
                    Фамилия должна состоять только из латинских букв,
                    первая должна быть заглавной, остальные прописные.
                    Минимум 2 буквы.""");
        }
        return hashMapInhabitantRepository.addInhabitant(inhabitant);
    }

    public static void testRemoveInhabitantByPassNumber(String passNumber)
            throws InvalidPassNumberException, InhabitantNotFoundException {
        passNumber = passNumber.strip().toUpperCase();
        //Проверка на соответствие ввода номера пропуска
        if (!passNumber.matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        //Проверка наличи жителя в базе с введенным номером пропуска
        if (!hashMapInhabitantRepository.removeInhabitantByPassNumber(passNumber)) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
    }

    public static Inhabitant testGetInhabitantByPassNumber(String passNumber)
            throws InvalidPassNumberException, InhabitantNotFoundException {
        passNumber = passNumber.strip().toUpperCase();
        if (!passNumber.matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        Optional<Inhabitant> inhabitant = hashMapInhabitantRepository.getInhabitantByPassNumber(passNumber);
        if (inhabitant.isEmpty()) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
        return inhabitant.get();
    }
}