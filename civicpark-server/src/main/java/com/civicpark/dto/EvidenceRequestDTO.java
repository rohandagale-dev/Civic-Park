package com.civicpark.dto;

import com.civicpark.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The client provides the type of media and the file URL.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvidenceRequestDTO {

    private MediaType mediaType;
    private String mediaUrl;
    private String objectKey;
}
