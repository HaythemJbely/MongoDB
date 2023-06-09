package com.softparadigm.mongo.mongdbspringboot.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    @Id
    private String id;

    private ERole name;
}
