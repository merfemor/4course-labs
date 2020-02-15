#!/usr/bin/python

import argparse
import sys

'''
4x4 grille is:

1 2 3 1  
3 4 4 2
2 4 4 3
1 3 2 1
'''

APPEND_SYMBOL = '_'

GRILLE_EXAMPLE = \
    "X..." + \
    "X.X." + \
    "...." + \
    "..X."


# Rotate grille by 90 degrees clockwise.
# i = k div 4, j = k % 4
# ni = 3 - j, nj = i, nk = ni * 4 + nj
def rotate90_grille(grille):
    new_index = lambda k: 4 * (3 - k % 4) + int(k / 4)
    rotatated_grille = [grille[new_index(k)] for k in range(0, 16)]
    return rotatated_grille


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
    res = ""
    new_index = lambda i: i - i % 16 + permutations[i % 16]
    for i in range(0, len(text)):
        ni = new_index(i)
        res += text[ni]
    return res


def run_encode(grille):
    for line in sys.stdin:
        if line[-1] == '\n':
            line = line[:-1:1]
        if len(line) == 0:
            continue
        encoded_text = encode(line, grille)
        sys.stdout.write(encoded_text + '\n')


def decode(text, grille):
    raise Exception("decode not implemented")


def run_decode(grille_file):
    # TODO: get grille from arg
    grille = None
    text = sys.stdin.read()
    decoded_text = decode(text, grille)
    sys.stdout.write(decoded_text)


def main():
    parser = argparse.ArgumentParser(description="Cardan Grille encoder/decoder")
    parser.add_argument('-d', '--decode', action='store', dest='decode_grille_file', type=str,
                        help='decode input using specified grille file')
    args = parser.parse_args()

    if args.decode_grille_file is None:
        run_encode(GRILLE_EXAMPLE)
    else:
        run_decode(args.decode_grille_file)


if __name__ == '__main__':
    main()
