#!/usr/bin/python2
# -*- coding: utf-8 -*-


# 1.
# Вх: список строк, Возвр: кол-во строк
# где строка > 2 символов и первый символ == последнему
def me(words):
    count = 0
    for word in words:
        if len(word) > 2 and word[0] == word[-1]:
            count = count + 1
    return count


# 2.
# Вх: список строк, Возвр: список со строками (упорядочено)
# за искл всех строк начинающихся с 'x', которые попадают в начало списка.
# ['tix', 'xyz', 'apple', 'xacadu', 'aabbbccc'] -> ['xacadu', 'xyz', 'aabbbccc', 'apple', 'tix']
def fx(words):
    def comparator(item1, item2):
        first_is_x = len(item1) > 0 and item1[0] == 'x'
        second_is_x = len(item2) > 0 and item2[0] == 'x'
        if first_is_x and not second_is_x:
            return -1
        elif not first_is_x and second_is_x:
            return 1
        elif item1 > item2:
            return 1
        elif item1 < item2:
            return -1
        else:
            return 0
    return sorted(words, cmp=comparator)


# 3.
# Вх: список непустых кортежей,
# Возвр: список сортир по возрастанию последнего элемента в каждом корт.
# [(1, 7), (1, 3), (3, 4, 5), (2, 2)] -> [(2, 2), (1, 3), (3, 4, 5), (1, 7)]
def sort_by_last_cortege_element(corteges):
    return sorted(corteges, key=lambda c: c[1])


def assert_equals(expected_result, actual_result):
    if expected_result != actual_result:
        raise Exception("expect result: " + repr(expected_result) + ", but actual: " + repr(actual_result))


def test_me():
    assert_equals(0, me([]))
    assert_equals(0, me(['a']))
    assert_equals(0, me(['aa']))
    assert_equals(1, me(['aab', 'aa', 'abba']))


def test_fx():
    assert_equals(['a', 'b'], fx(['b', 'a']))
    assert_equals(['x', 'a', 'b'], fx(['b', 'x', 'a']))


def test_sort_by_last_cortege_element():
    assert_equals([(1, 2), (0, 3)], sort_by_last_cortege_element([(0, 3), (1, 2)]))
    assert_equals([(3, 1), (2, 2), (1, 3)], sort_by_last_cortege_element([(2, 2), (1, 3), (3, 1)]))


def test():
    test_me()
    test_fx()
    test_sort_by_last_cortege_element()


if __name__ == '__main__':
    test()