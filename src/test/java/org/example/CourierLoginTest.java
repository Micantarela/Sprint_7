package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.example.TestUtil.LOGIN;
import static org.example.TestUtil.MESSAGE_FIELD;
import static org.example.TestUtil.PASSWORD;

public class CourierLoginTest {
    public static final String INVALID_PASSWORD = "gnfdbhigshfajesfbesi";
    public static final String INVALID_LOGIN = "gertmlhgoerspofknvbhrtuoigjernwfiuhoerwi";
    public static final String COURIER_NOT_FOUND_MESSAGE = "Учетная запись не найдена";
    public static final String INVALID_REQUEST_MESSAGE = "Недостаточно данных для входа";

    @BeforeClass
    public static void beforeClass() {
        TestUtil.deleteDefaultCourier();
        TestUtil.createDefaultCourier();
    }

    @AfterClass
    public static void afterClass() {
        TestUtil.deleteDefaultCourier();
    }

    @Test
    @DisplayName("Successful login test")
    @Description("Успешная авторизация")
    public void successfulLoginTest() {
        Response response = TestUtil.loginCourierWithResponse(LOGIN, PASSWORD);
        response.then()
            .statusCode(200)
            .and()
            .body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Failed login test with wrong login")
    @Description("Неуспешная авторизация с несуществующим логином")
    public void failedLoginTestWrongLogin() {
        Response response = TestUtil.loginCourierWithResponse(INVALID_LOGIN, PASSWORD);
        response.then()
            .statusCode(404)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(COURIER_NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Failed login test with wrong password")
    @Description("Неуспешная авторизация с неверным паролем")
    public void failedLoginTestWrongPassword() {
        Response response = TestUtil.loginCourierWithResponse(LOGIN, INVALID_PASSWORD);
        response.then()
            .statusCode(404)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(COURIER_NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Failed login test with null login")
    @Description("Неуспешная авторизация с пустым полем login")
    public void failedLoginTestLoginIsNull() {
        Response response = TestUtil.loginCourierWithResponse(null, PASSWORD);
        response.then()
            .statusCode(400)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(INVALID_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Failed login test with null password")
    @Description("Неуспешная авторизация с пустым полем password")
    public void failedLoginTestPasswordIsNull() {
        Response response = TestUtil.loginCourierWithResponse(LOGIN, null);
        response.then()
            .statusCode(400)
            .and()
            .body(MESSAGE_FIELD, Matchers.equalTo(INVALID_REQUEST_MESSAGE));
    }

}
