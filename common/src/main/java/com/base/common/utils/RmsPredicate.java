package com.base.common.utils;

import java.util.ArrayList;
import java.util.Collection;

public class RmsPredicate {


    public static <T> Collection<T> filter(Collection<T> target, RmsPredicateImp<T> predicate) {
        Collection<T> result = new ArrayList<>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }

        return result;
    }

    @SuppressWarnings("unused")
    public static <T> T select(Collection<T> target, RmsPredicateImp<T> predicate) {
        T result = null;
        for (T element : target) {
            if (!predicate.apply(element))
                continue;
            result = element;
            break;
        }
        return result;
    }
    @SuppressWarnings("unused")
    public static <T> T select(Collection<T> target, RmsPredicateImp<T> predicate, T defaultValue) {
        T result = defaultValue;
        for (T element : target) {
            if (!predicate.apply(element))
                continue;
            result = element;
            break;
        }
        return result;
    }
}
