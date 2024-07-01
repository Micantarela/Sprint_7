package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.example.TestUtil.FIRSTNAME;
import static org.example.TestUtil.LOGIN;
import static org.example.TestUtil.MESSAGE_FIELD;
import static org.example.TestUtil.PASSWORD;

public class CourierCreateTest {

    public static final String CONFLICT_MESSAGE_CONTENT = "Этот логин уже используется. Попробуйте другой.";
    public static final String INVALID_MESSAGE_CONTENT = "Недостаточно данных для создания учетной записи";

    @BeforeClass
    public static void beforeClass() {
        TestUtil.deleteDefaultCourier();
    }

    @AfterClass
    public static void afterClass() {
        TestUtil.deleteDefaultCourier();
    }

    @Test
    @DisplayName("Successful creation test")
    @Description("Успешное создание курьера")
    public void successfulCreationTest() {
        Response response = TestUtil.createCourier(LOGIN, PASSWORD, FIRSTNAME);
        response.then()
            .statusCode(201)
            .and()
            .body("ok", Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Failed creation test with null login field")
    @Description("Неуспешное создание курьера с пустым полем login")
    public void failedCreationTest_LoginIsNull() {
        Response response = TestUtil.createCourier(null, PASSWORD, FIRSTNAME);
        response.then()
            .statusCode(400)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(INVALID_MESSAGE_CONTENT));
    }

    @Test
    @DisplayName("Failed creation test with null password field")
    @Description("Неуспешное создание курьера с пустым полем password")
    public void failedCreationTest_PasswordIsNull() {
        Response response = TestUtil.createCourier(LOGIN, null, FIRSTNAME);
        response.then()
            .statusCode(400)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(INVALID_MESSAGE_CONTENT));
    }

    @Test
    @DisplayName("Failed duplication creation test")
    @Description("Неуспешное создание курьера с уже существующим логином")
    public void failedDuplicateCreationTest() {
        Response firstResponse = TestUtil.createCourier(LOGIN, PASSWORD, FIRSTNAME);
        firstResponse.then()
            .statusCode(201)
            .and()
            .body("ok", Matchers.equalTo(true));

        Response secondResponse = TestUtil.createCourier(LOGIN, PASSWORD, FIRSTNAME);
        secondResponse.then()
            .statusCode(409)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(CONFLICT_MESSAGE_CONTENT));

        TestUtil.deleteCourier(LOGIN, PASSWORD);
    }

}
