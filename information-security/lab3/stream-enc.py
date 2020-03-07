#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import argparse
import getpass
import sys
import bitarray

LRS1_SIZE = 78
LRS2_SIZE = 54
LRS1_COEFF = [77, 6, 5, 2, 0]
LRS2_COEFF = [53, 6, 2, 1, 0]


def string_to_bits(string):
    ba = bitarray.bitarray()
    ba.frombytes(string.encode("ascii"))
    return ba.tolist()


def bits_to_byte(bits):
    return bitarray.bitarray(bits).tobytes()[0]


def key_string_to_bits(key_string, array_size):
    string_bits = string_to_bits(key_string)
    if len(string_bits) < array_size:
        num_s = (array_size - len(string_bits)) // len(string_bits)
        string_bits *= num_s + 2
    return string_bits[:array_size]


def modify_lrs_state(lrs_state, lrs_coeff):
    last_el = False
    for co in lrs_coeff:
        last_el ^= lrs_state[co]
    return lrs_state[1:] + [last_el]


def gamma_generator(key_string):
    """Генератор гамма-последовательности. Возвращает по биту.
    """
    lrs1_state = key_string_to_bits(key_string, LRS1_SIZE)
    lrs2_state = key_string_to_bits(key_string, LRS2_SIZE)
    t = 1

    while True:
        first_and = lrs1_state[77] and lrs2_state[52]
        second_and = lrs1_state[3] and lrs2_state[41]

        yield first_and ^ second_and

        lrs1_state = modify_lrs_state(lrs1_state, LRS1_COEFF)
        if t % 3 == 0:
            lrs2_state = modify_lrs_state(lrs2_state, LRS2_COEFF)
        t += 1


def gamma_generator_bytes(key_string):
    gen = gamma_generator(key_string)
    while True:
        bits = [next(gen) for _ in range(8)]
        yield bits_to_byte(bits)


def read_and_perform_encrypt_or_decrypt(password):
    generator = gamma_generator_bytes(password)

    while True:
        byte_string = sys.stdin.buffer.read(1)
        if len(byte_string) == 0:  # eof
            break
        processed_bytes = [next(generator) ^ byte for byte in byte_string]
        sys.stdout.buffer.write(bytes(processed_bytes))


def run_encrypt():
    password = getpass.getpass("Type password (max length is 16 symbols): ")
    if getpass.getpass("Repeat password: ") != password:
        raise Exception("passwords doesn't match")
    read_and_perform_encrypt_or_decrypt(password)


def run_decrypt():
    password = getpass.getpass("Password: ")
    read_and_perform_encrypt_or_decrypt(password)


def main():
    parser = argparse.ArgumentParser(description="Stream text encrypt tool")
    parser.add_argument('-d', '--decrypt', action='store_true', dest='decrypt_mode', default=False)
    args = parser.parse_args()

    if args.decrypt_mode:
        run_decrypt()
    else:
        run_encrypt()


if __name__ == '__main__':
    main()
