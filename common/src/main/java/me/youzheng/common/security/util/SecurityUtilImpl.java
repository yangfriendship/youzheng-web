package me.youzheng.common.security.util;

import me.youzheng.common.domain.MetaData;
import me.youzheng.common.security.domain.UserContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtilImpl implements SecurityUtil {

    @Override
    public UserContext getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserContext)) {
            return null;
        }
        UserContext result = (UserContext) principal;
        return result;
    }

    @Override
    public Integer getUserPrimaryKey() {
        UserContext currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        } else {
            return currentUser.getUserNo();
        }
    }

    @Override
    public boolean isOwner(Integer userNo) {
        Integer currentUserNo = getUserPrimaryKey();
        return currentUserNo != null && currentUserNo.equals(userNo);
    }

}