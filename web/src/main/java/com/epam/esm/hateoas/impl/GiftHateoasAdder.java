

package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftHateoasAdder implements HateoasAdder<GiftCertificateDto> {
    private static final Class<GiftController> GIFT_CONTROLLER_CLASS = GiftController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GIFT_CONTROLLER_CLASS).getGiftCertificate(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GIFT_CONTROLLER_CLASS).updateGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GIFT_CONTROLLER_CLASS).deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(GIFT_CONTROLLER_CLASS).createGiftCertificate(giftCertificateDto)).withRel("new"));
        giftCertificateDto.getTags().forEach(tagDto -> tagDto.add(linkTo(methodOn(TAG_CONTROLLER).getTag(tagDto.getId())).withSelfRel()));
    }
}