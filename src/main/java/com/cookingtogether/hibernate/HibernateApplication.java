package com.cookingtogether.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения для кулинарного блога.
 * Использует Spring Boot для запуска.
 */
@SpringBootApplication
public class HibernateApplication {

    /**
     * Главный метод, запускающий Spring Boot приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(HibernateApplication.class, args);
    }
}
