package org.caselli;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author niccolocaselli
 */
public record UnionType(List<PhpType> types) implements PhpType {
    public UnionType(List<PhpType> types) {
        Objects.requireNonNull(types);
        if (types.isEmpty()) throw new IllegalArgumentException("UnionType requires at least one type");
        this.types = Collections.unmodifiableList(new ArrayList<>(types));
    }

    @Override
    public String describe() {
        return types.stream().map(PhpType::describe).collect(Collectors.joining("|"));
    }

    @Override
    public String toString() {
        return describe();
    }
}