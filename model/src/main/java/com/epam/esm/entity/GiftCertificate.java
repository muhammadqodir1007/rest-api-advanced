package com.epam.esm.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "gift_certificates")
public class GiftCertificate extends Identifiable {

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    @Size(max = 45, min = 3)
    private String name;

    @Column(name = "description")
    @Size(max = 300)
    private String description;

    @Column(name = "price")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999.99")
    private BigDecimal price;

    @Column(name = "duration")
    @Min(value = 0)
    @Max(value = 366)
    private int duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "gift_certificates_has_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public GiftCertificate(long id, String name, String description, BigDecimal price, int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return price.compareTo(that.price) == 0 && duration == that.duration && Objects.equals(name, that.name)
                && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate) && super.equals(that)
                && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, createDate, lastUpdateDate, tags);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("GiftCertificate{");
        result.append("id=").append(super.getId());
        result.append(", name='").append(name).append('\'');
        result.append(", description='").append(description).append('\'');
        result.append(", price=").append(price);
        result.append(", duration=").append(duration);
        result.append(", createDate=").append(createDate);
        result.append(", lastUpdateDate=").append(lastUpdateDate);
        result.append(", tags=").append(tags);
        result.append('}');
        return result.toString();
    }
}
