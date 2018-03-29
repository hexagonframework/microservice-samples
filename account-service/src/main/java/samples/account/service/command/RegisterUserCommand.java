package samples.account.service.command;

import lombok.Data;

/**
 * @author Xuegui Yuan
 */
@Data
public class RegisterUserCommand {
  private String username;
  private String password;
}
