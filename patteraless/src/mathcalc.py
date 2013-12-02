#-*- encoding:utf-8 -*-

"""
    该模块主要关心数学计算相关，提供对应的函数。
"""


def calc(n):
    """
    return the number is the give n :
    1^1 + 2^2 + 3^3 + ... + n^n
    """
    return sum([x ** x for x in xrange(n + 1)])


def calc2(n):
    """
    return the number is the give n :
    1^1 + 2^2 + 3^3 + ... + n^n
    """
    return reduce(lambda x, y: x + y, map(lambda x: x ** x, xrange(n + 1)))


def calc3(n):
    """
    return the number is the give n :
    1^1 + 2^2 + 3^3 + ... + n^n
    """
    return sum(map(lambda x: x ** x, xrange(n + 1)))

