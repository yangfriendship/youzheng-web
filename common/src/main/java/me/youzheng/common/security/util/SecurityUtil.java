package me.youzheng.common.security.util;

import me.youzheng.common.domain.MetaData;
import me.youzheng.common.security.domain.UserContext;

public interface SecurityUtil {

    UserContext getCurrentUser();

    Integer getUserPrimaryKey();

}