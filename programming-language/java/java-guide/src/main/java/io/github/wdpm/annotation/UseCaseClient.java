package io.github.wdpm.annotation;

/**
 * UseCase 注解使用示例
 *
 * @author evan
 * @date 2020/5/1
 */
public class UseCaseClient {
    @UseCase(id = 1, description = "Passwords must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }
}
