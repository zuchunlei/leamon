#-*- encoding: utf-8 -*-

"""
this is the module doc str
"""

from socket import *


class Connection(object):
    """ this is class Connection """
    
    def __init__(self, host, port):
        sock = socket(host, port)
        self.__sock = sock

    def conn(self):
        pass

