package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoasAdder implements HateoasAdder<TagDto> {
    private static final Class<TagController> TAG_CONTROLLER_CLASS = TagController.class;

    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER_CLASS).getTag(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER_CLASS).deleteTag(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER_CLASS).createTag(tagDto)).withRel("new"));
    }
}
