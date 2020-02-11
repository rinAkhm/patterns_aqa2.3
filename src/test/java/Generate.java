import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Generate {

    public Generate() {
    }

    public static RegistrationForData generateByCard(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationForData(faker.address().city(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber());
    }
    
    public static String getFutureDate(int plusDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate currentDate = LocalDate.now();
        LocalDate controlDate = currentDate.plusDays(plusDate);
        String formattedControlDate = controlDate.format(format);
        return formattedControlDate;
    }

}



