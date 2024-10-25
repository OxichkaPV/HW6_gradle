package ru.netology.ibank.build.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.build.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement actionTransfer = $("[data-test-id='action-transfer']");
    private final SelenideElement sumAmount = $("[data-test-id='amount'] input");
    private final SelenideElement inputFrom = $("[data-test-id='from'] input");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private final SelenideElement header = $(byText("Пополнение карты"));

    public TransferPage() {
        header.shouldBe(visible);
    }

    public DashboardPage successTransfer(String amount, DataHelper.CardInfo card) {
        transfer(amount, card);
        return new DashboardPage();
    }

    public void transfer(String amount, DataHelper.CardInfo card) {
        sumAmount.setValue(amount);
        inputFrom.setValue(card.getNumberCard());
        actionTransfer.click();
    }

    public void findErrorMessage() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
    }
}
