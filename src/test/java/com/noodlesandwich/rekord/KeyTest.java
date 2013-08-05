package com.noodlesandwich.rekord;

import com.noodlesandwich.rekord.transformers.Transformer;
import org.junit.Test;

import static com.noodlesandwich.rekord.Transformers.defaultsTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class KeyTest {
    @Test public void
    retrieves_a_value_from_a_property_map() {
        Properties<Thing> properties = new Properties<Thing>()
                .with(Thing.one.of(1))
                .with(Thing.two.of(2));

        assertThat(Thing.one.retrieveFrom(properties), is(1));
    }

    @Test public void
    returns_null_if_the_property_map_does_not_contain_the_key() {
        Properties<Thing> properties = new Properties<Thing>()
                .with(Thing.one.of(5));

        assertThat(Thing.two.retrieveFrom(properties), is(nullValue()));
    }

    @Test public void
    transforms_according_to_its_transformer() {
        Key<Thing, String> key = Key.<Thing, String>named("key").that(upperCases());

        Properties<Thing> properties = new Properties<Thing>()
                .with(key.of("kablammo"));

        assertThat(key.retrieveFrom(properties), is("KABLAMMO"));
    }

    @Test public void
    delegates_to_internal_transformers() {
        Key<Thing, String> key = Key.<Thing, String>named("key").that(defaultsTo("nobody loves me")).then(upperCases());

        Properties<Thing> properties = new Properties<>();

        assertThat(key.retrieveFrom(properties), is("NOBODY LOVES ME"));
    }

    @Test public void
    stringifies_to_its_name() {
        assertThat(Thing.one, hasToString("one"));
    }

    private static interface Thing extends RekordType {
        Key<Thing, Integer> one = Key.named("one");
        Key<Thing, Integer> two = Key.named("two");
    }

    private static Transformer<String, String> upperCases() {
        return new Transformer<String, String>() {
            @Override public String transform(String value) {
                return value.toUpperCase();
            }
        };
    }
}