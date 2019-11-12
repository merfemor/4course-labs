#!/usr/bin/python2
# -*- coding: utf-8 -*-


# 1.
# Входящие параметры: int <count> ,
# Результат: string в форме
# "Number of: <count>", где <count> число из вход.парам.
#  Если число равно 10 или более, напечатать "many"
#  вместо <count>
#  Пример: (5) -> "Number of: 5"
#  (23) -> 'Number of: many'
def num_of_items(count):
    return "Number of: " + str("many" if count > 9 else count)


# 2.
# Входящие параметры: string s,
# Результат: string из 2х первых и 2х последних символов s
# Пример 'welcome' -> 'weme'.
def start_end_symbols(s):
    return s[0:2] + s[-2:]


# 3.
# Входящие параметры: string s,
# Результат: string где все вхождения 1го символа заменяются на '*'
# (кроме самого 1го символа)
# Пример: 'bibble' -> 'bi**le'
# s.replace(stra, strb)
def replace_char(s):
    first_symbol = s[0]
    return first_symbol + s.replace(first_symbol, '*')[1:]


# 4
# Входящие параметры: string a и b,
# Результат: string где <a> и <b> разделены пробелом
# а превые 2 симв обоих строк заменены друг на друга
# Т.е. 'max', pid' -> 'pix mad'
# 'dog', 'dinner' -> 'dig donner'
def str_mix(a, b):
    a_last = a[-1]
    b_last = b[-1]
    return b[0:-1] + a_last + ' ' + a[0:-1] + b_last


def assert_equals(expected_result, actual_result):
    if expected_result != actual_result:
        raise Exception("expect result: " + repr(expected_result) + ", but actual: " + repr(actual_result))


# Provided simple test() function used in main() to print
# what each function returns vs. what it's supposed to return.
def test():
    assert_equals("Number of: 1", num_of_items(1))
    assert_equals("Number of: many", num_of_items(11))

    assert_equals('weme', start_end_symbols('welcome'))
    assert_equals('frog', start_end_symbols('frog'))

    assert_equals('bi**le', replace_char('bibble'))
    assert_equals('pencil', replace_char('pencil'))
    assert_equals('a', replace_char('a'))

    assert_equals('dinneg dor', str_mix('dog', 'dinner'))


if __name__ == '__main__':
    test()