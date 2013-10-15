#*-* coding:utf-8 -*-

import httplib


class HttpDemo(object):
    """
        this is a class named HttpDemo
    """

    def __init__(self, host, port=80):
        self.__conn = httplib.HTTPConnection(host, port)

    def request(self, path='/'):
        self.__conn.request('get', path)

    def result(self):
        reply = self.__conn.getresponse()
        data = reply.read()

        print data


if __name__ == '__main__':
    hd = HttpDemo('www.baidu.com')
    hd.request()
    hd.result()

