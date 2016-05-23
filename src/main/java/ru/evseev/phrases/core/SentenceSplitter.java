package ru.evseev.phrases.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anev on 23/05/16.
 */
public class SentenceSplitter {


    public static List<String> split(String sentence, int wordsCount) {

        if (wordsCount <= 0 || sentence == null || sentence.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<String> list = Arrays.asList(sentence.split(" ")).stream()
                .map((s) -> s.trim())
                .filter((s) -> !s.isEmpty())
                .collect(Collectors.toList());

        if (wordsCount == 1) {
            return list;
        }

        if (wordsCount > list.size()) {
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList();
        int steps = list.size() - wordsCount + 1;
        for (int i = 0; i < steps; i++) {
            result.add(
                    list.subList(i, i + wordsCount).stream().collect(Collectors.joining(" "))
            );
        }

        return result;
    }

}
