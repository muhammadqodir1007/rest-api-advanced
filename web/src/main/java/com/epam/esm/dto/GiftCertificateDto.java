package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import com.epam.esm.validation.number.ValidPrice;
import com.epam.esm.validation.text.ValidDescription;
import com.epam.esm.validation.text.ValidName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    @ValidName
    private String name;
    @ValidDescription
    private String description;
    @ValidPrice
    private BigDecimal price;
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    private List<Tag> tags;

    public GiftCertificateDto(String name, String description, BigDecimal price, int duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
