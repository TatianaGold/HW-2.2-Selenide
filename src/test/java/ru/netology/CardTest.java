package ru.netology;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldTestCorrectForm() {
        $x("//input[@placeholder=\"Город\"]").val("Майкоп");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.BACK_SPACE,Keys.HOME,Keys.SHIFT));
        String planingDate = generateDate(4, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(planingDate);
        $("[data-test-id='name'] input").val("Крылова Ольга");
        $("[data-test-id='phone'] input").val("+79883112223");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + planingDate));
    }
}
