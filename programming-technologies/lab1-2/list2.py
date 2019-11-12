#!/usr/bin/python2
# -*- coding: utf-8 -*-


# 1.
# Вх: список чисел, Возвр: список чисел, где
# повторяющиеся числа урезаны до одного
# пример [0, 2, 2, 3] returns [0, 2, 3].
def rm_adj(nums):
    return list(set(nums))


# 2. Вх: Два списка упорядоченных по возрастанию, Возвр: новый отсортированный объединенный список
def join_sorted(list1, list2):
    return sorted(list1 + list2)


def assert_equals(expected_result, actual_result):
    if expected_result != actual_result:
        raise Exception("expect result: " + repr(expected_result) + ", but actual: " + repr(actual_result))


def test():
    assert_equals([], rm_adj([]))
    assert_equals([1], rm_adj([1, 1, 1]))
    assert_equals([1, 2], rm_adj([1, 2, 1]))

    assert_equals([], join_sorted([], []))
    assert_equals([1, 1, 2, 2, 3, 3], join_sorted([1, 1, 2, 3], [2, 3]))


if __name__ == '__main__':
    test()