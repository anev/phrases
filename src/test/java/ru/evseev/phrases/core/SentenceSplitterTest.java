package ru.evseev.phrases.core;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by anev on 23/05/16.
 */
public class SentenceSplitterTest {

    @Test
    public void test2words() {
        List<String> res1 = SentenceSplitter.split("a b c d e f", 2);
        assertThat(res1)
                .hasSize(5)
                .contains("a b")
                .contains("b c")
                .contains("c d")
                .contains("d e")
                .contains("e f");
    }

    @Test
    public void test3words() {
        List<String> res1 = SentenceSplitter.split("a b c d e f", 3);
        assertThat(res1)
                .hasSize(4)
                .contains("a b c")
                .contains("b c d")
                .contains("c d e")
                .contains("d e f");
    }

    @Test
    public void tooShort() {
        List<String> res1 = SentenceSplitter.split("a b c d e f", 10);
        assertThat(res1)
                .hasSize(0);
    }

    @Test
    public void testOne() {
        List<String> res1 = SentenceSplitter.split("a b c d e f", 1);
        assertThat(res1).hasSize(6);
    }

    @Test
    public void testNull() {
        List<String> res1 = SentenceSplitter.split(null, 2);
        assertThat(res1).hasSize(0);
    }

    @Test
    public void testZero() {
        List<String> res1 = SentenceSplitter.split("a b c", 0);
        assertThat(res1).hasSize(0);
    }

    @Test
    public void testNegative() {
        List<String> res1 = SentenceSplitter.split("a b c", -10);
        assertThat(res1).hasSize(0);
    }

}