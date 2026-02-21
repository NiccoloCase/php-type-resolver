package org.caselli;
import java.util.List;

/**
 * @author niccolocaselli
 */
public final class TypeFactory {
    private TypeFactory() {}

    public static PhpType createType(String typeName) {
        return new MyPhpType(typeName);
    }

    public static PhpType createUnionType(List<PhpType> types) {
        if (types.size() == 1) return types.get(0);
        return new UnionType(types);
    }
}