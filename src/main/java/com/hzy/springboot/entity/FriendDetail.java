package com.hzy.springboot.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 由于设置了排除的属性，
*  所以生成的JSON和Java类并不是完全对应关系，
*  所以禁用DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES是必要的。
* */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("FriendDetail")//用于指定JSON根属性的名称。
@JsonIgnoreProperties({"uselessProp1", "uselessProp3"})
public class FriendDetail {

    @JsonProperty("NickName")//指定一个属性用于JSON映射，默认情况下映射的JSON属性与注解的属性名称相同
    private String name;

    @JsonProperty("Age")
    private int age;

    private String uselessProp1;

    @JsonIgnore//注解用于排除某个属性，这样该属性就不会被Jackson序列化和反序列化。
    private int uselessProp2;

    private String uselessProp3;
}