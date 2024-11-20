package repository;

import model.Currency;

import java.util.Map;

public interface CurrencyRepository {

    // Геттеры и сеттеры

    public Map<String, Currency> getCurrencyMap();



}