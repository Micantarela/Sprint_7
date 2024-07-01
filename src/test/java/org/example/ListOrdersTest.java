package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ListOrdersTest {

    public static final String TRACK_FIELD = "track";

    @Test
    @DisplayName("Successful get orders list test")
    @Description("Успешное получение списка заказов")
    public void successfulGetOrdersListTest() {
        TestUtil.getOrdersList()
            .then()
            .statusCode(200)
            .and()
            .body(TRACK_FIELD, Matchers.not(Matchers.emptyArray()));
    }
}
