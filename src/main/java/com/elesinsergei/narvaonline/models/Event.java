package com.elesinsergei.narvaonline.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Model for blog event
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String title;
    private String content;
    private String status;
}
