package com.epam.esm.logic.renovator.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.renovator.Updater;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificateUpdater implements Updater<GiftCertificate> {

    @Override
    public GiftCertificate updateObject(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {
        String name = newGiftCertificate.getName();
        if (!Objects.isNull(name)) {
            oldGiftCertificate.setName(name);
        }

        String description = newGiftCertificate.getDescription();
        if (!Objects.isNull(description)) {
            oldGiftCertificate.setDescription(description);
        }

        BigDecimal price = newGiftCertificate.getPrice();
        if (!Objects.isNull(price)) {
            oldGiftCertificate.setPrice(price);
        }

        int duration = newGiftCertificate.getDuration();
        if (duration != 0) {
            oldGiftCertificate.setDuration(duration);
        }

        List<Tag> tags = newGiftCertificate.getTags();
        if (!Objects.isNull(tags)) {
            oldGiftCertificate.setTags(tags);
        }

        return oldGiftCertificate;
    }

    @Override
    public List<GiftCertificate> updateListFromDatabase(List<GiftCertificate> newListOfGiftCertificates) {
        throw new UnsupportedOperationException();
    }
}
