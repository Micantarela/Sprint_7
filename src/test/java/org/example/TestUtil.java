package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.dto.CourierCreationDto;
import org.example.dto.CourierLoginDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.OrderCreationDto;

import static io.restassured.RestAssured.given;

public class TestUtil {

    public static final String STAND_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_URL = String.join("/", STAND_URL, "api/v1/courier");
    public static final String COURIER_LOGIN_URL = String.join("/", STAND_URL, "api/v1/courier/login");
    private static final String ORDER_URL = String.join("/", TestUtil.STAND_URL, "api/v1/orders");
    public static final String CONTENT_TYPE_HEADER = "Content-type";
    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final String LOGIN = "someUniqueLongLogin123467";
    public static final String PASSWORD = "someStrongPassword";
    public static final String FIRSTNAME = "Alexander";
    public static final String MESSAGE_FIELD = "message";

    @Step("Вызов запроса для создания курьера")
    public static Response createCourier(String login, String password, String firstName) {
        CourierCreationDto createDto = new CourierCreationDto(login, password, firstName);

        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(createDto)
            .when()
            .post(COURIER_URL);
    }

    @Step("Создание дефолтного курьера")
    public static void createDefaultCourier() {
        CourierCreationDto createDto = new CourierCreationDto(LOGIN, PASSWORD, FIRSTNAME);

        given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(createDto)
            .when()
            .post(COURIER_URL);
    }

    @Step("Вызов запроса для авторизации курьера")
    public static LoginResponseDto loginCourier(String login, String password) {
        CourierLoginDto loginDto = new CourierLoginDto(login, password);

        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(loginDto)
            .when()
            .post(COURIER_LOGIN_URL)
            .body().as(LoginResponseDto.class);
    }

    @Step("Вызов запроса для авторизации курьера")
    public static Response loginCourierWithResponse(String login, String password) {
        CourierLoginDto loginDto = new CourierLoginDto(login, password);

        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(loginDto)
            .when()
            .post(COURIER_LOGIN_URL);
    }

    @Step("Вызов запроса для удаления курьера")
    public static void deleteCourier(String courierId) {
        given()
            .auth().none()
            .when()
            .delete(String.join("/", COURIER_URL, courierId));
    }

    @Step("Вызов запроса для удаления курьера")
    public static void deleteCourier(String login, String password) {
        String id = loginCourier(login, password).getId();
        deleteCourier(id);
    }

    @Step("Удаление дефолтного курьера")
    public static void deleteDefaultCourier() {
        Response response = loginCourierWithResponse(LOGIN, PASSWORD);
        if (response.getStatusCode() == 200) {
            String id = response.as(LoginResponseDto.class).getId();
            deleteCourier(id);
        }
    }

    @Step("Вызов запроса для создания заказа")
    public static Response createOrder(OrderCreationDto orderCreationDto) {
        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(orderCreationDto)
            .when()
            .post(ORDER_URL);
    }

    public static Response getOrdersList() {
        return given()
            .auth().none()
            .when()
            .get(ORDER_URL);
    }
}
