#-*- coding: utf-8 -*-
import socket


class NetWorkConnection(object):
    """
    the class is a net connection
    """
    def __init__(self, host, port):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        address = (host, port)
        self.sock.connect(address)

    def send(self, path):
        try:
            fd = open(path)
            data = fd.read()
            self.sock.send(data)
        except IOError, e:
            print '%s' % e

    def close(self):
        self.sock.close()


if __name__ == '__main__':
    nwc = NetWorkConnection('192.168.40.6', 8000)
    nwc.send('f://1.txt')
    nwc.close()
