package view;

import service.MainService;

import java.util.Scanner;

public class Menu {

    // Поля

    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    // Конструктор

    public Menu(MainService service) {
        this.service = service;
    }

    // Методы

    // Запускает программу
    public void run() {
        this.showMainMenu();
    }

    // Главное меню
    private void showMainMenu() {

        System.out.println("\u001B[32m\nДобро пожаловать в обменный пункт валюты!\u001B[0m\n" +
                "1. Обмен валюты\n" +
                "2. Меню пользователя\n" +
                "3. Меню администратора\n" +
                "0. Выход из системы\n");

        int choice = getSelection();

        while (true) {
            switch (choice) {
                case 1:
                    showExchangeMenu();
                    break;
                case 2:
                    showUserMenu();
                    break;
                case 3:
                    if (service.getActiveUser() == null) {
                        System.out.print("\u001B[31m\nДоступ запрещён! Вы не авторизованы как администратором системы." +
                                "\u001B[0m\n");
                        waitRead();
                        showMainMenu();
                        break;
                    }
                    showAdminMenu();
                    break;
                case 0:
                    System.out.println("До свидания!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Некорректный выбор. Повторите ввод.");
                    choice = getSelection();
                    break;
            }
        }
    }

    private void showExchangeMenu() {
        System.out.println("\nОбмен валюты:\n" +
                "1. Показать курсы валюты\n" +
                "2. Произвести обмен\n" +
                "0. Вернуться в предыдущее меню\n");

        int choice = getSelection();

        while (true) {
            switch (choice) {
                case 1:
                    service.printCurrencyRates();
                    System.out.println();
                    waitRead();
                    showExchangeMenu();
                    break;
                case 2:
                    if (service.getActiveUser() == null) {
                        System.out.println("\u001B[31m\nВы не авторизованы в системе. Пройдите авторизацию.\u001B[0m");
                        waitRead();
                        showExchangeMenu();
                        }
                    showExchangeTransactionMenu();
                    break;
                case 0:
                    showMainMenu();
                    break;
                default:
                    System.out.println("Некорректный выбор. Повторите ввод.");
                    choice = getSelection();
                    break;
            }
        }
    }

    private void showExchangeTransactionMenu() {
        System.out.println("\nВведите ID счёта списания:\n");
        service.printUserAccounts();
        waitRead();
        showExchangeMenu();
    }

    private void showUserMenu() {

    }

    private void showAdminMenu() {
    }

    private void waitRead() {
        System.out.print("Для продолжения нажмите Enter...");
        scanner.nextLine();
    }

    /**
     * Возвращает только введённое число (предотвращая ошибку в случае ввода символов).
     *
     * @return Выбор.
     */
    private int getSelection() {
        int selection;
        while (true) {
            System.out.print("Введите Ваш выбор: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Вы ввели не число. Повторите ввод.");
                scanner.nextLine();
            } else {
                selection = scanner.nextInt();
                scanner.nextLine();
                return selection;
            }
        }
    }

}