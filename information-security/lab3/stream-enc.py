#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import argparse
import getpass
import sys
import bitarray

LFSR1_SIZE = 78
LFSR2_SIZE = 54
LFSR1_COEFF = [77, 6, 5, 2, 0]
LFSR2_COEFF = [53, 6, 2, 1, 0]


def string_to_bits(string):
    """Преобразует строку в массив бит
    """
    ba = bitarray.bitarray()
    ba.frombytes(string.encode("ascii"))
    return ba.tolist()


def bits_to_byte(bits):
    """Преобразует массив из 8 бит в байт
    """
    return bitarray.bitarray(bits).tobytes()[0]


def key_string_to_bits(key_string, array_size):
    """Преобразует строку ключа, введенную пользователем в массив бит заданной длины.
    """
    string_bits = string_to_bits(key_string)
    if len(string_bits) < array_size:
        num_s = (array_size - len(string_bits)) // len(string_bits)
        string_bits *= num_s + 2
    return string_bits[:array_size]


def modify_lsfr_state(lsfr_state, lsfr_coeff):
    """Возвращает измененнное состояние регистра после одного такта.
    Биты в регистре переносятся вправо, а в последний бит записывается XOR битов,
    указанных в массиве коэффициентов lsfr_coeff.
    """
    last_el = False
    for co in lsfr_coeff:
        last_el ^= lsfr_state[co]
    return lsfr_state[1:] + [last_el]


def gamma_generator(key_string):
    """Генератор гамма-последовательности. Возвращает очередной бит последовательности.
    Моделирует работу регистров потактово.
    """
    lsfr1_state = key_string_to_bits(key_string, LFSR1_SIZE)
    lsfr2_state = key_string_to_bits(key_string, LFSR2_SIZE)
    t = 1

    while True:
        first_and = lsfr1_state[77] and lsfr2_state[52]
        second_and = lsfr1_state[3] and lsfr2_state[41]

        yield first_and ^ second_and

        lsfr1_state = modify_lsfr_state(lsfr1_state, LFSR1_COEFF)
        if t % 3 == 0:
            lsfr2_state = modify_lsfr_state(lsfr2_state, LFSR2_COEFF)
        t += 1


def gamma_generator_bytes(key_string):
    """То же, что и gamma_generator, но возвращает байты, выбирая сразу 8 значений из gamma_generator.
    """
    gen = gamma_generator(key_string)
    while True:
        bits = [next(gen) for _ in range(8)]
        yield bits_to_byte(bits)


def read_and_perform_encrypt_or_decrypt(password):
    """Общая процедура для шифрования и дешифрации.
    Процесс и шифрования, и дешифрации представляет собой XOR входного текста с гамма-последовательностью.
    Процедура побайтово считывает клавиатурный ввод (stdin), делает XOR с очередным байтом гамма-последовательности,
    и выводит байт в stdout.
    """
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
