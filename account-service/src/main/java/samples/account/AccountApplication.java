package samples.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import samples.account.security.CustomUserInfoTokenServices;

/**
 * @author Xuegui Yuan
 */
@SpringBootApplication
@EnableResourceServer
@EnableOAuth2Client
@EnableConfigurationProperties
@EnableFeignClients
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccountApplication extends ResourceServerConfigurerAdapter {
  @Autowired
  private ResourceServerProperties sso;

  @Bean
  OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ProtectedResourceDetails oauth2RemoteResource,
                                                              OAuth2ClientContext oauth2ClientContext) {
    return new OAuth2FeignRequestInterceptor(oauth2ClientContext, oauth2RemoteResource);
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  public OAuth2RestTemplate clientCredentialsRestTemplate() {
    return new OAuth2RestTemplate(clientCredentialsResourceDetails());
  }

  @Bean
  public ResourceServerTokenServices tokenServices() {
    return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/" , "/demo", "/register").permitAll()
        .anyRequest().authenticated();
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountApplication.class, args);
  }
}
