package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangdingfei
 * @date 2023/8/14 22:57
 */
@Getter
@AllArgsConstructor
public enum MenuValueEnum {
    MY_FILE(0,"我的文件"),
    RECENT_CHANGE (1,"最近修改"),
    RECYLE_BIN ( 2,"回收站"),
    MY_COMMON_USE ( 3,"我的常用"),
    RECENT_OPEN ( 4,"最近打开"),
    MY_SPACE ( 5,"我的空间"),
    PUBLIC_SPACE (6,"公共空间"),
    OTHER_SPACE ( 7,"其他空间");

    int value;
    String name;
}
