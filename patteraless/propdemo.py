#-*- encoding: utf-8 -*-


class D(object):
	"""
	测试property函数
	"""

	def __init__(self, name):
		self.__name = name

	def getx(self):
		print "getx is calling now!"
		return self.__name

	def setx(self, value):
		print "setx is calling now!"
		self.__name = value

	content = property(getx, setx, doc = 'this is a doc str!')


class T(object):
	"""
	测试property的注解形式
	"""

	def __init__(self, name):
		self.__x = name

	@property
	def content(self):
		print "x is calling now !"
		return self.__x

	@content.setter
	def content(self, value):
		print "y is calling now !"
		self.__x = value

	@content.deleter
	def content(self):
		print "del is calling now !"
		del self.__x


class S(object):
	"""
	测试特殊方法
	"""

	def __init__(self):
		self.array = {}

	def __setitem__(self, key, value):
		print "set item is calling now!"
		self.array[key] = value

	def __getitem__(self, key):
		print "get item is calling now!"
		return self.array[key]