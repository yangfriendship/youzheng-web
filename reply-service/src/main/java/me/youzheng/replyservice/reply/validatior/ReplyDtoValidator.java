package me.youzheng.replyservice.reply.validatior;

import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReplyDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ReplyDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReplyDto replyDto = (ReplyDto) target;
        if (!StringUtils.hasLength(replyDto.getContent())) {
            errors.rejectValue("content", "", "내용은 꼭 입력해주세요.");
        }
    }

}