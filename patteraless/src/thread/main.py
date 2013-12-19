#-*- encoding: utf-8 -*-

"""
多单线程运行一段程序
"""

import time
import thread


def loop0():
    """
    睡上几秒
    """
    print 'start loop 0 at: ', time.ctime()
    time.sleep(4)
    print 'loop 0 done at: ', time.ctime()


def loop1():
    """
    再睡上几秒
    """
    print 'start loop 1 at: ', time.ctime()
    time.sleep(2)
    print 'loop 1 done at: ', time.ctime()


def main():
    print 'starting at:', time.ctime()
    # 启动一个新的线程对loop函数进行执行
    thread.start_new_thread(loop0, ())
    thread.start_new_thread(loop1, ())
    print 'all Done at: ', time.ctime()


if __name__ == '__main__':
    main()
