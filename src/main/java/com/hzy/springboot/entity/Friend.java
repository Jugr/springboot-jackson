package com.hzy.springboot.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//@Setter @Getter,@ToString,@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friend {
    private String nickname;
    private int age;
}
