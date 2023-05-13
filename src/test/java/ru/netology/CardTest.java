package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    @BeforeEach
    public void openPage() {
        WebDriverManager.chromedriver().setup();
        webdriver().driver().browser().isChrome();
        open("http://localhost:9999/");
    }

    @AfterEach
    public void endTest() {
        closeWebDriver();
    }

    @Test
    void shouldTestCorrectForm() {
        $x("//input[@placeholder=\"Город\"]").val("Майкоп");
        String planingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(planingDate);
        $("[data-test-id='name'] input").val("Крылова Ольга");
        $("[data-test-id='phone'] input").val("+79883112223");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + planingDate));
    }
}
