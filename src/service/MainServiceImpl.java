package service;

import model.Account;
import model.Currency;
import model.User;
import repository.*;

import java.util.List;
import java.util.Map;

public class MainServiceImpl implements MainService {

    // Поля

    private final AccountRepository accountRepository;          // репозиторий счетов
    private final CurrencyRepository currencyRepository;        // репозиторий валют
    private final RateHistoryRepository rateHistoryRepository;  // репозиторий истории валют
    private final UserRepository userRepository;                // репозиторий пользователей
    private User activeUser;                                    // действующий пользователь

    // Конструктор

    public MainServiceImpl(AccountRepository accountRepository,
                           CurrencyRepository currencyRepository,
                           RateHistoryRepository rateHistoryRepository,
                           UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
        this.rateHistoryRepository = rateHistoryRepository;
        this.userRepository = userRepository;
        this.activeUser = userRepository.getUserByID(2);    // TODO: изменить в дальнейшем
    }

    // Методы

    // === READ ===

    // Выводит в консоль строковое представление курсов валют
    public void printCurrencyRates() {

        Map<String, Currency> currencies = currencyRepository.getCurrencyMap();

        System.out.println("\n\u001B[33mТекущие курсы валют:\u001B[0m");
        for (Map.Entry<String, Currency> entry : currencies.entrySet()) {
            if (entry.getKey().equals("EUR")) continue;
            else {
                // System.out.println(entry.getKey() + " " + entry.getValue().getCurrencyName() + " " + entry.getValue().getRate().getCurrencyRate() + " Euro");
                System.out.printf("1 %-15s (%s) = %f Евро\n",
                        entry.getValue().getCurrencyName(),
                        entry.getKey(),
                        entry.getValue().getRate().getCurrencyRate());
            }
        }
    }

    // Выводит в консоль строковое представление счетов пользователя
    public void printUserAccounts() {

        List<Account> accounts = accountRepository.getAccounts().get(getActiveUser().getUserID());

        System.out.printf(String.format("\u001B[33m%-5s %-9s %-10s\u001B[0m\n", "ID:", "Валюта:", "Остаток:"));
        for (Account account : accounts) {
            System.out.println(String.format("%-5s %-9s %-10s",
                    account.getAccountID(), account.getCurrencyCode(), account.getBalance()));
        }
        System.out.println();

    }

    // Возвращает активного пользователя
    @Override
    public User getActiveUser() {
        return activeUser;
    }

}