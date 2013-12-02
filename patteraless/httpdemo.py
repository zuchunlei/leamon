import httplib
import urllib

class D(object):

	def __init__(self,host,port=80):
		self.conn = httplib.HTTPConnection(host,port)

	def close(self):
		if self.conn:
			self.conn.close()

	def post(self,path):
		params = {'name':'祖春雷'}
		params = urllib.urlencode(params)
		headers = {"Content-type":"application/x-www-form-urlencoded","Accept":"text/plain"}  
		self.conn.request('GET',path,params,headers)

	def result(self):
		reply = self.conn.getresponse()
		print ' %s %s' % (reply.status,reply.reason)
		for line in reply.read():
			print line,
