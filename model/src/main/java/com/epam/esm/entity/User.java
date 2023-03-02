package com.epam.esm.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User extends Identifiable {

    @Column(name = "name")

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User(long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return Objects.equals(name, that.name) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("User{");
        result.append("id=").append(super.getId());
        result.append(", name='").append(name).append('\'');
        result.append('}');
        return result.toString();
    }
}
