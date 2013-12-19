#-*- encoding: utf-8 -*-


"""
使用锁对象来实现多线程的同步
"""

import time
import thread


def loop(num, sec, lock_obj):
    """
    循环程序，sec为沉睡秒数
    """
    print 'start loop %d at: %s' % (num, time.ctime())
    time.sleep(sec)
    print 'loop  %d done at: %s' % (num, time.ctime())
    lock_obj.release()


if __name__ == '__main__':
    loop_times = [2, 4]
    locks = []
    for x in xrange(len(loop_times)):
        lock = thread.allocate_lock()
        locks.append(lock)
        lock.acquire()
    print 'main starting at: %s' % time.ctime()
    for x in xrange(len(loop_times)):
        thread.start_new_thread(loop, (x, loop_times[x], locks[x]))

    for lock in locks:
        while lock.locked():
            pass

    print 'main done at: %s' % time.ctime()