package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.DataGenerator.Registration.generateDate;
import static ru.netology.web.DataGenerator.Registration.generateUser;

public class CardTest {
    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    @Test
    public void shouldSendFormValid() {

        UserInfo user = generateUser();

        $("[data-test-id='city'] input").val(user.getCity());
        $("[data-test-id='date'] input[class='input__control']").
                sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").setValue(generateDate(5));
        $("[data-test-id='name'] input").val(user.getName());
        $("[data-test-id='phone'] input").val(user.getPhone());
        $x(".//label[@data-test-id='agreement']").click();
        $(byText("Запланировать")).click();

        $("[data-test-id='success-notification'] div[class='notification__content']").
                shouldBe(visible, Duration.ofSeconds(15)).should(text("Встреча успешно запланирована на " +
                        generateDate(5)));
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] input[class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input[class='input__control']").setValue(generateDate(8));
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification']"). shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification'] div[class='notification__content']").
                shouldBe(visible, Duration.ofSeconds(15)).should(text("Встреча успешно запланирована на " +
                        generateDate(8)));
    }

}
