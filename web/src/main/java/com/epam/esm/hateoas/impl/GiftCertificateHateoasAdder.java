package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateHateoasAdder implements HateoasAdder<GiftCertificateDto> {
    private static final Class<GiftCertificateController> CONTROLLER = GiftCertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).giftCertificateById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).updateGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).createGiftCertificate(giftCertificateDto)).withRel("new"));
        giftCertificateDto.getTags().forEach(tagDto -> tagDto.add(linkTo(methodOn(TAG_CONTROLLER).tagById(tagDto.getId())).withSelfRel()));
    }
}
