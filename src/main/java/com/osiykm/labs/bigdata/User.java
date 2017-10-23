package com.osiykm.labs.bigdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

/***
 * @author osiykm
 * created 22.10.2017 23:21
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
class User implements Identifiable<String> {
    @Id
    private String id;

    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    private String language;
    private String email;
}
