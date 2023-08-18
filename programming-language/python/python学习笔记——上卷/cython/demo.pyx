# distutils: language=c++
# cython: language_level=3

cdef extern from "hello.h":
    void hello(char *)
hello("abc")
