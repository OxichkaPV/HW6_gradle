package ru.netology.ibank.build.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.build.data.DataHelper;
import ru.netology.ibank.build.page.DashboardPage;
import ru.netology.ibank.build.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {
    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCard;
    DataHelper.CardInfo secondCard;
    int balanceFirstCard;
    int balanceSecondCard;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var userInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(userInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCard = DataHelper.getFirstCard();
        secondCard = DataHelper.getSecondCard();
        balanceFirstCard = dashboardPage.getCardBalance(0);
        balanceSecondCard = dashboardPage.getCardBalance(1);
    }

    @Test
    void transferFromFirstToSecond() {
        var amount = DataHelper.generateValidAmount(balanceFirstCard);
        var expectedBalanceFirstCard = balanceFirstCard - amount;
        var expectedBalanceSecondCard = balanceSecondCard + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCard);
        dashboardPage = transferPage.successTransfer(String.valueOf(amount), firstCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard));
    }

    @Test
    void transferFromSecondToFirst() {
        var amount = DataHelper.generateValidAmount(balanceSecondCard);
        var expectedBalanceSecondCard = balanceSecondCard - amount;
        var expectedBalanceFirstCard = balanceFirstCard + amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCard);
        dashboardPage = transferPage.successTransfer(String.valueOf(amount), secondCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard));
    }

    @Test
    void errorTransferIfAmountMoreBalance() {
        var amount = DataHelper.generateInvalidAmount(balanceSecondCard);
        var transferPage = dashboardPage.selectCardToTransfer(firstCard);
        transferPage.successTransfer(String.valueOf(amount), secondCard);
        transferPage.findErrorMessage();
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(balanceFirstCard, actualBalanceFirstCard),
                () -> assertEquals(balanceSecondCard, actualBalanceSecondCard));
    }
}
