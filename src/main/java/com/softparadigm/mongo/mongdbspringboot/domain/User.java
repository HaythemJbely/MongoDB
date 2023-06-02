package com.softparadigm.mongo.mongdbspringboot.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private String userId;
    private String name;
    private Long cin;
    private Long age;
    private boolean isValidated;
    private List<String> skills;
    private List<Address> addresses;

}
