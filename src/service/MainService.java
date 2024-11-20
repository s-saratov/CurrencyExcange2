package service;

import model.Account;
import model.User;

import java.math.BigDecimal;

public interface MainService {

    // === READ ===

    // Возвращает активного пользователя
    public User getActiveUser();

    // Возвращает объект пользователя по ID
    public User getUserByID(int userID);

    // Возвращает объект счёта по ID
    public Account getAccountByID(int accountID);

    // Возвращает текущий курс валюты по коду
    public double getCurrencyRate(String currencyCode);

    // Возвращает код валюты по ID счёта
    public String getCurrencyCode(int accountID);

    // Выводит в консоль строковое представление курсов валют
    public void printCurrencyRates();

    // Выводит в консоль строковое представление счетов пользователя
    public void printUserAccounts();

    // Выводит в консоль строковое представление счетов пользователя (за исключением id)
    public void printUserAccounts(int id);



    // === UPDATE ===

    // Осуществляет перевод суммы денег с одного счёта (по ID) на другой (по ID)
    public void transferMoney(int sourceAccountID, int targetAccountID, BigDecimal amount);

}