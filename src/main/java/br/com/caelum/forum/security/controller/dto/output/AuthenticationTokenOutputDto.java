package br.com.caelum.forum.security.controller.dto.output;

public class AuthenticationTokenOutputDto {
    private String tokenType;
    private String token;

    public AuthenticationTokenOutputDto(String tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }
}
