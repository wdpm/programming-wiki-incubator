cmake_minimum_required(VERSION 3.21)
project(c_plus_basic/basic)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(c_plus_basic
#        pointer_and_ref.cpp
#        struct.cpp
#        pointer_offset.cpp
#        swap.cpp
#        dynamic_malloc.cpp
#        two_dim_array.cpp
#        fun_pointer.cpp
        fun_definition.cpp
        file_ops.cpp
        fstream.cpp
        )
