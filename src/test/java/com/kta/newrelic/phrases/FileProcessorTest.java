package com.kta.newrelic.phrases;

import com.kta.newrelic.AbstractBaseTest;
import com.kta.newrelic.exceptions.FileLoadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileProcessorTest extends AbstractBaseTest {

    private FileProcessor fileProcessor;
    private ConcurrentHashMap<String, Integer> map;

    @Mock
    private FileLoader fileLoader;

    @BeforeEach
    void setup() {
        fileProcessor = new FileProcessor(fileLoader);
        map = new ConcurrentHashMap<>();
    }

    @Test
    void process_happyPath() throws FileLoadingException {
        when(fileLoader.loadFile(any())).thenReturn("foo bar hello world");
        String resultOutput = fileProcessor.process(MOBY_FILE, map);
        assertThat(resultOutput, matchesPattern("filename files/moby\\.txt completed successfully in [0-9]+ milliSeconds!"));
    }

    @Test
    void process_happyPath_emptyFile() throws FileLoadingException {
        when(fileLoader.loadFile(any())).thenReturn("");
        String resultOutput = fileProcessor.process(EMPTY_FILE, map);
        assertThat(resultOutput, matchesPattern("filename files/empty.txt is empty, process ending in [0-9]+ milliSeconds!"));
    }

    @Test
    void process_fileNotFound_throwException() throws FileLoadingException {
        when(fileLoader.loadFile(any())).thenThrow(FileLoadingException.class);
        assertThrows(FileLoadingException.class, () -> fileProcessor.process(MOBY_FILE, map));
    }
}