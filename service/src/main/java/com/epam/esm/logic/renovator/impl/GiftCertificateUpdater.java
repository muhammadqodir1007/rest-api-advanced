package com.epam.esm.logic.renovator.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.renovator.Updater;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GiftCertificateUpdater implements Updater<GiftCertificate> {

    @Override
    public GiftCertificate updateObject(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {
        String name = newGiftCertificate.getName();
        if (name != null) {
            oldGiftCertificate.setName(name);
        }

        String description = newGiftCertificate.getDescription();
        if (description != null) {
            oldGiftCertificate.setDescription(description);
        }

        BigDecimal price = newGiftCertificate.getPrice();
        if (price != null) {
            oldGiftCertificate.setPrice(price);
        }

        int duration = newGiftCertificate.getDuration();
        if (duration != 0) {
            oldGiftCertificate.setDuration(duration);
        }

        List<Tag> tags = newGiftCertificate.getTags();
        if (tags != null) {
            oldGiftCertificate.setTags(tags);
        }

        return oldGiftCertificate;
    }

    @Override
    public List<GiftCertificate> updateListFromDatabase(List<GiftCertificate> newListOfGiftCertificates) {
        throw new UnsupportedOperationException();
    }
}
