package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.hateoas.impl.GiftHateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class GiftController {

    private final GiftCertificateService giftCertificateService;
    private final GiftHateoasAdder hateoasAdder;

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> getGiftCertificate(@PathVariable long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseEntity.ok(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> deleteGiftCertificate(@PathVariable long id) {
        ApiResponse apiResponse = giftCertificateService.removeById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GiftCertificateDto> createGiftCertificate(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.insert(giftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseEntity.ok(giftCertificateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@PathVariable long id, @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.update(id, giftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseEntity.ok(giftCertificateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getGiftCertificates(@RequestParam MultiValueMap<String, String> requestParams,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size) {
        List<GiftCertificateDto> searchResult = giftCertificateService.search(requestParams, page, size);
        searchResult.forEach(hateoasAdder::addLinks);
        return searchResult;
    }
}
