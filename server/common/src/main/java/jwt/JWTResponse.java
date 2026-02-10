package jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTResponse {
    @JsonProperty("token")
    private String token;

    public JWTResponse(String token) {
        this.token = token;
    }
}