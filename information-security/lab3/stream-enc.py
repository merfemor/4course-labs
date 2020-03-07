#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import argparse
import getpass
import sys


def gamma_generator(key_string):
    """Генератор гамма-последовательности. Возвращает по байту.
    """
    while True:
        # TODO: implement
        yield 0


def read_and_perform_encrypt_or_decrypt(password):
    generator = gamma_generator(password)

    while True:
        byte_string = sys.stdin.buffer.read(1)
        if len(byte_string) == 0:  # eof
            break
        encrpyted_bytes = [next(generator) ^ byte for byte in byte_string]
        sys.stdout.buffer.write(bytes(encrpyted_bytes))


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
