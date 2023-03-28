package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessageKey {

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.Identifiable}.
     */
    public static final String NO_ENTITY = "identifiable.noObject";


    public static final String OPERATION_NOT_SUPPORTED_FOR_USER_ENTITY = "user.not.support";


    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.Tag}.
     */
    public static final String TAG_EXIST = "tag.alreadyExist";
    public static final String TAG_NOT_FOUND = "tag.notFound";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.GiftCertificate}.
     */
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "certificate.notFound";
    public static final String GIFT_CERTIFICATE_EXIST = "certificate.alreadyExist";


    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.User}.
     */
    public static final String USER_NOT_FOUND = "user.notFound";

    /**
     * Keys for exception messages associated with {@link java.awt.print.Pageable}.
     */
    public static final String INVALID_PAGINATION = "pagination.invalid";
}
