package com.hzy.springboot;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hzy.springboot.entity.Friend;
import com.hzy.springboot.entity.FriendDetail;
import com.hzy.springboot.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringbootJacksonApplicationTests {

    @Test
    void contextLoads() throws IOException {
//        test1();
        test2();
    }


    //简单变量的读和写writeValueAsString  readValue
    @Test
    public void test1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Friend friend1 = Friend.builder().nickname("张三").age(12).build();
        Friend friend = new Friend("yitian", 25);

        // 写为字符串
        String text = mapper.writeValueAsString(friend);
        // 写为文件
        mapper.writeValue(new File("friend.json"), friend);
        // 写为字节流
        byte[] bytes = mapper.writeValueAsBytes(friend);
        System.out.println(text);
        // 从字符串中读取
        Friend newFriend = mapper.readValue(text, Friend.class);
        // 从字节流中读取
        newFriend = mapper.readValue(bytes, Friend.class);
        // 从文件中读取
        newFriend = mapper.readValue(new File("friend.json"), Friend.class);
        System.out.println(newFriend);
    }

    //Map的写 和  读某一个值
    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        Map<String, Object> map = new HashMap<>();
        map.put("age", 25);
        map.put("name", "yitian");
        map.put("interests", new String[]{"pc games", "music"});

        String text = mapper.writeValueAsString(map);
        System.out.println(text);


//     从JSON转换为Map对象的时候，由于Java的类型擦除，所以类型需要手动用new TypeReference<Map<T1,T2>>(){}给出
        Map<String, Object> map2 = mapper.readValue(text, new TypeReference<Map<String, Object>>() {
        });

        System.out.println(map2);


//        可以使用readTree方法直接读取JSON中的某个属性值。
        JsonNode jsonNode = mapper.readTree(text);
        int age = jsonNode.get("age").asInt();
        String name = jsonNode.get("name").asText();
        System.out.println("name:" + name + " age:" + age);

    }

    public void test3() {
        ObjectMapper mapper = new ObjectMapper();
        // 美化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 允许序列化空的POJO类
        // （否则会抛出异常）
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 把java.util.Date, Calendar输出为数字（时间戳）
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 在遇到未知属性的时候不抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 强制JSON 空字符串("")转换为null对象值:
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 在JSON中允许C/C++ 样式的注释(非标准，默认禁用)
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 允许没有引号的字段名（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 强制转义非ASCII字符
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        // 将内容包裹为一个JSON属性，属性名由@JsonRootName注解指定
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }

    //不完全匹配
    @Test
    public void test4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);


//        设置了排除的属性，所以生成的JSON和Java类并不是完全对应关系
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        FriendDetail fd = new FriendDetail("yitian", 25, "", 0, "");
        String text = mapper.writeValueAsString(fd);
        System.out.println(text);

        FriendDetail fd2 = mapper.readValue(text, FriendDetail.class);
        System.out.println(fd2);
//
//        {"NickName":"yitian","Age":25}
//        FriendDetail(name=yitian, age=25, uselessProp1=null, uselessProp2=0, uselessProp3=null)
    }

    //日期类
    @Test
    public void test5() throws JsonProcessingException {
        Person p1 = new Person("yitian", "易天", 25, "10000", LocalDate.of(1994, 1, 1));


//        自动搜索所有日期模块，不需要我们手动注册。
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());


        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String text = mapper.writeValueAsString(p1);
        System.out.println(text);

        Person p2 = mapper.readValue(text, Person.class);
        System.out.println(p2);

        /*
        {"birthday":[1994,1,1],"Name":"yitian","NickName":"易天","Age":25,"IdentityCode":"10000"}
        Person(name=yitian, nickname=易天, age=25, identityCode=10000, birthday=1994-01-01)
        */

        /*
        //不使用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //指定日期格式
        mapper.setDateFormat(sdf);
        */
    }

}
