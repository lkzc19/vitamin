import sys


if __name__ == '__main__':
    test_string = 'a' * 100
    single_string_size = sys.getsizeof(test_string)
    total_size = single_string_size * 10000 * 100

    print(f"单个字符串内存占用: {single_string_size} 字节")
    print(f"100万个字符串总内存占用: {total_size} 字节")
    print(f"100万个字符串总内存占用: {total_size / 1024 / 1024} MB")
