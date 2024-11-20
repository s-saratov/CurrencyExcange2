package repository;

import model.User;
import model.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepositoryImpl implements UserRepository {

    // Поля
    private final Map<Integer, User> users;                                 // карта пользователей
    private final AtomicInteger currentID = new AtomicInteger(1);   // объект, отвечающий за генерацию уникальных ID

    // Конструктор

    public UserRepositoryImpl() {
        // Создаём карту пользователей
        this.users = new HashMap<>();
        // Добавляем администратора по умолчанию
        this.addUser("Bob Howard", "admin@mail.com", "A*,5QReA-J1CDo[", UserRole.ADMIN);
        // Добавляем пользователя по умолчанию
        this.addUser("Max Mustermann", "max@mail.com", "!2345Qwerty");
        // Добавляем заблокированного пользователя
        this.addUser("Sergei Mavrodi","sm@sergey-mavrodi.com", "MmM-4EVER!", UserRole.BLOCKED);
    }


    // Методы

    // === CREATE ===

    // Добавляет пользователя в карту пользователей (без указания роли)
    @Override
    public User addUser(String name, String email, String password) {
        User newUser = new User(this.currentID.getAndIncrement(), name, email, password);
        users.put(newUser.getUserID(), newUser);
        return newUser;
        // TODO: дописать проверки
    }

    // Добавляет пользователя в карту пользователей (с указанием роли)
    @Override
    public User addUser(String name, String email, String password, UserRole role) {
        User newUser = new User(this.currentID.getAndIncrement(), name, email, password, role);
        users.put(newUser.getUserID(), newUser);
        return newUser;
        // TODO: дописать проверки
    }

    // === READ ===

    // Проверяет, зарегистрирован ли пользователь с указанным именем
    @Override
    public List<User> getUserByName(String name) {
        return null;
        // TODO: написать метод
    }

    // Проверяет, зарегистрирован ли пользователь с заданным адресом электронной почты
    @Override
    public boolean isEmailExists(String email) {
        return true;
        // TODO: написать метод
    }

    // Возвращает список пользователей по заданным ролям
    @Override
    public List<User> getUsersByRole(UserRole... roles) {
        return null;
        // TODO: написать метод
    }

    // Возвращает объект пользователя по адресу электронной почты
    @Override
    public User getUserByEmail(String email) {
        return null;
        // TODO: написать метод
    }

    // Возвращает объект пользователя по ID
    @Override
    public User getUserByID(int userId) {
        return users.get(userId);
        // TODO: написать проверки
    }

    // === DELETE ===

    // Удаляет счёт пользователя из карты
    @Override
    public boolean deleteUser(int userId) {
        return true;
        // TODO: написать метод
    }

}