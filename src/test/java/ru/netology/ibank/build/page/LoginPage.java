package ru.netology.ibank.build.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.build.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id='login'] input");
    private final SelenideElement password = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");

    public VerificationPage validLogin(DataHelper.UserInfo user) {
        login.setValue(user.getLogin());
        password.setValue(user.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
