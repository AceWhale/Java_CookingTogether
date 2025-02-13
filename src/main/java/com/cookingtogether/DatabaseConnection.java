package com.cookingtogether;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для управления соединением с базой данных.
 * Реализует паттерн Singleton, обеспечивая единое соединение с БД в рамках приложения.
 */
public class DatabaseConnection {

    /** Единственный экземпляр класса (Singleton). */
    private static DatabaseConnection instance;

    /** Объект соединения с базой данных. */
    private Connection connection;

    /**
     * Приватный конструктор, который устанавливает соединение с базой данных.
     * Использует параметры подключения, такие как URL, имя пользователя и пароль.
     */
    private DatabaseConnection() {
        try {
            // URL, имя пользователя и пароль для подключения к базе данных.
            String url = "jdbc:mariadb://127.0.0.1:3306/cooking";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения единственного экземпляра класса DatabaseConnection.
     * Если экземпляр не создан, создаёт новый.
     *
     * @return экземпляр DatabaseConnection.
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Метод для получения текущего соединения с базой данных.
     *
     * @return объект {@link Connection}, представляющий соединение с базой данных.
     */
    public Connection getConnection() {
        return connection;
    }
}
