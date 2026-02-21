package org.caselli;

/**
 * @author niccolocaselli
 * Simple implementations to simulate the API
 */
class MyDocTag implements DocTag {
    private final String value;
    MyDocTag(String value) { this.value = value; }
    @Override public String getValue() { return value; }
}