package com.epam.esm.controller;

import com.epam.esm.config.MessageByLang;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.hateoas.impl.GiftHateoasAdder;
import com.epam.esm.service.GiftCertificateService;
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
@RequestMapping("/api/certificates")
public class GiftController {

    private final GiftCertificateService giftCertificateService;
    private final DtoConverter<GiftCertificate, GiftCertificateDto> giftCertificateDtoConverter;
    private final GiftHateoasAdder hateoasAdder;

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id the ID of the gift certificate to retrieve
     * @return the gift certificate with the specified ID
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificate(@PathVariable long id) {
        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Deletes a gift certificate by its ID.
     *
     * @param id the ID of the gift certificate to delete
     * @return a response entity with no content
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.removeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageByLang.toLocale("successfully.deleted"));
    }

    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificate the gift certificate to create
     * @return the created gift certificate
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificate addedGiftCertificate = giftCertificateService.insert(giftCertificateDtoConverter.convertToEntity(giftCertificate));
        GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(addedGiftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Updates an existing gift certificate.
     *
     * @param id              the ID of the gift certificate to update
     * @param giftCertificate the updated gift certificate
     * @return the updated gift certificate
     */

    @PatchMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@Valid @PathVariable long id, @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(id, giftCertificateDtoConverter.convertToEntity(giftCertificate));
        GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(updatedGiftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Retrieves a list of gift certificates based on the provided parameters.
     *
     * @param requestParams a map of search parameters
     * @param page          the page number to retrieve (default: 0)
     * @param size          the number of items per page (default: 5)
     * @return a list of gift certificates that match the search parameters
     */
    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(@RequestParam MultiValueMap<String, String> requestParams,
                                                        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificate> giftCertificates = giftCertificateService.search(requestParams, page, size);
        List<GiftCertificateDto> giftCertificateDtos = giftCertificates.stream()
                .map(giftCertificateDtoConverter::convertToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
        return giftCertificateDtos;
    }
}
