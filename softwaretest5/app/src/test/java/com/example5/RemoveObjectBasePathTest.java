package com.example5;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemoveObjectBasePathTest {
    // 用例1：覆盖路径1 → o=null，空链表
    @Test
    void removeObject_null_emptyList() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        boolean result = list.remove(null);
        assertFalse(result);
    }

    // 用例2：覆盖路径2 → o=null，元素存在
    @Test
    void removeObject_null_exist() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        list.add(null);
        list.add("a");
        boolean result = list.remove(null);
        assertTrue(result);
    }

    // 用例3：覆盖路径3 → o=null，元素不存在
    @Test
    void removeObject_null_notExist() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        list.add("a");
        list.add("b");
        boolean result = list.remove(null);
        assertFalse(result);
    }

    // 用例4：覆盖路径4 → o≠null，空链表
    @Test
    void removeObject_notNull_emptyList() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        boolean result = list.remove("test");
        assertFalse(result);
    }

    // 用例5：覆盖路径5 → o≠null，元素存在
    @Test
    void removeObject_notNull_exist() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        list.add("a");
        list.add("b");
        boolean result = list.remove("a");
        assertTrue(result);
    }

    // 用例6：覆盖路径6 → o≠null，元素不存在
    @Test
    void removeObject_notNull_notExist() {
        LinkedListRemove<String> list = new LinkedListRemove<>();
        list.add("a");
        list.add("b");
        boolean result = list.remove("c");
        assertFalse(result);
    }
}
