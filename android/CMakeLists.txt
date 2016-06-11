cmake_minimum_required(VERSION 3.4.1)


add_library(midi SHARED
    src/main/cpp/midi.cpp)

target_link_libraries(midi OpenSLES log)