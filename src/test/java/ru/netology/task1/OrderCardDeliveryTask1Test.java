package ru.netology.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCardDeliveryTask1Test {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");//Открываем страницу с заказом карты

    }

    @Test
    public void shouldInputValidValue() {

        //Вводим в поле название города
        $("[data-test-id=city] input").setValue("Уфа");

        //Очищаем поле ввода даты встречи с представителем банка
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");

        //Определяем необходимую дату встречи с представителем банка путём добавления к текущей дате
        //нескольких дней (от трёх и более)
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        //Вводим в поле дату встречи с представителем банка, определённую выше
        $("[data-test-id=date] input").setValue(meetingData);

        //Вводим в поле фамилию и имя
        $("[data-test-id=name] input").setValue("Иванов Сергей");

        //вводим в поле номер телефона
        $("[data-test-id=phone] input").setValue("+79789991100");

        //Выставляем флаг согласия использования персональных данных
        $("[data-test-id=agreement]").click();

        //Нажимаем кнопку "Забронировать"
        $(withText("Забронировать")).click();

        //Дожидаемся надписи "Успешно!" в течение 15 сек
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));

        //Проверяем появление записи "Встреча успешно забронирована на ... дату"
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча " +
                "успешно забронирована на " + meetingData));
    }

    @Test
    public void shouldInputInvalidCity() {

        $("[data-test-id=city] input").setValue("Кряк");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный " +
                "город недоступна"));
    }

    @Test
    public void shouldInputFieldCityEmpty() {

        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Поле обязательно для " +
                "заполнения"));
    }

    @Test
    public void shouldInputInvalidDate() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Заказ на выбранную дату " +
                "невозможен"));
    }

    @Test
    public void shouldInputFieldDateEmpty() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    public void shouldInputInvalidNameEng() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Ivanov Sergey");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные " +
                "неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldInputFieldNameEmpty() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно " +
                "для заполнения"));
    }

    @Test
    public void shouldInputInvalidTel() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+7978");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан " +
                "неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldInputFieldTelEmpty() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для " +
                "заполнения"));
    }

    @Test
    public void shouldInputNoAgreement() {

        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys("BACK_SPACE");
        String meetingData = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(meetingData);
        $("[data-test-id=name] input").setValue("Иванов Сергей");
        $("[data-test-id=phone] input").setValue("+79789991100");
        $(withText("Забронировать")).click();
        $("[data-test-id=agreement] .checkbox__text").shouldHave(exactText("Я соглашаюсь с " +
                "условиями обработки и использования моих персональных данных"));
    }
}
