package com.epam.esm.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends Identifiable {

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

    public Order() {
    }




    @PrePersist
    public void onPrePersist() {
        setPurchaseTime(LocalDateTime.now());
    }

    public Order(long id, BigDecimal price, LocalDateTime purchaseTime, User user, GiftCertificate giftCertificate) {
        super(id);
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order that = (Order) o;
        return user == that.user && giftCertificate == that.giftCertificate && Objects.equals(price, that.price)
                && Objects.equals(purchaseTime, that.purchaseTime) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price, purchaseTime, user, giftCertificate);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Order{");
        result.append("id=").append(super.getId());
        result.append(", price=").append(price);
        result.append(", purchaseTime=").append(purchaseTime);
        result.append(", user=").append(user);
        result.append(", giftCertificate=").append(giftCertificate);
        result.append('}');
        return result.toString();
    }
}
