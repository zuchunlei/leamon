#-*- encoding: utf-8 -*-

"""
io模块处理器
"""


class FilterChain(object):
    """
    过滤器链表
    """

    def __init__(self, handler):
        self.chain = []
        self.index = 0
        self.handler = handler

    def add_filter(self, filter_obj):
        self.chain.append(filter_obj)

    def remove_filter(self, filter_obj):
        self.chain.remove(filter_obj)

    def invoke(self):
        if self.index < len(self.chain):
            filter_obj = self.chain[self.index]
            self.index += 1
            filter_obj.do_filter()
            return

        self.handler.handle()


class Filter(object):
    """
    过滤器对象
    """
    counter = 0

    def __init__(self, chain_obj):
        Filter.counter += 1
        self.number = Filter.counter
        self.filter_chain = chain_obj

    def do_filter(self):
        print "这是拦截器: %d 执行过滤操作" % self.number

        # 前置通知
        self.do_before_filter()
        # 链式调用
        self.filter_chain.invoke()
        # 后置通知
        self.do_after_filter()

    def do_before_filter(self):
        pass

    def do_after_filter(self):
        pass


class Handler(object):
    """
    处理器
    """

    def __init__(self, host, port=80):
        self.address = (host, port)

    def handle(self):
        import socket

        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect(self.address)
        sock.send('bye !')
        sock.close()

        print "处理器开始执行"


if __name__ == '__main__':
    filterChain = FilterChain(Handler('163.com'))

    for i in xrange(4):
        filterChain.add_filter(Filter(filterChain))

    filterChain.invoke()