# coding: utf-8
import requests


def main():
    requests.put('http://127.0.0.1:5000/put', {'foo': 'bar'})  # PUT
    requests.delete('http://127.0.0.1:5000/delete')  # DELETE
    requests.head('http://127.0.0.1:5000/get')  # HEAD
    requests.options('http://127.0.0.1:5000/options')  # OPTIONS

if __name__ == '__main__':
    main()
