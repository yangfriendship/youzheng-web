package me.youzheng.userservice.user.validator;

import static me.youzheng.common.constants.RexExpressions.EMAIL_REG_EXPRESSION;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import me.youzheng.userservice.user.domain.dto.UserCreateDto;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class UserCreateValidator implements Validator {

    public final Pattern pattern = Pattern.compile(EMAIL_REG_EXPRESSION);

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDto createDto = (UserCreateDto) target;
        if (!StringUtils.hasLength(createDto.getLoginId()) || createDto.getLoginId().length() < 5) {
            errors.rejectValue("loginId", "loginId.length");
        }
        if (!StringUtils.hasLength(createDto.getEmail()) || !pattern.matcher(createDto.getEmail())
            .matches()) {
            errors.rejectValue("email", "email.invalid");
        }
        // TODO Password 검증 나중에 추가

        if (!StringUtils.hasLength(createDto.getUserName())
            || createDto.getUserName().length() < 2) {
            errors.rejectValue("userName", "userName.length");
        }
    }

}