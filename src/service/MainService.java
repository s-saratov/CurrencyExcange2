package service;

import model.User;

public interface MainService {

// === READ ===

    // Выводит в консоль строковое представление курсов валют
    public void printCurrencyRates();

    // Выводит в консоль строковое представление счетов пользователя
    public void printUserAccounts();

    // Возвращает активного пользователя
    public User getActiveUser();

}