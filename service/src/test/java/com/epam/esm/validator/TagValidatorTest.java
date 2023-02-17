import com.epam.esm.entity.Tag;

//package com.epam.esm.validator;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.ExceptionResult;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
class TagValidatorTest {
    private static final String INCORRECT_NAME = "qw";
    private static final String CORRECT_NAME = "tagName";}
//    private static final Tag INCORRECT_TAG = new Tag(INCORRECT_NAME);
//    private static final Tag CORRECT_TAG = new Tag(CORRECT_NAME);
//
//    @Test
//    void testValidateIncorrectData() {
//        ExceptionResult exceptionResult = new ExceptionResult();
//        TagValidator.validate(INCORRECT_TAG, exceptionResult);
//        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
//    }
//
//    @Test
//    void testValidateCorrectData() {
//        ExceptionResult exceptionResult = new ExceptionResult();
//        TagValidator.validate(CORRECT_TAG, exceptionResult);
//        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
//    }
//
//    @Test
//    void testValidateNameIncorrectData() {
//        ExceptionResult exceptionResult = new ExceptionResult();
//        TagValidator.validateName(INCORRECT_NAME, exceptionResult);
//        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
//    }
//
//    @Test
//    void testValidateNameCorrectData() {
//        ExceptionResult exceptionResult = new ExceptionResult();
//        TagValidator.validateName(CORRECT_NAME, exceptionResult);
//        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
//    }
//}