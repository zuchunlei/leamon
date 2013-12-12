#-*- encoding: utf-8 -*-

"""
猜数游戏，系统随机生成一个数字，用户输入进行猜测，并给出提示信息.
"""

import random

count = 0
number = random.randrange(0, 1000)

input_str = raw_input("please input a number: ")

while True:
    client_input = int(input_str)
    if client_input > number:
        print "你输入的数字比真实数据大！"
    elif client_input < number:
        print "你输入的数字比真实数据小！"
    else:
        print "恭喜你，答对了！！"
        print "你一共使用 %d 次，就猜对了！" % count
        break

    count += 1
    input_str = raw_input("please input a number: ")