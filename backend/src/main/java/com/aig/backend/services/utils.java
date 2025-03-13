package com.aig.backend.services;

import org.springframework.ai.model.Media;

import java.util.List;

public class utils {
    public static String mediaToString(List<Media> media) {
        if (media.isEmpty()) {
            return "";
        }

        String all = new String();
        int i = 0;
        all += media.get(i++).toString() + "\n";
        for (; i < media.size(); ++i) {
            all += media.get(i).toString() + "\n";
        }

        return all;
    }
}
