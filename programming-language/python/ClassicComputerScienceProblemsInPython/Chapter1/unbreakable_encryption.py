# unbreakable_encryption.py
# From Classic Computer Science Problems in Python Chapter 1
# Copyright 2018 David Kopec
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
from secrets import token_bytes
from typing import Tuple


def random_key(length: int) -> int:
    # generate length random bytes
    tb: bytes = token_bytes(length)
    # convert those bytes into a bit string and return it
    return int.from_bytes(tb, "big")


def encrypt(original: str) -> Tuple[int, int]:
    original_bytes: bytes = original.encode()
    dummy: int = random_key(len(original_bytes))
    original_key: int = int.from_bytes(original_bytes, "big")
    encrypted: int = original_key ^ dummy  # XOR
    return dummy, encrypted


def decrypt(key1: int, key2: int) -> str:
    decrypted: int = key1 ^ key2  # XOR
    # 在用整除操作（//）除以8之前，必须给解密数据的长度加上7，以确保
    # 能“向上舍入”，避免出现边界差一（off-by-one）错误。如果上述一次性密码
    # 本的加密过程确实有效，应该就能毫无问题地加密和解密Unicode字符串了
    temp: bytes = decrypted.to_bytes((decrypted.bit_length() + 7) // 8, "big")

    # here why not use (decrypted.bit_length() / 8)
    # '0b100101' => 6 bit, 1 byte,
    # 6/8 => 0
    # (6+7)//8 => 1 后可以修复这个问题。1~7范围的bit都可以得出: ([1-8]+7)//8 => 1
    # temp: bytes = decrypted.to_bytes(int(decrypted.bit_length() / 8), "big")
    return temp.decode()


if __name__ == "__main__":
    key1, key2 = encrypt("One Time Pad!")
    result: str = decrypt(key1, key2)
    print(result)
