package ge.mziuri.emarket.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.credentials")
public class AppCredentialsProperties {

    private String username;
    private String password;

}

