#-*- encoding: utf-8 -*-


"""
测试with语句的上下文管理器
"""


class Wither(object):
    def __init__(self, name, password):
        self.name = name
        self.password = password

    def __enter__(self):
        print "in enter"
        return self.name

    def __exit__(self, t, v, b):
        print "in exit"
        return self.password


if __name__ == '__main__':
    w = Wither('zcl', 123)
    with w as f:
        print "in with"

