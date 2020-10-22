package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class Modelmages {

    @JsonPropertyDescription
    private MultipartFile fileInput;
}
