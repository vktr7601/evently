package jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse implements Serializable {
    @JsonProperty("jwtToken")
    private String jwtToken;
    @JsonProperty("userRole")
    private String userRole;
}