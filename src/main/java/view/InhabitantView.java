package view;

import controller.InhabitantController;
import entity.Inhabitant;
import exception.PassNumbersOverflowException;

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
                System.out.println();
                continue;
            }

            System.out.println();

            switch (select) {
                case "1" -> inhabitantController.printAllInhabitants();
                case "2" -> inhabitantController.printAllPassNumbers();
                case "3" -> {
                    System.out.print("Введите имя нового жителя: ");
                    String firsName = scanner.nextLine().strip();
                    System.out.print("Введите фамилию нового жителя: ");
                    String lastName = scanner.nextLine().strip();
                    System.out.println();
                    try {
                        inhabitantController.addInhabitant(new Inhabitant(firsName, lastName));
                    } catch (PassNumbersOverflowException e) {
                        System.out.println("ОШИБКА: " + e.getMessage());
                    }
                }
                case "4" -> {
                    System.out.print("Введите номер пропуска: ");
                    String passNumber = scanner.nextLine();
                    System.out.println();
                    inhabitantController.printInhabitantByPassNumber(passNumber);
                }
                case "5" -> {
                    System.out.print("Введите номер пропуска: ");
                    String passNumber = scanner.nextLine();
                    System.out.println();
                    inhabitantController.removeInhabitantByPassNumber(passNumber);
                }
                case "6" -> {
                    System.out.println(">>>ЗАВЕРШАЮ РАБОТУ ПРИЛОЖЕНИЯ<<<");
                    flag = false;
                }
            }
        }
    }
}