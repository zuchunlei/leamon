# -*- encoding: utf-8 -*-

from socket import *
from time import *

server_socket = socket(AF_INET, SOCK_STREAM)
address = ('localhost', 60000)
server_socket.bind(address)
server_socket.listen(100)

client, client_address = server_socket.accept()

time.sleep(10)
#此时client对端的socket网络连接已经关闭。

client.send('123')
#向关闭的网络连接发送数据，此时返回RST，通知socket已经不可用了。
#此时两端的socket网络连接已经消逝，操作系统内核中已经没有socket结构。


client.send('123')
#在次向关闭的网络连接中发送数据，得到信号SIGPIPE，也就是broken pipe。
