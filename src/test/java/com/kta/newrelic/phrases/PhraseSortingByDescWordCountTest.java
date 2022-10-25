package com.kta.newrelic.phrases;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhraseSortingByDescWordCountTest {

    @Test
    void compare_happyPath() {
        AbstractMap.SimpleImmutableEntry<String, Integer> high = new AbstractMap.SimpleImmutableEntry<>("foo", 10);
        AbstractMap.SimpleImmutableEntry<String, Integer> low = new AbstractMap.SimpleImmutableEntry<>("bar", 3);

        int compare = new PhraseSortingByDescWordCount().compare(high, low);
        assertEquals(-7, compare);
    }
}