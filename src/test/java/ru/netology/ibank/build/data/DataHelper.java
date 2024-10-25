package ru.netology.ibank.build.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {

    private DataHelper() {

    }

    public static UserInfo getUser() {
        return new UserInfo("vasya", "qwerty123");
    }

    public static String getVerificationCode() {
        return "12345";
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static String getMaskedNumber(String numberCard) {
        return "**** **** **** " + numberCard.substring(15);
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }

    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000) + 1;
    }

    @Value
    public static class CardInfo {
        String numberCard;
        String testId;
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
    }
}