import re
import urllib

contents = []


class Search(object):
    global contents

    def __init__(self, url, reg):
        self.__url = url
        self.__reg = reg

    def find(self):
        page = urllib.urlopen(self.__url)
        html = page.read()
        page.close()

        for line in html:
            l = re.findall(line, self.__reg)
            if l:
                contents.append(l)


