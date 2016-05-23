package ru.evseev.phrases.core;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Created by anev on 23/05/16.
 */
public class SentenceProcessor {

    private final int maxWords;
    private final Multiset<String> phrases = HashMultiset.create();

    public SentenceProcessor(int maxWords) {
        this.maxWords = maxWords;
    }

    public Multiset.Entry<String> mostPopularPhrase() {
        UnmodifiableIterator<Multiset.Entry<String>> it =
                Multisets.copyHighestCountFirst(phrases).entrySet().iterator();
        return it.hasNext()
                ? it.next()
                : NULL_ENTRY;
    }

    public void clear() {
        phrases.clear();
    }

    public void process(String centence) {
        SentenceSplitter.split(centence, maxWords).stream().forEach((s) -> phrases.add(s));
    }

    private static final Multiset.Entry<String> NULL_ENTRY = new Multiset.Entry<String>() {
        @Override
        public String getElement() {
            return "";
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "[null]";
        }
    };
}
