import socket


class Conn(object):
    def __init__(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def listen(self, port=12345, backlog=10):
        address = ('192.168.40.6', port)
        self.sock.bind(address)
        self.sock.listen(backlog)

    def service(self):
        cli, addr = self.sock.accept()
        cli.send('zuchunlei is a good boy! OK!')
        print '%s ,%s' % (addr[0], str(addr[-1]))
        cli.close()

    def close(self):
        self.sock.close()

