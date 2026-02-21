package org.caselli;

import java.util.List;

/**
 * @author niccolocaselli
 */
public interface PhpDocBlock {
    List<DocTag> getTagsByName(String tagName);
}