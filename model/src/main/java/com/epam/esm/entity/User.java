package com.epam.esm.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private long id;

    @Column(name = "name")

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        User that = (User) o;
//        return Objects.equals(name, that.name) && super.equals(that);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.hashCode(), name);
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder result = new StringBuilder("User{");
//        result.append("id=").append(this.getId());
//        result.append(", name='").append(name).append('\'');
//        result.append('}');
//        return result.toString();
//    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}
