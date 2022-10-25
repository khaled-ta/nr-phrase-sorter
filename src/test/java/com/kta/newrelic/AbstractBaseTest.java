package com.kta.newrelic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class AbstractBaseTest {

    protected static final String EMPTY_FILE = "files/empty.txt";
    protected static final String MOBY_FILE = "files/moby.txt";
    protected static final String BROTHERS_K_FILE = "files/brothers-karamazov.txt";
    protected static final String ONE_WORD_FILE = "files/one-word-file.txt";
    protected static final String NON_EXISTING_FILE = "file-does-not-exist.txt";
    protected static final String ARABIC_FILE = "files/arabic.txt";

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

}
