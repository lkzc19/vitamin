import base64


if __name__ == '__main__':
    # with open('static/1.png', 'rb') as file:
    #     byte_data = file.read()
    #     print(byte_data)

    with open('static/1.png', 'rb') as file:
        byte_data = file.read()
        base64_data = base64.b64encode(byte_data).decode('utf-8')

    print(base64_data)
