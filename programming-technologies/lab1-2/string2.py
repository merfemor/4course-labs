#!/usr/bin/python2
# -*- coding: utf-8 -*-


# 1.
# Вх: строка. Если длина > 3, добавить в конец "ing",
# если в конце нет уже "ing", иначе добавить "ly".
def v(s):
    if len(s) > 3:
        return s if s.endswith("ing") else s + "ing"
    return s + "ly"


# 2.
# Вх: строка. Заменить подстроку от 'not' до 'bad'. ('bad' после 'not')
# на 'good'.
# Пример: So 'This music is not so bad!' -> This music is good!
def nb(s):
    start_idx = s.find("not")
    end_idx = s.find("bad") + 3
    return s[:start_idx] + "good" + s[end_idx:]


def assert_equals(expected_result, actual_result):
    if expected_result != actual_result:
        raise Exception("expect result: " + repr(expected_result) + ", but actual: " + repr(actual_result))


def test():
    assert_equals("ping", v("ping"))
    assert_equals("ponging", v("pong"))
    assert_equals("doly", v("do"))

    assert_equals("This music is good!", nb("This music is not so bad!"))


if __name__ == '__main__':
    test()