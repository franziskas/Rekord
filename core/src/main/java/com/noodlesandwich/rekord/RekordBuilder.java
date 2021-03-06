package com.noodlesandwich.rekord;

import com.noodlesandwich.rekord.keys.Key;
import com.noodlesandwich.rekord.properties.Property;

public interface RekordBuilder<T, B extends RekordBuilder<T, B>> extends RekordTemplate<T> {
    <V> B with(Property<T, V> property);

    <V> B with(Key<T, V> key, V value);

    <V> B with(V value, Key<T, V> key);

    B without(Key<T, ?> key);

    B merge(FixedRekord<T> other);
}
