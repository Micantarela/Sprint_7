package org.example.dto;

public class LoginResponseDto {
    private String id;

    public LoginResponseDto(String id) {
        this.id = id;
    }

    public LoginResponseDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
