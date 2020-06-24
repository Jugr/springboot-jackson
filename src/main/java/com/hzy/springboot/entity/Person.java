package com.hzy.springboot.entity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("Person")
@Builder
public class Person {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NickName")
    private String nickname;
    @JsonProperty("Age")
    private int age;
    @JsonProperty("IdentityCode")
    private String identityCode;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-DD")
    private LocalDate birthday;

}