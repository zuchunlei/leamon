from socket import *

conn = []

def connect(num=100):
    global conn
    addr = ('www.baidu.com',80)
    for i in xrange(num):
        sock = socket(AF_INET,SOCK_STREAM)
        sock.connect(addr)
        conn.append(sock)

def close():
	"""
	close the soclet connect
	"""
	global conn
	for connect in conn:
		conn.close()

