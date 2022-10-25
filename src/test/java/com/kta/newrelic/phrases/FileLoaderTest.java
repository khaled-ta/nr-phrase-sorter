package com.kta.newrelic.phrases;

import com.kta.newrelic.AbstractBaseTest;
import com.kta.newrelic.exceptions.FileLoadingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest extends AbstractBaseTest {

    private static FileLoader fileLoader;

    @BeforeAll
    public static void setup() {
        fileLoader = new FileLoader();
    }

    @Test
    void loadFile_happyPath() throws FileLoadingException {
        String fileContents = fileLoader.loadFile(MOBY_FILE);
        assertNotNull(fileContents);
        assertTrue(fileContents.length() > 0);
    }

    @Test
    void loadFile_happyPath_whenFileContentIsEmpty() throws FileLoadingException {
        String fileContents = fileLoader.loadFile(EMPTY_FILE);
        assertNotNull(fileContents);
        assertEquals(0, fileContents.length());
    }

    @Test
    void loadFile_whenFileDoesNotExist_throwException() {
        assertThrows(FileLoadingException.class, () -> fileLoader.loadFile(NON_EXISTING_FILE));
    }

    @Test
    void loadFile_whenNullIsPassed_throwException() {
        assertThrows(FileLoadingException.class, () -> fileLoader.loadFile(null));
    }
}