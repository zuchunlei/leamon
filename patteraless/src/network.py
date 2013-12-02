import urllib


class NetWork(object):
    """ this class named NetWork """
    conn = []

    def __init__(self, url, number=100):
        """
            constructor for the instance of NetWork
        """
        self.__url = url
        self.__number = number

    def connect(self):
        """
            create the connection of the url
        """
        for i in xrange(self.__number):
            connection = urllib.urlopen(self.__url)
            NetWork.conn.append(connection)

    def close(self):
        tmpconn = []
        """ close the to url connection """
        for connection in NetWork.conn:
            if connection.geturl() == self.__url:
                connection.close()
                tmpconn.append(connection)

        for connection in tmpconn:
            NetWork.conn.remove(connection)
