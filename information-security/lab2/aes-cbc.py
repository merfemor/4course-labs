#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import argparse
import getpass
import secrets

"""
Официальная документация AES: http://csrc.nist.gov/publications/fips/fips197/fips-197.pdf
Реализовано шифрование/деширфация AES в 128-битном режиме с сцеплением блоков (CBC).
"""

SBOX = [
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
]
INV_SBOX = [
    0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
    0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
    0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
    0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
    0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
    0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
    0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
    0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
    0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
    0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
    0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
    0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
    0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
    0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
    0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
    0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
]
RCON = [
    [0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36],
    [0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00],
    [0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00],
    [0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00]
]

BLOCK_SIZE_BYTES = 16
FILE_LENGTH_BYTES_IN_HEADER = 8
ROUNDS_NUM = 10


def create_2d_array(n, m):
    """Создает пустой массив размером n * m.
    n - количество строк
    m - количество столбцов
    """
    return [[None for _ in range(m)] for _ in range(n)]


def block_to_state(block):
    """Трансформирует строку из 16 байт в массив state 4x4
    """
    state = create_2d_array(4, 4)

    for i in range(len(block)):
        state_i = i % 4
        state_j = i // 4
        state[state_i][state_j] = block[i]
    return state


def state_to_block(state):
    """Трансформирует массив state в строку из 16 байт
    """
    return [state[i % 4][i // 4] for i in range(16)]


def shift_left_array(arr, n):
    """Циклический сдвиг массива влево на n
    """
    assert n < len(arr)
    return [arr[(i + n) % len(arr)] for i in range(len(arr))]


def shift_right_array(arr, n):
    """Циклический сдвиг массива вправо на n
    """
    assert n < len(arr)
    return [arr[(i + len(arr) - n) % len(arr)] for i in range(len(arr))]


def key_expansion(password_str):
    password_bytes = [i for i in password_str.encode("ascii")]
    password_bytes += [0x01] * (16 - len(password_bytes))
    assert len(password_bytes) == 16

    column_num = 4 * (ROUNDS_NUM + 1)
    key_schedule = create_2d_array(4, column_num)
    for i in range(len(password_bytes)):
        ki = i % 4
        kj = i // 4
        key_schedule[ki][kj] = password_bytes[i]

    for i in range(4, column_num):
        w_prev = [key_schedule[b][i - 1] for b in range(4)]
        w_prev4 = [key_schedule[b][i - 4] for b in range(4)]
        if i % 4 == 0:
            w_prev = shift_left_array(w_prev, 1)
            w_prev = [SBOX[b] for b in w_prev]
            rcon_col = i // 4 - 1
            w_now = [w_prev4[j] ^ w_prev[j] ^ RCON[j][rcon_col] for j in range(4)]
        else:
            w_now = [w_prev4[j] ^ w_prev[j] for j in range(4)]
        for j in range(4):
            key_schedule[j][i] = w_now[j]

    return key_schedule


def add_round_key(state, key_schedule, round_num):
    new_state = create_2d_array(4, 4)
    for i in range(4):
        for j in range(4):
            new_state[i][j] = state[i][j] ^ key_schedule[i][j + round_num * 4]
    return new_state


def sub_bytes(state, inverse=False):
    replacemenets = INV_SBOX if inverse else SBOX
    new_state = create_2d_array(4, 4)
    for i in range(4):
        for j in range(4):
            new_state[i][j] = replacemenets[state[i][j]]
    return new_state


def shift_rows(state, inverse=False):
    new_state = create_2d_array(4, 4)
    for i in range(4):
        shift_func = shift_right_array if inverse else shift_left_array
        new_state[i] = shift_func(state[i], i)
    return new_state


# далее идут функции умножения на разные константы в поле Галуа
def mul_by_02(num):
    if num < 0x80:
        return num << 1
    return ((num << 1) ^ 0x1b) % 0x100


def mul_by_03(num):
    return mul_by_02(num) ^ num


def mul_by_09(num):
    return mul_by_02(mul_by_02(mul_by_02(num))) ^ num


def mul_by_0b(num):
    return mul_by_02(mul_by_02(mul_by_02(num))) ^ mul_by_02(num) ^ num


def mul_by_0d(num):
    return mul_by_02(mul_by_02(mul_by_02(num))) ^ mul_by_02(mul_by_02(num)) ^ num


def mul_by_0e(num):
    return mul_by_02(mul_by_02(mul_by_02(num))) ^ mul_by_02(mul_by_02(num)) ^ mul_by_02(num)


def mix_columns(state):
    new_state = create_2d_array(4, 4)
    for i in range(4):
        new_state[0][i] = mul_by_02(state[0][i]) ^ mul_by_03(state[1][i]) ^ state[2][i] ^ state[3][i]
        new_state[1][i] = state[0][i] ^ mul_by_02(state[1][i]) ^ mul_by_03(state[2][i]) ^ state[3][i]
        new_state[2][i] = state[0][i] ^ state[1][i] ^ mul_by_02(state[2][i]) ^ mul_by_03(state[3][i])
        new_state[3][i] = mul_by_03(state[0][i]) ^ state[1][i] ^ state[2][i] ^ mul_by_02(state[3][i])
    return new_state


def inv_mix_columns(state):
    new_state = create_2d_array(4, 4)
    for i in range(4):
        new_state[0][i] = mul_by_0e(state[0][i]) ^ mul_by_0b(state[1][i]) ^ mul_by_0d(state[2][i]) ^ mul_by_09(
            state[3][i])
        new_state[1][i] = mul_by_09(state[0][i]) ^ mul_by_0e(state[1][i]) ^ mul_by_0b(state[2][i]) ^ mul_by_0d(
            state[3][i])
        new_state[2][i] = mul_by_0d(state[0][i]) ^ mul_by_09(state[1][i]) ^ mul_by_0e(state[2][i]) ^ mul_by_0b(
            state[3][i])
        new_state[3][i] = mul_by_0b(state[0][i]) ^ mul_by_0d(state[1][i]) ^ mul_by_09(state[2][i]) ^ mul_by_0e(
            state[3][i])
    return new_state


def encrypt_block(block, key_schedule):
    """Шифрует один блок, используя уже сгенерированный key_schedule.
    Превращает блок в массив 4x4 state и производит последовательно трансформации.
    """
    state = block_to_state(block)
    state = add_round_key(state, key_schedule, 0)

    for round_num in range(1, ROUNDS_NUM):
        state = sub_bytes(state)
        state = shift_rows(state)
        state = mix_columns(state)
        state = add_round_key(state, key_schedule, round_num)

    state = sub_bytes(state)
    state = shift_rows(state)
    state = add_round_key(state, key_schedule, ROUNDS_NUM)
    return state_to_block(state)


def generate_init_vector():
    return [b for b in secrets.token_bytes(BLOCK_SIZE_BYTES)]


def run_encrypt(input_filename, output_filename):
    """Основная процедура шифрования файла.
    Считывает пароль, превращает его в ключ шифрования,
    шифрует содержимое файла input_filename и выводит в файл output_filename, считывая блоками по 16 байт.
    """
    password = getpass.getpass("Type password (max length is 16 symbols): ")

    # в 128 битном режиме для корректной генерации key_schedule длина пароля должна быть не более 16 байт
    if len(password) > 16:
        raise Exception("password too long, max len is 16 symbols")
    if getpass.getpass("Repeat password: ") != password:
        raise Exception("passwords doesn't match")

    key_schedule = key_expansion(password)

    total_byte_length = 0
    encrypted_blocks = []

    # в режиме CBC результат шифрования каждого блока замешивается с предыдущим
    # самый первый блок замешивается с случайно сгенерированным масивом init_vector
    init_vector = generate_init_vector()
    previous_encrypted = init_vector

    with open(input_filename, mode="rb") as file:
        while True:
            part = [i for i in file.read(BLOCK_SIZE_BYTES)]
            total_byte_length += len(part)
            if len(part) == 0:  # конец файла
                break
            if len(part) < BLOCK_SIZE_BYTES:
                # дополняем блок до длины 16
                part += [0] * (BLOCK_SIZE_BYTES - len(part) - 1)
                part += [1]

            part = [part[j] ^ previous_encrypted[j] for j in range(len(part))]
            encrypted_block = encrypt_block(part, key_schedule)
            previous_encrypted = encrypted_block
            encrypted_blocks.extend(encrypted_block)

    with open(output_filename, mode='wb') as file:
        bytes_length = total_byte_length.to_bytes(FILE_LENGTH_BYTES_IN_HEADER, byteorder='big')
        # в зашифрованный файл перед данными записывается длина исходного зашифрованного файла и массив init_vector
        file.write(bytes_length)
        file.write(bytes(init_vector))
        file.write(bytes(encrypted_blocks))


def decrypt_block(block, key_schedule):
    """Дешифрует один блок, используя уже сгенерированный key_schedule.
    Превращает блок в массив 4x4 state и производит последовательно обратные трансформации.
    """
    state = block_to_state(block)
    state = add_round_key(state, key_schedule, ROUNDS_NUM)

    for round_num in range(ROUNDS_NUM - 1, 0, -1):
        state = shift_rows(state, inverse=True)
        state = sub_bytes(state, inverse=True)
        state = add_round_key(state, key_schedule, round_num)
        state = inv_mix_columns(state)

    state = shift_rows(state, inverse=True)
    state = sub_bytes(state, inverse=True)
    state = add_round_key(state, key_schedule, 0)
    return state_to_block(state)


def run_decrypt(input_filename, output_filename):
    """Основная процедура дешифровки файла.
    Считывает пароль, превращает его в ключ шифрования,
    дешифрует содержимое файла input_filename и выводит в файл output_filename, считывая блоками по 16 байт.
    """
    password = getpass.getpass("Password: ")
    if len(password) > 16:
        raise Exception("password too long, max len is 16 symbols")

    key_schedule = key_expansion(password)
    decrypted_blocks = []

    with open(input_filename, mode="rb") as file:
        # считываем сначала длину исходного файла, затем  массив init_vector
        file_length_bytes = file.read(FILE_LENGTH_BYTES_IN_HEADER)
        original_file_length = int.from_bytes(file_length_bytes, byteorder='big')
        init_vector = file.read(BLOCK_SIZE_BYTES)
        previous_encrypted = init_vector
        while True:
            part = file.read(BLOCK_SIZE_BYTES)[:]
            if len(part) == 0:  # конец файла
                break
            part_copy = part[:]
            decrypted_block = decrypt_block(part, key_schedule)
            decrypted_block = [decrypted_block[j] ^ previous_encrypted[j] for j in range(len(part))]
            previous_encrypted = part_copy
            decrypted_blocks.extend(decrypted_block)

    # обрезаем расшифрованные данные по размеру оригинального файла
    decrypted_blocks = decrypted_blocks[:original_file_length]
    with open(output_filename, mode='wb') as file:
        file.write(bytes(decrypted_blocks))


def main():
    parser = argparse.ArgumentParser(description="AES 128 CBC encrypt/decrypt tool.")
    parser.add_argument('-d', '--decrypt', action='store_true', dest='decrypt_mode', default=False)
    parser.add_argument('-in', action='store', dest='input_filename', type=str, required=True)
    parser.add_argument('-out', action='store', dest='output_filename', type=str, required=True)
    args = parser.parse_args()

    if args.decrypt_mode:
        run_decrypt(args.input_filename, args.output_filename)
    else:
        run_encrypt(args.input_filename, args.output_filename)


if __name__ == '__main__':
    main()
