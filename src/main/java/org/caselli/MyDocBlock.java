package org.caselli;

import java.util.*;

/**
 * @author niccolocaselli
 */
final class MyDocBlock implements PhpDocBlock {
    private final Map<String, List<DocTag>> tags = new HashMap<>();

    public MyDocBlock addTag(String name, String value) {
        tags.computeIfAbsent(name, k -> new ArrayList<>()).add(new MyDocTag(value));
        return this;
    }

    @Override
    public List<DocTag> getTagsByName(String tagName) {
        return tags.getOrDefault(tagName, Collections.emptyList());
    }
}
