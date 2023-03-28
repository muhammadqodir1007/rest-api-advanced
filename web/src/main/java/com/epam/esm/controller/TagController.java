package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.hateoas.impl.TagHateoasAdder;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagHateoasAdder hateoasAdder;

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable long id) {
        TagDto tagDto = tagService.getById(id);
        hateoasAdder.addLinks(tagDto);
        return ResponseEntity.ok(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable long id) {
        ApiResponse apiResponse = tagService.removeById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {
        TagDto createdTag = tagService.insert(tagDto);
        hateoasAdder.addLinks(createdTag);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    } @PatchMapping ("/{id}")
    public ResponseEntity<TagDto> updateTag( @PathVariable long id ,@Valid @RequestBody TagDto tagDto) {
        TagDto updatedTag = tagService.update(id,tagDto);
        hateoasAdder.addLinks(updatedTag);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedTag);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> searchTags(@RequestParam MultiValueMap<String, String> params,
                                   @RequestParam(defaultValue = "0", required = false) int page,
                                   @RequestParam(defaultValue = "5", required = false) int size) {
        List<TagDto> searchResults = tagService.search(params, page, size);
        searchResults.forEach(hateoasAdder::addLinks);
        return searchResults;
    }

    @GetMapping("/popular-tag")
    public ResponseEntity<TagDto> getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        TagDto popularTag = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        hateoasAdder.addLinks(popularTag);
        return ResponseEntity.ok(popularTag);
    }
}
