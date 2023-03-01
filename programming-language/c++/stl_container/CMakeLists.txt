cmake_minimum_required(VERSION 3.21)
project(c_plus_basic/stl_container)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(c_plus_basic
        iterator.cpp
        sequence_container.cpp
        associative_container.cpp
        unordered_container.cpp
        container_adaptor.cpp
        )
