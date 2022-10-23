# coding:utf-8
import re
import os

current_dir = os.path.dirname(__file__)
text_path = '/刚好遇见你-李玉刚.lrc'

with open(current_dir + text_path, 'r') as text:
    # pick single chinese word one by one,like '刚','好','遇','见','你'
    pattern_chinese = re.compile(u"[\u4e00-\u9fff]")
    # return a list
    raw_words = pattern_chinese.findall(text.read())
    # return a tuple
    words_set = set(raw_words)
    # build a dict
    counts_dict = {word: raw_words.count(word) for word in words_set}
    for word in sorted(counts_dict,key=lambda x:counts_dict[x],reverse=True):
        print('{}--{} times'.format(word,counts_dict[word]))

