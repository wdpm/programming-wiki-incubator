import os

from setuptools import find_packages, setup


def read_file(filename):
    basepath = os.path.dirname(os.path.dirname(__file__))
    filepath = os.path.join(basepath, filename)
    if os.path.exists(filepath):
        return open(filepath).read()
    else:
        return ''


setup(
    name='guestbook',
    version='1.0.0',
    description='A guestbook web application.',
    long_description=read_file('README.rst'),
    author='< 你的名字 >',
    author_email='< 你的邮箱地址 >',
    url='https://bitbucket.org/< 你的 Bitbucket 账户 >/guestbook/',
    classifiers=[
        'Development Status :: 4 - Beta',
        'Framework :: Flask',
        'License :: OSI Approved :: BSD License',
        'Programming Language :: Python',
        'Programming Language :: Python :: 2.7',
    ],
    packages=find_packages(),
    include_package_data=True,
    keywords=['web', 'guestbook'],
    license='BSD License',
    install_requires=[
        'Flask',
    ],
    entry_points="""
     [console_scripts]
     guestbook = guestbook:main
     """,
)
