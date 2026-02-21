package org.caselli;

import java.util.Objects;

/**
 * @author niccolocaselli
 */
public final class MyPhpType implements PhpType {
    private final String name;

    public MyPhpType(String name) {
        this.name = Objects.requireNonNull(name).trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public String describe() {
        return name;
    }

    @Override
    public String toString() {
        return describe();
    }
}