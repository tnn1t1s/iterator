cmake_minimum_required(VERSION 3.14)
project(collating_iterator VERSION 1.0.0 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Compiler warnings
if(MSVC)
    add_compile_options(/W4)
else()
    add_compile_options(-Wall -Wextra -Wpedantic)
endif()

# Header-only library
add_library(collating_iterator INTERFACE)
target_include_directories(collating_iterator INTERFACE
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:include>
)

# Testing
option(BUILD_TESTING "Build tests" ON)
if(BUILD_TESTING)
    include(FetchContent)

    FetchContent_Declare(
        googletest
        GIT_REPOSITORY https://github.com/google/googletest.git
        GIT_TAG release-1.12.1
    )

    set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
    FetchContent_MakeAvailable(googletest)

    enable_testing()

    add_executable(collating_iterator_test tests/test_collating_iterator.cpp)
    target_link_libraries(collating_iterator_test
        PRIVATE
            collating_iterator
            gtest_main
    )

    include(GoogleTest)
    gtest_discover_tests(collating_iterator_test)
endif()

# Benchmarking
option(BUILD_BENCHMARKS "Build benchmarks" OFF)
if(BUILD_BENCHMARKS)
    FetchContent_Declare(
        benchmark
        GIT_REPOSITORY https://github.com/google/benchmark.git
        GIT_TAG v1.8.3
    )

    set(BENCHMARK_ENABLE_TESTING OFF CACHE BOOL "" FORCE)
    FetchContent_MakeAvailable(benchmark)

    add_executable(collating_iterator_benchmark benchmarks/benchmark_collating_iterator.cpp)
    target_link_libraries(collating_iterator_benchmark
        PRIVATE
            collating_iterator
            benchmark::benchmark
    )
endif()
