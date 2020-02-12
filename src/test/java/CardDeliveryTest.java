import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    SelenideElement heading = $("#root"); //заголовок
    SelenideElement city = $("[data-test-id ='city'] input"); //  поле ввода города
    SelenideElement lastNameAndName = $("[data-test-id ='name'] input"); //поле ввода фамилии и имени
    SelenideElement number = $("[data-test-id ='phone'] input"); //поле ввода сот. мобильный
    SelenideElement date = $("[placeholder = 'Дата встречи'] "); //поле дата
    SelenideElement checkMark = $("[data-test-id=agreement]");//поле чек-бокс
    SelenideElement button = $("span.button__text");//кнопка
    SelenideElement succsesNotif = $("[data-test-id='success-notification']");//успешно заполнена
    SelenideElement repeatNotif = $("[data-test-id='replan-notification']"); //дата измненена
    SelenideElement repeatButton = $(byText("Перепланировать")); //"div.notification__content > button > span > span.button__text]");// копка поддверждения


    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReservationCard() throws InterruptedException {
        String futureDay = Generate.getFutureDate(4);
        RegistrationForData registrationForData = Generate.generateByCard("ru");
        heading.isDisplayed();
        city.setValue(registrationForData.getCity());
        lastNameAndName.setValue(registrationForData.getFullName());
        number.setValue(registrationForData.getMobilNumber());
        checkMark.click();
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        date.setValue(futureDay);
        button.click();
        succsesNotif.waitUntil(Condition.visible, 15000);
        succsesNotif.shouldHave(text("Успешно!"));
        succsesNotif.shouldHave(text("Встреча успешно запланирована на " + futureDay));

        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        String newFutureDay = Generate.getFutureDate(8);
        date.setValue(newFutureDay);
        button.click();
        repeatNotif.waitUntil(Condition.visible, 15000);
        repeatNotif.shouldHave(text("Необходимо подтверждение"));
        repeatButton.click();
        repeatNotif.waitUntil(Condition.exist, 15000);
        succsesNotif.shouldHave(text("Встреча успешно запланирована на " + newFutureDay));
    }
}