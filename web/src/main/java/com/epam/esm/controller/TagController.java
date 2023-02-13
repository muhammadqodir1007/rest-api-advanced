package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    private final DtoConverter<Tag, TagDto> tagDtoConverter;
    private final HateoasAdder<TagDto> hateoasAdder;

    @Autowired
    public TagController(TagService tagService, DtoConverter<Tag, TagDto> tagDtoConverter,
                         HateoasAdder<TagDto> hateoasAdder) {
        this.tagService = tagService;
        this.tagDtoConverter = tagDtoConverter;
        this.hateoasAdder = hateoasAdder;
    }

    /**
     * Method for getting tag by ID.
     *
     * @param id ID of tag to get
     * @return Found tag entity with hateoas
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto tagById(@PathVariable("id") long id) {
        Tag tag = tagService.getById(id);

        TagDto tagDto = tagDtoConverter.convertToDto(tag);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    /**
     * Method for removing tag by ID.
     *
     * @param id ID of tag to remove
     * @return NO_CONTENT HttpStatus
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for saving new tag.
     *
     * @param tag tag for saving
     * @return created tag with hateoas
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tag) {
        Tag addedTag = tagService.insert(tagDtoConverter.convertToEntity(tag));

        TagDto tagDto = tagDtoConverter.convertToDto(addedTag);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    /**
     * Method for getting list of tags from data source by special filter.
     *  If there are no filters, then it returns all tags.
     *
     * @param allRequestParams request parameters, which include the information needed for the search
     * @return List of found tags with hateoas
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> tagsByParameter(@RequestParam MultiValueMap<String, String> allRequestParams,
                                        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                        @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Tag> tags = tagService.doFilter(allRequestParams, page, size);

        return tags.stream()
                .map(tagDtoConverter::convertToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Method for getting most popular tag if user with the highest cost of all orders.
     *
     * @return Found tag entity with hateoas
     */
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public TagDto mostPopularTagOfUserWithHighestCostOfAllOrders() {
        Tag tag = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();

        TagDto tagDto = tagDtoConverter.convertToDto(tag);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }
}
