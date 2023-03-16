package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.impl.GiftDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.logic.renovator.impl.TagUpdater;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private final Tag TAG_1 = new Tag(1, "tag_1");
    private final Tag TAG_2 = new Tag(2, "tag_2");
    private final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "Gift_1", "For holiday", new BigDecimal("10.9"), 3, LocalDateTime.parse("2022-03-29T06:12:15.156"), LocalDateTime.parse("2022-03-29T06:12:15.156"), new ArrayList<>(Arrays.asList(TAG_1, TAG_2)));
    private final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "Gift_3", "For occupation mars", new BigDecimal("30.9"), 3, LocalDateTime.parse("2022-03-29T06:12:15.156"), LocalDateTime.parse("2022-03-29T06:12:15.156"), new ArrayList<>(Arrays.asList(TAG_1, new Tag("tag_100"))));

    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO = new GiftCertificateDto(1, "name", "description", BigDecimal.valueOf(34), 34, Collections.emptyList());
    @Mock
    private GiftCertificateDaoImpl giftDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @Mock
    private GiftDtoConverter giftDtoConverter;

    @Mock
    private TagUpdater tagUpdater;


    @InjectMocks
    private GiftCertificateServiceImpl giftService;

    @Test
    void shouldGetByID() {
        when(giftDao.findById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        when(giftDtoConverter.convertToEntity(any())).thenReturn(GIFT_CERTIFICATE_1);
        GiftCertificate actual = giftDtoConverter.convertToEntity(giftService.getById(GIFT_CERTIFICATE_1.getId()));
        assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void shouldRemove() {
        long id = 1L;
        doNothing().when(giftDao).removeGiftCertificateHasTag(id);
        doNothing().when(giftDao).removeById(id);
        giftService.removeById(id);
    }

    @Test
    void shouldGetAll() {
        when(giftDao.findAll(any())).thenReturn(List.of(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_3));
        when(giftDtoConverter.convertToDto(any())).thenReturn(GIFT_CERTIFICATE_DTO);
        List<GiftCertificateDto> dtoList = giftService.getAll(0, 5);
        assertEquals(List.of(GIFT_CERTIFICATE_DTO, GIFT_CERTIFICATE_DTO), dtoList);
    }


    @Test
    public void shouldInsert() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Gift Certificate");
        GiftCertificate newGiftCertificate = new GiftCertificate();
        newGiftCertificate.setName("Gift Certificate");
        List<Tag> tagsToPersist = new ArrayList<>();
        newGiftCertificate.setTags(tagsToPersist);
        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto();
        expectedGiftCertificateDto.setId(1L);
        expectedGiftCertificateDto.setName("Gift Certificate");
        when(giftDao.findByName(anyString())).thenReturn(Optional.empty());
        when(giftDtoConverter.convertToEntity(any(GiftCertificateDto.class))).thenReturn(newGiftCertificate);
        when(tagUpdater.updateListFromDatabase(anyList())).thenReturn(tagsToPersist);
        when(giftDao.insert(any(GiftCertificate.class))).thenReturn(newGiftCertificate);
        when(giftDtoConverter.convertToDto(any(GiftCertificate.class))).thenReturn(expectedGiftCertificateDto);
        GiftCertificateDto actualGiftCertificateDto = giftService.insert(giftCertificateDto);
        assertEquals(expectedGiftCertificateDto.getId(), actualGiftCertificateDto.getId());
        assertEquals(expectedGiftCertificateDto.getName(), actualGiftCertificateDto.getName());
    }


    @Test
    public void shouldThrowDuplicateException() {
        String giftCertificateName = "Gift Certificate";
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(giftCertificateName);
        GiftCertificate existingGiftCertificate = new GiftCertificate();
        existingGiftCertificate.setName(giftCertificateName);
        when(giftDao.findByName(giftCertificateName)).thenReturn(Optional.of(existingGiftCertificate));
        assertThrows(DuplicateEntityException.class, () -> giftService.insert(giftCertificateDto));
    }


    @Test
    public void shouldSearch() {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag", "holiday");
        int page = 0;
        int size = 10;
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("Gift Certificate");
        giftCertificate.setPrice(BigDecimal.valueOf(50.0));
        List<GiftCertificate> certificates = Collections.singletonList(giftCertificate);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(1L);
        giftCertificateDto.setName("Gift Certificate");
        giftCertificateDto.setPrice(BigDecimal.valueOf(50.0));
        when(giftDao.search(any(), any())).thenReturn(certificates);
        when(giftDtoConverter.convertToDto(any(GiftCertificate.class))).thenReturn(giftCertificateDto);
        List<GiftCertificateDto> actualGiftCertificateDtos = giftService.search(requestParams, page, size);
        assertEquals(1, actualGiftCertificateDtos.size());
        GiftCertificateDto actualGiftCertificateDto = actualGiftCertificateDtos.get(0);
        assertEquals(giftCertificateDto.getId(), actualGiftCertificateDto.getId());
        assertEquals(giftCertificateDto.getName(), actualGiftCertificateDto.getName());
        assertEquals(giftCertificateDto.getPrice(), actualGiftCertificateDto.getPrice());
    }

    @Test
    public void shouldSearchWithNullParam() {
        int page = 0;
        int size = 10;
        List<GiftCertificateDto> actualGiftCertificateDtos = giftService.search(null, page, size);
        assertEquals(Collections.emptyList(), actualGiftCertificateDtos);
    }
}


