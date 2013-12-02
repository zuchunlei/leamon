# -*- encoding: utf-8 -*-


import urllib

"""
该模块用来显示客户的html代码的
"""


class view(object):
    """
    this class is view
    """

    def __init__(self, url):
        self.page = urllib.urlopen(url)

    def getHtml(self):
        html = self.page.read();
        self.page.close()
        return html


if __name__ == "__main__":
    view = view('http://baidu.com')
    print view.getHtml()

