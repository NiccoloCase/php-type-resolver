package org.caselli;

/**
 * @author niccolocaselli
 */
public interface PhpVariable {
    PhpDocBlock getDocBlock(); // may be null
    String getName(); // e.g. "$user"
}