package org.example;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.dto.OrderCreationDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Set;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private static final String BLACK = "BLACK";
    private static final String GREY = "GREY";
    private static final String TRACK_FIELD = "track";

    private final Set<String> color;
    private static final Faker faker = new Faker();

    public OrderCreationTest(Set<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] parameters() {
        return new Object[]{
            Set.of(),
            Set.of(BLACK),
            Set.of(GREY),
            Set.of(GREY, BLACK),
            null
        };
    }

    @Test
    @DisplayName("Successful order creation test")
    @Description("Успешное создание заказа")
    public void successfulOrderCreationTest() {
        OrderCreationDto orderCreationDto = getDefaultOrderCreationDto();
        orderCreationDto.setColor(color);

        TestUtil.createOrder(orderCreationDto)
            .then()
            .statusCode(201)
            .and()
            .body(TRACK_FIELD, Matchers.notNullValue());
    }

    private OrderCreationDto getDefaultOrderCreationDto() {
        return new OrderCreationDto(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().streetAddress(),
                faker.address().city(),
                faker.phoneNumber().phoneNumber(),
                faker.random().nextInt(10),
                LocalDate.now().toString(),
                faker.backToTheFuture().quote(),
                null);
    }

}