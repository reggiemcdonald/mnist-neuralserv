package org.reggiemcdonald.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ListUtils {

    public static <T> ArrayList<T> arrayList(T... items) {
        ArrayList<T> list = new ArrayList<>(Arrays.asList(items));
        return list;
    }

    public static <T> LinkedList<T> linkedList(T... items) {
        LinkedList<T> list = new LinkedList<>(Arrays.asList(items));
        return list;
    }
}
