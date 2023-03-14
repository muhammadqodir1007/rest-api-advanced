package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.renovator.impl.GiftCertificateUpdater;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;

    @Mock
    private GiftCertificateUpdater renovator = Mockito.mock(GiftCertificateUpdater.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;


    private static final Tag TAG_2 = new Tag(2, "tagName3");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1", "description1", new BigDecimal("10.1"), 1, LocalDateTime.parse("2020-08-29T06:12:15.156"), LocalDateTime.parse("2020-08-29T06:12:15.156"), Arrays.asList(new Tag(1, "tagName1"), new Tag(2, "tagName3"), new Tag(3, "tagName5")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3", "description3", new BigDecimal("30.3"), 3, LocalDateTime.parse("2019-08-29T06:12:15.156"), LocalDateTime.parse("2019-08-29T06:12:15.156"), Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2", "description2", new BigDecimal("20.2"), 2, LocalDateTime.parse("2018-08-29T06:12:15.156"), LocalDateTime.parse("2018-08-29T06:12:15.156"), null);
    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4, "giftCertificate2", "description2", new BigDecimal("20.2"), 2, LocalDateTime.now(), LocalDateTime.now(), null);

    private static final String SORT_PARAMETER = "DESC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;


    @Test
    void shouldInsert() {
        when(giftCertificateDao.insert(any())).thenReturn(GIFT_CERTIFICATE_4);

        GiftCertificate certificate = giftCertificateService.insert(new GiftCertificate("name", "description", BigDecimal.valueOf(34), 34));

        assertEquals(GIFT_CERTIFICATE_4.getName(), certificate.getName());
    }


    @Test
    public void shouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Existing Gift Certificate");
        giftCertificate.setDescription("This is an existing gift certificate");
        GiftCertificate existingGiftCertificate = new GiftCertificate();
        existingGiftCertificate.setId(1L);
        existingGiftCertificate.setName("Existing Gift Certificate");
        existingGiftCertificate.setDescription("This is an existing gift certificate");
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(existingGiftCertificate));
        assertThrows(DuplicateEntityException.class, () -> {
            giftCertificateService.insert(giftCertificate);
        });

    }

    @Test
    void shouldGetById() {
        when(giftCertificateDao.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        GiftCertificate actual = giftCertificateService.getById(GIFT_CERTIFICATE_2.getId());
        assertEquals(GIFT_CERTIFICATE_2, actual);
    }

    @Test
    public void shouldThrowExceptionWhenFindById() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        long invalidId = 1L;
        assertThrows(NoSuchEntityException.class, () -> {
            giftCertificateService.getById(invalidId);
        });
        verify(giftCertificateDao, times(1)).findById(eq(invalidId));
    }


    @Test
    void shouldGetAll() {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(giftCertificateDao.findAll(pageRequest)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.getAll(PAGE, SIZE);
        assertEquals(giftCertificates, actual);
    }


    @Test
    void shouldUpdate() {
        when(renovator.updateObject(GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_3)).thenReturn(GIFT_CERTIFICATE_3);
        when(giftCertificateDao.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        when(giftCertificateDao.update(GIFT_CERTIFICATE_3)).thenReturn(GIFT_CERTIFICATE_3);
        GiftCertificate actual = giftCertificateService.update(GIFT_CERTIFICATE_3.getId(), GIFT_CERTIFICATE_3);
        assertEquals(GIFT_CERTIFICATE_3, actual);
    }

    @Test
    public void shouldNotUpdateWithInvalidId() {
        long id = 1L;
        GiftCertificate giftCertificate = new GiftCertificate();
        when(giftCertificateDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.update(id, giftCertificate));
    }

    @Test
    public void shouldNotUpdateWithDuplicateName() {
        long id = 1L;
        String giftCertificateName = "gift certificate";
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateName);
        GiftCertificate oldGiftCertificate = new GiftCertificate();
        oldGiftCertificate.setName("old gift certificate");
        when(giftCertificateDao.findById(id)).thenReturn(Optional.of(oldGiftCertificate));
        when(giftCertificateDao.findByName(giftCertificateName)).thenReturn(Optional.of(giftCertificate));

        assertThrows(DuplicateEntityException.class, () -> giftCertificateService.update(id, giftCertificate));

    }

    @Test
    void shouldSearch() {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tagName", TAG_2.getName());
        requestParams.add("sortByName", SORT_PARAMETER);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(giftCertificateDao.search(requestParams, pageRequest)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.search(requestParams, PAGE, SIZE);
        assertEquals(giftCertificates, actual);
    }

    @Test
    public void shouldRemoveById() {
        long id = 1L;
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        when(giftCertificateDao.findById(id)).thenReturn(Optional.of(giftCertificate));
        giftCertificateService.removeById(id);
        verify(giftCertificateDao).findById(id);
        verify(giftCertificateDao).removeGiftCertificateHasTag(id);
        verify(giftCertificateDao).removeById(id);
    }

    @Test
    public void shouldThrowExceptionWhenRemovingById() {
        long id = 1L;
        when(giftCertificateDao.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.removeById(id));
        verify(giftCertificateDao).findById(id);
        verifyNoMoreInteractions(giftCertificateDao);
    }


}