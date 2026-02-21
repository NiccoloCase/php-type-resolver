package org.caselli;

/**
 * @author niccolocaselli
 */
public class Main {

    public static void main(String[] args) {
        TypeResolver resolver = new TypeResolver();

        // tests provided by the task:

        // 1) Standard Type: /** @var User */ for $user
        PhpDocBlock doc1 = new MyDocBlock().addTag("var", "User");
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$user", doc1))); // User

        // 2) Union Type: /** @var string|int */ for $id
        PhpDocBlock doc2 = new MyDocBlock().addTag("var", "string|int");
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$id", doc2))); // string|int

        // 3) Named Tag: /** @var Logger $log */ for $log
        PhpDocBlock doc3 = new MyDocBlock().addTag("var", "Logger $log");
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$log", doc3))); // Logger

        // 4) Name Mismatch -> mixed
        PhpDocBlock doc4 = new MyDocBlock().addTag("var", "Admin $adm");
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$guest", doc4))); // mixed

        // 5) Multiple Tags: pick the correct one by name
        PhpDocBlock doc5 = new MyDocBlock()
                .addTag("var", "int $id")
                .addTag("var", "string $name");
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$name", doc5))); // string

        // 6) Fallback: no docblock
        System.out.println(resolver.inferTypeFromDoc(new MyVariable("$x", null))); // mixed

    }
}