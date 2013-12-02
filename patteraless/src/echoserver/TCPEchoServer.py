#-*- encoding:utf-8 -*-

import socket


class EchoServer(object):
    """
    TCP Protocol Echo Server Implements
    """

    def __init__(self, host='localhost', port=12345, backlog=100, size=1024):
        self._sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._sock.bind((host, port))
        self._sock.listen(backlog)
        self._size = size

    def service(self):
        """
        服务方法
        """
        connection, client = self._sock.accept()
        print "host is %s and port is %d" % (client[0], client[1])
        self.handle(connection)
        # self.stop()

    def handle(self, connection):
        try:
            data = connection.recv(self._size)
        except IOError, e:
            print '%s ,' % e
        connection.send(data)
        print '%s , %d' % (data, len(data))
        connection.close()

    def stop(self):
        self._sock.close()


if __name__ == "__main__":
    server = EchoServer()
    while True:
        server.service()


