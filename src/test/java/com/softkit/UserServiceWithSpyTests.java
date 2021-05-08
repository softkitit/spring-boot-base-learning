package com.softkit;

import com.softkit.exception.CustomException;
import com.softkit.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {StarterApplication.class})
public class UserServiceWithSpyTests {

    @Autowired
    private UserService userService;

    //    spying is keeping the existing functionality but allow to intercept method calls or track count of invocations of this method
    //    useful for checking that cache/optimisations working properly
    @SpyBean
    private AuthenticationManager authenticationManager;

    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> authDataCaptor;


    @Test
    public void checkThatWithSpyingWeCanVerifyALotOfMetrics() {

        String username = "username";
        String password = "password";

//      intercepting the call of this method to get an accepted argument


        int executionCount = 3;

        for (int i = 0; i < executionCount; i++) {

            try {
//        username must be the same, because of our above rule for mocking
                userService.signin(username, password);

            } catch (CustomException e) {

                assertThat(e.getMessage()).isEqualTo("Invalid username/password supplied");
                assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        Mockito.verify(authenticationManager, Mockito.times(3)).authenticate(authDataCaptor.capture());

        Mockito.verifyNoMoreInteractions(authenticationManager);
        long neededUsernameCount = authDataCaptor.getAllValues().stream().filter(v -> v.equals(new UsernamePasswordAuthenticationToken(username, password))).count();
//        number of executions equals to number of values in capture
        assertThat(neededUsernameCount).isEqualTo(executionCount);

    }
}
