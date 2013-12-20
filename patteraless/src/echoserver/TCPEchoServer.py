#-*- encoding:utf-8 -*-

import socket
import thread


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
        # 采用多线程方式执行handle方法，运行多用户同时访问。
        # self.handle(connection)
        thread.start_new_thread(self.handle, (connection,))
        # self.stop()

    def handle(self, connection):
        while True:
            try:
                data = connection.recv(self._size)
            except IOError, e:
                print '%s ' % e
            if data == 'bye':
                break
            print 'data is %s,and len is %d' % (data, len(data))
            connection.send(data)
        connection.close()

    def stop(self):
        self._sock.close()


if __name__ == "__main__":
    server = EchoServer("192.168.1.90")
    while True:
        server.service()


