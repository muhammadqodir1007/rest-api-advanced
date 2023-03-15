package com.epam.esm.controller;

import com.epam.esm.config.MessageByLang;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.impl.TagHateoasAdder;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    private final DtoConverter<Tag, TagDto> tagDtoConverter;
    private final TagHateoasAdder hateoasAdder;

    /**
     * Retrieve a tag by its ID.
     *
     * @param id the ID of the tag to retrieve
     * @return the tag matching the given ID
     */

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable long id) {
        Tag tag = tagService.getById(id);
        TagDto tagDto = tagDtoConverter.convertToDto(tag);
        hateoasAdder.addLinks(tagDto);
        return ResponseEntity.ok(tagDto);
    }

    /**
     * Delete a tag by its ID.
     *
     * @param id the ID of the tag to delete
     * @return a 204 No Content response
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteTag(@PathVariable long id) {
        tagService.removeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageByLang.toLocale("successfully.deleted"));
    }

    /**
     * Create a new tag.
     *
     * @param tagDto the tag to create
     * @return a 201 Created response with the created tag
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {
        Tag tag = tagService.insert(tagDtoConverter.convertToEntity(tagDto));
        TagDto createdTagDto = tagDtoConverter.convertToDto(tag);
        hateoasAdder.addLinks(createdTagDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTagDto);
    }

    /**
     * Search for tags based on the given parameters.
     *
     * @param params a map of query parameters to search for tags
     * @param page   the page number to retrieve (default: 0)
     * @param size   the number of items per page (default: 5)
     * @return a list of tags matching the given search parameters
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> searchTags(@RequestParam MultiValueMap<String, String> params,
                                   @RequestParam(defaultValue = "0", required = false) int page,
                                   @RequestParam(defaultValue = "5", required = false) int size) {
        List<Tag> tags = tagService.search(params, page, size);
        return tags.stream().map(tagDtoConverter::convertToDto)
                .peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }

    @GetMapping("/popular-tag")
    public ResponseEntity<TagDto> getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        Tag tag = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        TagDto tagDto = tagDtoConverter.convertToDto(tag);
        hateoasAdder.addLinks(tagDto);
        return ResponseEntity.ok(tagDto);
    }
}
