#!/usr/bin/python

"""
udp echo server
"""
from socket import *


class UDPEchoServer(object):
    """
    provide the echo server via udp transport protocol.
    """

    def __init__(self, address):
        self._sock = socket(AF_INET, SOCK_DGRAM)
        self._sock.bind(address)

    def service(self):
        while True:
            data, address = self._sock.recvfrom(1024)
            self._sock.sendto(data, address)

    def close(self):
        if self._sock:
            self._sock.close()

