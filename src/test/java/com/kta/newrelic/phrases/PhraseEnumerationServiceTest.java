package com.kta.newrelic.phrases;

import com.kta.newrelic.AbstractBaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PhraseEnumerationServiceTest extends AbstractBaseTest {

    private static PhraseEnumerationService service;

    @BeforeAll
    static void setup() {
        service = new PhraseEnumerationService();
    }

    @Test
    void execute_happyPath() {
        assertDoesNotThrow(() -> service.execute(MOBY_FILE, BROTHERS_K_FILE, EMPTY_FILE, NON_EXISTING_FILE, ARABIC_FILE));
    }
}