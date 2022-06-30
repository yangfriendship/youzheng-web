package me.youzheng.userservice.user.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.youzheng.userservice.test.EntityFactory;
import me.youzheng.userservice.user.domain.dto.UserCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

@ExtendWith(MockitoExtension.class)
public class UserCreateValidatorTest {

    UserCreateValidator validator;
    UserCreateDto createDto;
    @Mock
    Errors errors;

    @BeforeEach
    public void init() {
        this.validator = new UserCreateValidator();
        this.createDto = EntityFactory.createUserCreateDto();

    }

    @DisplayName("UserCreateValidator.support - UserCreateDto 만 검증할 수 있다.")
    @Test
    public void support_success() {
        assertFalse(this.validator.supports(Object.class));
        assertTrue(this.validator.supports(UserCreateDto.class));
    }

    @Test
    public void validateTest() {
        // given
        Map<String, Object> map = new HashMap<>();
        MapBindingResult bindingResult = new MapBindingResult(map, "objectName");

        // when
        this.validator.validate(this.createDto, bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    public void validateTest_hasErrors() {
        UserCreateDto invalidDto = UserCreateDto.builder()
            .loginId("abcd") // >=5
            .email("woojungnaber.com") //
            .userName("s") // >= 2
            .build();
        // given
        Map<String, Object> map = new HashMap<>();
        MapBindingResult bindingResult = new MapBindingResult(map, "objectName");

        // when
        this.validator.validate(invalidDto, bindingResult);
        assertTrue(bindingResult.hasErrors());

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        assertEquals(3, allErrors.size());
    }

}