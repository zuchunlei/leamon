"""
calc module
"""


def calc(n):
	"""
		1^1 + 2^2 + 3^3 + .... + n^n
	"""
	return sum([x**x for x in xrange(n+1)])


def calc2(n):
	"""
		1^1 + 2^2 + 3^3 + .... + n^n
	"""
	return reduce(lambda x,y: x+y, map(lambda x:x**x ,xrange(n+1)))



