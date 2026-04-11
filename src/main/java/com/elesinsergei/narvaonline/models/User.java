package com.elesinsergei.narvaonline.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @JsonProperty("roles")
    private String[] user_roles;
    private String status;
}
