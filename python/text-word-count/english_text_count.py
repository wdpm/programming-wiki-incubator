import os
import re

current_dir = os.path.dirname(__file__)
text_path = '/scarborough fair.lrc'

with open(current_dir + text_path, 'r') as text:
    # 1.remove special punctuation and whitespace
    # 2.convert to lower case
    # string.punctuation:      !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
    punctuation = '[\s\\!"#$%&\'()*+,-./:;<=>?@[\]^_`{|}~]'
    words = [raw_word.lower() for raw_word in re.split(punctuation, text.read()) if raw_word != '']
    print(words)
    # 3.remove duplicate word
    words_set = set(words)
    counts_dict = {word: words.count(word) for word in words_set}
    for word in sorted(counts_dict, key=lambda x: counts_dict[x], reverse=True):
        print('{} -- {} times'.format(word, counts_dict[word]))
