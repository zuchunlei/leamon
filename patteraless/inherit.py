#-*- encoding: utf-8 -*-


"""
python类继承测试
"""


class D(object):

	def __init__(self, name):
		self.name = name

	def __str__(self):
		return "this is class D doc string"

	def __repr__(self):
		return "this is class D repr string"

	def say(self):
		return self.name


class DD(D):

	def __init__(self, name, age, sex):
		super(DD, self).__init__(name)
		self.age = age
		self.sex = sex
	
	# override
	def say(self):
		return "%s %s %s" % (self.name, self.age, self.sex)

