package com.epam.esm.dto;

import com.epam.esm.validation.number.ValidPrice;
import com.epam.esm.validation.text.ValidName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    @ValidName
    private String name;
}
