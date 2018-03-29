package samples.account.service;

import javax.validation.Valid;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import samples.account.service.command.RegisterUserCommand;

/**
 * @author Xuegui Yuan
 */
@FeignClient(name = "uaa", url = "${uaa-service.url}")
public interface UaaApiClient {
  /**
   * 创建新用户
   * @param user
   */
  @RequestMapping(value = "/users", method = RequestMethod.POST)
  void createUser(@Valid @RequestBody RegisterUserCommand user);
}
