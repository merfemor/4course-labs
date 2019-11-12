#!/usr/bin/python2
# -*- coding: utf-8 -*-

import random
import sys

'''
Прочитать из файла (имя - параметр командной строки)
все слова (разделитель пробел)

Создать "Похожий" словарь который отображает каждое слово из файла
на список всех слов, которые следуют за ним (все варианты).

Список слов может быть в любом порядке и включать повторения.
например "and" ['best", "then", "after", "then", ...] 

Считаем , что пустая строка предшествует всем словам в файле.

С помощью "Похожего" словаря сгенерировать новый текст
похожий на оригинал.
Т.е. напечатать слово - посмотреть какое может быть следующим 
и выбрать случайное.

В качестве теста можно использовать вывод программы как вход.парам. для следующей копии
(для первой вход.парам. - файл)

Файл:
He is not what he should be
He is not what he need to be
But at least he is not what he used to be
  (c) Team Coach

'''

MAX_STATEMENT_LENGTH = 100

def mem_dict(filename):
    words = []
    with open(filename, "r") as file:
        content = file.read()
        words = content.split()

    word_dict = {}

    for i in range(0, len(words) - 1):
        word = words[i]
        following_word = words[i + 1]
        l = word_dict.get(word, []) or []
        l.append(following_word)
        word_dict[word] = l
    word_dict[words[-1]] = []
    return word_dict


def generate_statement(dict):
    keys = dict.keys()
    cur_key = random.choice(keys)

    st = ""

    for i in range(0, MAX_STATEMENT_LENGTH):
        st += cur_key + " "
        available_follow = dict[cur_key]
        if len(available_follow) == 0:
            break
        cur_key = random.choice(available_follow)

    return st


def main():
    if len(sys.argv) == 1:
        print("usage: " + sys.argv[0] + " <filename>")
        exit(1)
        return

    word_dict = mem_dict(sys.argv[1])
    st = generate_statement(word_dict)
    print(st)


if __name__ == '__main__':
    main()
