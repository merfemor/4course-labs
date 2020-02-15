#!/usr/bin/python
# -*- coding: utf-8 -*-

import argparse
import sys

'''
4x4 grille is:

1 2 3 1  
3 4 4 2
2 4 4 3
1 3 2 1
'''

APPEND_SYMBOL = ' '

DEFAULT_GRILLE = \
    "X..." + \
    "X.X." + \
    "...." + \
    "..X."


# Rotate grille by 90 degrees clockwise.
# i = k div 4, j = k % 4
# ni = 3 - j, nj = i, nk = ni * 4 + nj
def rotate90_grille(grille):
    def new_index(k):
        return 4 * (3 - k % 4) + int(k / 4)

    return [grille[new_index(k)] for k in range(0, 16)]


# convert grille to array[16] of new indexes
def grille_to_permutations(grille):
    moved_to = [-1] * 16
    cur = 0
    for i in range(0, 4):
        for j in range(0, 16):
            if grille[j] == 'X':
                moved_to[cur] = j
                cur += 1
        grille = rotate90_grille(grille)
    return moved_to


def encode(text, grille_str):
    # дополним символами до длины кратной 16
    text += APPEND_SYMBOL * ((16 - len(text) % 16) % 16)
    permutations = grille_to_permutations(grille_str)
    encoded_text = ""

    def new_index(i):
        return i - i % 16 + permutations[i % 16]

    for i in range(0, len(text)):
        ni = new_index(i)
        encoded_text += text[ni]
    return encoded_text


def run_encode(grille):
    for line in sys.stdin:
        if line[-1] == '\n':
            line = line[:-1:1]
        if len(line) == 0:
            continue
        encoded_text = encode(line, grille)
        sys.stdout.write(encoded_text + '\n')


def decode(text, grille):
    permutations = grille_to_permutations(grille)
    reversed_permutations = [-1] * 16
    for i in range(0, len(permutations)):
        reversed_permutations[permutations[i]] = i

    def new_index(i):
        return i - i % 16 + reversed_permutations[i % 16]

    decoded_text = ""
    for i in range(0, len(text)):
        decoded_text += text[new_index(i)]
    return decoded_text


def run_decode(grille):
    for line in sys.stdin:
        if line[-1] == '\n':
            line = line[:-1:1]
        if len(line) == 0:
            continue
        decoded_text = decode(line, grille)
        sys.stdout.write(decoded_text + '\n')


def main():
    parser = argparse.ArgumentParser(description="Cardan Grille encoder/decoder.")
    parser.add_argument('-d', '--decode', action='store_true', dest='decode_mode', default=False,
                        help='text will be decoded if this argument specified')
    parser.add_argument('-g', action='store', dest='grille_str', type=str, default='' + DEFAULT_GRILLE,
                        help='string with 4x4 grille. If not specified, default is ' + DEFAULT_GRILLE)
    args = parser.parse_args()

    if args.decode_mode:
        run_decode(args.grille_str)
    else:
        run_encode(args.grille_str)


if __name__ == '__main__':
    main()
