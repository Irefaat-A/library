package com.sos.models;

import com.sos.validators.annotations.ValidGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Accused {
    private String name;
    @ValidGender()
    private String gender;
    private String identity;
}
