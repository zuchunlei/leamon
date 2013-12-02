from socket import *


def test():
	sock = socket(AF_INET, SOCK_DGRAM)
	address = ('192.168.40.6', 12345)
	data = 'zuchunlei'
	while data != '':
		sock.sendto(data, address)
		data, address = sock.recvfrom(1000)
