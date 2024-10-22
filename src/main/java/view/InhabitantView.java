package view;

import controller.InhabitantController;
import entity.Inhabitant;
import exception.InhabitantNotFoundException;
import exception.InvalidPassNumberException;
import exception.PassNumbersOverflowException;

import java.util.Map;
import java.util.Scanner;

public class InhabitantView {
    private final InhabitantController inhabitantController;
    Scanner scanner = new Scanner(System.in);

    public InhabitantView(InhabitantController inhabitantController) {
        this.inhabitantController = inhabitantController;
    }

    public void start() {
        greetings();
        selectionMenu();
    }

    private void greetings() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("|                      Здравствуйте!!!                        |");
        System.out.println("| Вы зашли в систему управления жителями и их картами доступа |");
        System.out.println("---------------------------------------------------------------");
    }

    private void selectionMenu() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Получить список всех жителей");
            System.out.println("2. Получить список всех выданных пропусков");
            System.out.println("3. Добавить нового жителя");
            System.out.println("4. Найти жителя по номеру пропуска");
            System.out.println("5. Удалить жителя по пропуску");
            System.out.println("6. Выключить систему");

            System.out.println();
            System.out.print("Введите номер интересующей вас команды: ");

            String select = scanner.nextLine();
            if (!select.strip().matches("^[1-6]$")) {
                System.out.println("Введена неверная команда. Введите число от 1 до 6");
                continue;
            }
            System.out.println();
            switch (select) {
                case "1" -> printAllInhabitants();
                case "2" -> printAllPassNumber();
                case "3" -> {
                    System.out.print("Введите имя нового жителя: ");
                    String firsName = scanner.nextLine().strip();
                    System.out.print("Введите фамилию нового жителя: ");
                    String lastName = scanner.nextLine().strip();
                    System.out.println();
                    try {
                        Inhabitant inhabitant = inhabitantController.addInhabitant(new Inhabitant(firsName, lastName));
                        System.out.printf("Житель %s %s успешно добавлен\n\n", firsName, lastName);
                    } catch (PassNumbersOverflowException e) {
                        e.printStackTrace();
                    }
                }
                case "4" -> {
                    System.out.print("Введите номер пропуска: ");
                    String passNumber = scanner.nextLine().strip();
                    Inhabitant inhabitant;
                    try {
                        inhabitant = inhabitantController.getInhabitantByPassNumber(passNumber);
                    } catch (InvalidPassNumberException | InhabitantNotFoundException e) {
                        e.printStackTrace();
                        continue;
                    }
                    System.out.println(inhabitant);
                }
                case "5" -> {
                    System.out.print("Введите номер пропуска: ");
                    String passNumber = scanner.nextLine().strip();
                    try {
                        inhabitantController.removeInhabitantByPassNumber(passNumber);
                    } catch (InvalidPassNumberException | InhabitantNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "6" -> {
                    System.out.println();
                    System.out.println(">>>ЗАВЕРШАЮ РАБОТУ ПРИЛОЖЕНИЯ<<<");
                    flag = false;
                }
            }
        }
    }

    private void printAllInhabitants() {
        if (inhabitantController.getInhabitants().isEmpty()) {
            System.out.println(">>>Список пуст<<<");
            System.out.println();
            return;
        }
        System.out.println("Список всех жителей: ");
        for (Map.Entry<String, Inhabitant> entry : inhabitantController.getInhabitants().entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void printAllPassNumber() {
        if (inhabitantController.getInhabitants().isEmpty()) {
            System.out.println(">>>Список пуст<<<");
            System.out.println();
            return;
        }
        System.out.println("Список всех пропусков: ");
        for (Map.Entry<String, Inhabitant> entry : inhabitantController.getInhabitants().entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println();
    }
}