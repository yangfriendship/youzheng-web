package me.youzheng.boardservice.board.validate;

import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.dto.BoardCreateDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class BoardCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardCreateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardCreateDto createDto = (BoardCreateDto) target;
        if (createDto.getTitle() == null || createDto.getTitle().length() < 2) {
            errors.rejectValue("title", "", "제목은 2글자 이상 입력해주세요.");
        }
        if (createDto.getContent() == null || createDto.getContent().length() < 2) {
            errors.rejectValue("content", "", "제목은 2글자 이상 입력해주세요.");
        }
    }

}