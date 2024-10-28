package com.wusuowei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentTypeEnum {

    ARTICLE(1, "文章", "/articles/"),

    MESSAGE(2, "留言", "/message/"),

    ABOUT(3, "关于我", "/about/"),

    LINK(4, "友链", "/friends/"),

    TALK(5, "说说", "/talks/");

    private final Integer type;

    private final String desc;

    private final String path;


    //获取评论路径
    public static String getCommentPath(Integer type) {
        //        遍历获取类型值
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
        //            判断类型值是否等于已有类型
            if (value.getType().equals(type)) {
                //相等返回路径
                return value.getPath();
            }
        }
        return null;
    }
    //获取评论枚举
    public static CommentTypeEnum getCommentEnum(Integer type) {
        //        遍历获取类型值
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            //            判断类型值是否等于已有类型
            if (value.getType().equals(type)) {
                //相等返回值
                return value;
            }
        }
        return null;
    }

}
