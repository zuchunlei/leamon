from socket import *


def test():
	sock = socket(AF_INET, SOCK_DGRAM)
	address = ('192.168.40.6', 12345)
	sock.bind(address)
	data, addr = sock.recvfrom(1000)
	while data != '':
		sock.sendto(data, addr)
		data, addr = sock.recvfrom(1000)
