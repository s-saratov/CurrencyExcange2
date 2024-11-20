package service;

import model.Account;
import model.Currency;
import model.User;
import repository.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    // Возвращает объект пользователя по ID
    @Override
    public User getUserByID(int userID) {
        return userRepository.getUserByID(userID);
    }

    // Возвращает активного пользователя
    @Override
    public User getActiveUser() {
        return activeUser;
    }

    // Возвращает объект счёта по ID
    @Override
    public Account getAccountByID(int accountID) {
        return accountRepository.getByID(accountID);
    }

    // Возвращает текущий курс валюты по коду
    @Override
    public double getCurrencyRate(String currencyCode) {
        return currencyRepository.getCurrencyMap().get(currencyCode).getRate().getCurrencyRate();
        // TODO: дописать проверку переданного значения
    }

    // Возвращает код валюты по ID счёта
    @Override
    public String getCurrencyCode(int accountID) {
        return getAccountByID(accountID).getCurrencyCode();
    }

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
    @Override
    public void printUserAccounts() {

        List<Account> accounts = accountRepository.getAccounts().get(getActiveUser().getUserID());

        System.out.printf(String.format("\u001B[33m%-5s %-9s %-10s\u001B[0m\n", "ID:", "Валюта:", "Остаток:"));
        for (Account account : accounts) {
            System.out.println(String.format("%-5s %-9s %-10s",
                    account.getAccountID(), account.getCurrencyCode(), account.getBalance()));
        }
        System.out.println();

    }

    // Выводит в консоль строковое представление счетов пользователя (за исключением id)
    @Override
    public void printUserAccounts(int id) {

        List<Account> accounts = accountRepository.getAccounts().get(getActiveUser().getUserID());

        System.out.printf(String.format("\u001B[33m%-5s %-9s %-10s\u001B[0m\n", "ID:", "Валюта:", "Остаток:"));
        for (Account account : accounts) {
            if (account.getAccountID() != id) {
                System.out.println(String.format("%-5s %-9s %-10s",
                        account.getAccountID(), account.getCurrencyCode(), account.getBalance()));
            }
        }

        System.out.println();

    }



    // === UPDATE ===

    // Осуществляет перевод суммы денег с одного счёта (по ID) на другой (по ID)
    @Override
    public void transferMoney(int sourceAccountID, int targetAccountID, BigDecimal amount) {
        Account sourceAccount = getAccountByID(sourceAccountID);
        Account targetAccount = getAccountByID(targetAccountID);

        // Списание суммы с исходного счёта
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));

        // Перевод суммы через евро
        // 1. Сначала переводим сумму в евро (базовая валюта)
        BigDecimal amountInEUR = toEUR(sourceAccountID, amount);
        System.out.println(amountInEUR);

        // 2. Затем из евро переводим в валюту целевого счёта
        BigDecimal convertedAmount = toAccountCurrency(targetAccountID, amountInEUR);
        System.out.println(convertedAmount);

        // Зачисление суммы на целевой счёт
        targetAccount.setBalance(targetAccount.getBalance().add(convertedAmount));
    }


    // Возвращает значение любой суммы в евро (в зависимости от валюты счёта по ID)
    private BigDecimal toEUR(int accountID, BigDecimal amount) {
        BigDecimal rateBD = BigDecimal.valueOf(getCurrencyRate(getCurrencyCode(accountID)));

        if (rateBD.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Курс должен быть больше нуля");
        }

        // Умножение для перевода в евро
        return amount.multiply(rateBD).setScale(2, RoundingMode.HALF_UP);

    }


    // Возвращает значение любой суммы в сумме валюты счёта
    private BigDecimal toAccountCurrency(int accountID, BigDecimal amount) {
        BigDecimal rateBD = BigDecimal.valueOf(getCurrencyRate(getCurrencyCode(accountID)));

        if (rateBD.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Курс должен быть больше нуля");
        }

        // Делим для перевода из евро в целевую валюту
        return amount.divide(rateBD, 2, RoundingMode.HALF_UP);



    }



}