package org.caselli;

/**
 * @author niccolocaselli
 */
final class MyVariable implements PhpVariable {
    private final String name;
    private final PhpDocBlock doc;

    MyVariable(String name, PhpDocBlock doc) {
        this.name = name;
        this.doc = doc;
    }

    @Override
    public PhpDocBlock getDocBlock() {
        return doc;
    }

    @Override
    public String getName() {
        return name;
    }
}