package com.sparta.camp.java.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  private String secret;
  private long accessTokenExpirationMillis;
  private long refreshTokenExpirationMillis;

  public long getAccessTokenExpirationMillis() { return accessTokenExpirationMillis; }
  public void setAccessTokenExpirationMillis(long v) { this.accessTokenExpirationMillis = v; }
  public long getRefreshTokenExpirationMillis() { return refreshTokenExpirationMillis; }
  public void setRefreshTokenExpirationMillis(long v) { this.refreshTokenExpirationMillis = v; }

}
