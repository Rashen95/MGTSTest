package service;

import entity.Inhabitant;
import exception.InhabitantNotFoundException;
import exception.InvalidFirstNameException;
import exception.InvalidLastNameException;
import exception.InvalidPassNumberException;
import repository.InhabitantRepository;

import java.util.Map;
import java.util.Optional;

/**
 * Сервис для управления репозиторием
 * Здесь заложена основная бизнес-логика
 * и все необходимые проверки корректности введенных данных
 */
public class InhabitantService {
    private final InhabitantRepository inhabitantRepository;

    public InhabitantService(InhabitantRepository inhabitantRepository) {
        this.inhabitantRepository = inhabitantRepository;
    }

    /**
     * Добавление нового жителя
     * @param inhabitant новый житель
     * @return добавленный житель
     * @throws InvalidFirstNameException неверно введено имя
     * @throws InvalidLastNameException неверно введена фамилия
     */
    public Inhabitant addInhabitant(Inhabitant inhabitant)
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
        return inhabitantRepository.addInhabitant(inhabitant);
    }

    /**
     * Удаление жителя по номеру пропуска
     * @param passNumber номер пропуска
     * @throws InvalidPassNumberException неверно введен номер пропуска
     * @throws InhabitantNotFoundException не найден житель по введенному номеру пропуска
     */
    public void removeInhabitantByPassNumber(String passNumber)
            throws InvalidPassNumberException, InhabitantNotFoundException {
        passNumber = passNumber.strip().toUpperCase();
        //Проверка на соответствие ввода номера пропуска
        if (!passNumber.matches("^[0-9A-F]{8}$")) {
            throw new InvalidPassNumberException("Вы ввели некорректный номер пропуска");
        }
        //Проверка наличи жителя в базе с введенным номером пропуска
        if (!inhabitantRepository.removeInhabitantByPassNumber(passNumber)) {
            throw new InhabitantNotFoundException("Житель с таким пропуском не найден");
        }
    }

    /**
     * Получение списка всех жителей
     * @return список жителей
     */
    public Map<String, Inhabitant> getInhabitants() {
        return inhabitantRepository.getInhabitants();
    }

    /**
     * Получение жителя по номеру пропуска
     * @param passNumber номер пропуска
     * @return найденный житель
     * @throws InvalidPassNumberException неверное введегн номер пропуска
     * @throws InhabitantNotFoundException не найден житель по введенному номеру пропуска
     */
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

    /**
     * Очистка базы данных, а также save-файла
     */
    public void clear() {
        inhabitantRepository.clear();
    }
}