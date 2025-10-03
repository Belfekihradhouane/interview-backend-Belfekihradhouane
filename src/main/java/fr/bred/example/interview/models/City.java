package fr.bred.example.interview.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String name;
    private String zipCode;
    @JsonProperty("coordinates")
    private Point point;
}

