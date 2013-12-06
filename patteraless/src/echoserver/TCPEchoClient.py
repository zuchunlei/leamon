#-*- encoding: utf-8 -*-


import socket


class TCPEchoClient(object):
    """
    TCP echo client，communication the echo server via tcp/ip socket
    """

    def __init__(self, host, port):
        address = (host, port)
        self.__sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.__sock.connect(address)

    def send_data(self, data):
        if data:
            self.__sock.send(data)

    def recv_data(self, size=1024):
        if self.__sock:
            return self.__sock.recv(size)

    def close(self):
        if self.__sock:
            self.__sock.close()


if __name__ == '__main__':
    cli = TCPEchoClient('localhost', 12345)
    data = 'zuchunlei is a good pythoner!!'
    cli.send_data(data)
    result = cli.recv_data()
    print result
    data = 'bye'
    cli.send_data(data)
    cli.close()
