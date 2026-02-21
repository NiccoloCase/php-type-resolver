package org.caselli;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author niccolocaselli
 */
public final class TypeResolver {

    /**
     * Infers the type of a PhpVariable based on its PhpDocBlock and @var tags.
     * @param variable the PhpVariable for which to infer the type
     * @return the inferred PhpType based on the @var tags in the variable's PhpDocBlock, or "mixed" if no relevant tags are found.
     */
    public PhpType inferTypeFromDoc(PhpVariable variable) {
        // just in case :)
        Objects.requireNonNull(variable, "variable");

        // reads the PhpDocBlock of the variable
        // if there is not docblock we simply fall back to "mixed" type
        PhpDocBlock doc = variable.getDocBlock();
        if (doc == null) return TypeFactory.createType("mixed");

        // get the @var tags from the docblock if any
        List<DocTag> varTags = doc.getTagsByName("var");
        if (varTags == null || varTags.isEmpty()) return TypeFactory.createType("mixed");


        // we loop over all @var tags and try to find the one that matches our variable name
        String varName = normalizeVarName(variable.getName());
        PhpType namedMatch = null;
        List<PhpType> candidates = new ArrayList<>(); // potential matches without variable name

        for (DocTag tag : varTags) {
            if (tag == null || tag.getValue() == null) continue;

            ParsedVarTag parsed = parseVarTag(tag.getValue());
            if (parsed == null || parsed.typeText.isEmpty()) continue;

            PhpType tagType = convertStringToPhpType(parsed.typeText);

            if (parsed.variableName != null) {
                // we found it!
                if (normalizeVarName(parsed.variableName).equals(varName)) {
                    namedMatch = tagType;
                    break;
                }
            } else {
                // potential fallback candidate
                candidates.add(tagType);
            }
        }

        if (namedMatch != null) return namedMatch;
        if (candidates.size() == 1) return candidates.get(0);

        // fallback
        return TypeFactory.createType("mixed");
    }

    /**
     * Normalizes a variable name by trimming whitespace.
     * @param name the variable name to normalize, e.g. " $count " or null
     * @return the normalized variable name
     */
    private static String normalizeVarName(String name) {
        if (name == null) return "";
        return name.trim();
    }

    /**
     * Converts a type string from a @var tag into a PhpType.
     * @param typeText the type part of the @var tag, e.g. "int", "string|false"
     * @return the corresponding PhpType, or "mixed" if the input is empty or invalid.
     */
    private static PhpType convertStringToPhpType(String typeText) {
        String t = typeText.trim();
        if (t.isEmpty()) return TypeFactory.createType("mixed");

        // union type split
        String[] parts = t.split("\\|");
        if (parts.length == 1) return TypeFactory.createType(parts[0].trim());

        List<PhpType> types = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) types.add(TypeFactory.createType(s));
        }

        if (types.isEmpty()) return TypeFactory.createType("mixed");
        return TypeFactory.createUnionType(types);
    }

    /**
     * Parses the value of a @var tag to extract the type and optional variable name.
     * @param value the raw value of the @var tag, e.g. "int $count" or "string|false"
     * @return a ParsedVarTag containing the type text and variable name (if any), or null if the input is empty.
     */
    private static ParsedVarTag parseVarTag(String value) {
        String v = value.trim();
        if (v.isEmpty()) return null;

        // the first word is the type part, the rest may contain the variable name
        int firstSpace = indexOfWhitespace(v);
        String typePart;
        String rest;

        if (firstSpace < 0) {
            // there is no space, so the entire value is the type part
            typePart = v;
            rest = "";
        } else {
            typePart = v.substring(0, firstSpace).trim();
            rest = v.substring(firstSpace).trim();
        }

        String varName = extractFirstVariableToken(rest); // e.g. "$log" or null
        return new ParsedVarTag(typePart, varName);
    }

    /**
     * Finds the index of the first whitespace character in the string, or -1 if there is none.
     * @param s the input string
     * @return the index of the first whitespace character, or -1 if not found
     */
    private static int indexOfWhitespace(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.isWhitespace(s.charAt(i))) return i;

        return -1;
    }

    /**
     * Extracts the first variable token from the input string
     * @param s input string, e.g. "$log some description" or "some description $log"
     * @return the first token that starts with '$' and has at least one character after it, or null if none found
     */
    private static String extractFirstVariableToken(String s) {
        if (s == null) return null;
        String r = s.trim();
        if (r.isEmpty()) return null;

        // find first token that starts with '$'
        int i = 0;
        while (i < r.length()) {
            // skip whitespace
            while (i < r.length() && Character.isWhitespace(r.charAt(i))) i++;
            if (i >= r.length()) break;

            int start = i;
            while (i < r.length() && !Character.isWhitespace(r.charAt(i))) i++;
            String token = r.substring(start, i);

            if (token.startsWith("$") && token.length() > 1)
                return token;

        }

        return null;
    }

    private static final class ParsedVarTag {
        final String typeText;
        final String variableName;

        ParsedVarTag(String typeText, String variableName) {
            this.typeText = typeText == null ? "" : typeText.trim();
            this.variableName = variableName;
        }
    }
}