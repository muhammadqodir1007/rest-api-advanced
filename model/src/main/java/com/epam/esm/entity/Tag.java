package com.epam.esm.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Getter
@Setter
@RequiredArgsConstructor

@Table(name = "tags")
public class Tag extends Identifiable {


    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tag that = (Tag) o;
        return Objects.equals(name, that.name) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Tag{");
        result.append("id=").append(super.getId());
        result.append(", name='").append(name).append('\'');
        result.append('}');
        return result.toString();
    }
}
