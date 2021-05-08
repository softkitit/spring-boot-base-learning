package com.softkit;

import com.softkit.exception.CustomException;
import com.softkit.model.User;
import com.softkit.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * This class is testing the service itself
 * and as you can see on the service level we don't have such strict rules (email, password validation, etc... )
 * so all interactions with the application must be through HTTP layer, that's very important to keep data clean
 */

@SpringBootTest(classes = {StarterApplication.class})
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void successUserSignupTest() {
        String signupToken1 = userService.signup(new User(null, "test", "test", "test",
                Lists.newArrayList()));

        assertThat(signupToken1).isNotBlank();

        try {
            userService.signup(new User(null, "test", "test", "test",
                    Lists.newArrayList()));
        } catch (CustomException e) {
            assertThat(e.getMessage()).isEqualTo("Username is already in use");
            assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
