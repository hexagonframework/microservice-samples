package samples.account.controller;

import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xuegui Yuan
 */
@RestController
public class AccountController {

  /**
   * oauth2.hasScope('server'): CustomUserInfoTokenServices 通过user-info url获取scope信息
   * @param name
   * @return
   */
  @PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
  @RequestMapping(path = "/{name}", method = RequestMethod.GET)
  public String getAccountByName(@PathVariable String name) {
    return name;
  }

  @RequestMapping(path = "/current", method = RequestMethod.GET)
  public Principal getCurrentAccount(Principal principal) {
    return principal;
  }

}
