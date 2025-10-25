#include "collating_iterator.hpp"
#include <gtest/gtest.h>
#include <vector>
#include <string>
#include <algorithm>

using namespace iterator;

TEST(CollatingIteratorTest, EmptyRanges) {
    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges;
    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    EXPECT_EQ(iter, end);
    EXPECT_FALSE(iter.has_next());
}

TEST(CollatingIteratorTest, SingleRange) {
    std::vector<int> vec = {1, 2, 3};
    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec.begin(), vec.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result, std::vector<int>({1, 2, 3}));
}

TEST(CollatingIteratorTest, TwoSortedRanges) {
    std::vector<int> vec1 = {1, 3, 5};
    std::vector<int> vec2 = {2, 4, 6};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result, std::vector<int>({1, 2, 3, 4, 5, 6}));
}

TEST(CollatingIteratorTest, MultipleSortedRanges) {
    std::vector<int> vec1 = {1, 6, 11};
    std::vector<int> vec2 = {2, 7, 12};
    std::vector<int> vec3 = {3, 8, 13};
    std::vector<int> vec4 = {4, 9, 14};
    std::vector<int> vec5 = {5, 10, 15};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()},
        {vec3.begin(), vec3.end()},
        {vec4.begin(), vec4.end()},
        {vec5.begin(), vec5.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    std::vector<int> expected;
    for (int i = 1; i <= 15; ++i) {
        expected.push_back(i);
    }

    EXPECT_EQ(result, expected);
}

TEST(CollatingIteratorTest, WithDuplicates) {
    std::vector<int> vec1 = {1, 3, 5, 5};
    std::vector<int> vec2 = {2, 3, 5, 6};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result, std::vector<int>({1, 2, 3, 3, 5, 5, 5, 6}));
}

TEST(CollatingIteratorTest, SomeEmptyRanges) {
    std::vector<int> vec1 = {1, 3, 5};
    std::vector<int> vec2;
    std::vector<int> vec3 = {2, 4, 6};
    std::vector<int> vec4;

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()},
        {vec3.begin(), vec3.end()},
        {vec4.begin(), vec4.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result, std::vector<int>({1, 2, 3, 4, 5, 6}));
}

TEST(CollatingIteratorTest, AllEmptyRanges) {
    std::vector<int> vec1, vec2, vec3;

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()},
        {vec3.begin(), vec3.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    EXPECT_EQ(iter, end);
}

TEST(CollatingIteratorTest, SingleElementRanges) {
    std::vector<int> vec1 = {3};
    std::vector<int> vec2 = {1};
    std::vector<int> vec3 = {2};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()},
        {vec3.begin(), vec3.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result, std::vector<int>({1, 2, 3}));
}

TEST(CollatingIteratorTest, LargeK) {
    std::vector<std::vector<int>> vecs(100);
    for (size_t i = 0; i < 100; ++i) {
        for (int j = 0; j < 10; ++j) {
            vecs[i].push_back(static_cast<int>(i + j * 100));
        }
    }

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges;
    for (auto& vec : vecs) {
        ranges.push_back({vec.begin(), vec.end()});
    }

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    EXPECT_EQ(result.size(), 1000u);
    EXPECT_TRUE(std::is_sorted(result.begin(), result.end()));
}

TEST(CollatingIteratorTest, Strings) {
    std::vector<std::string> vec1 = {"apple", "cherry", "grape"};
    std::vector<std::string> vec2 = {"banana", "date", "fig"};

    std::vector<std::pair<std::vector<std::string>::iterator,
                          std::vector<std::string>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<std::string>::iterator>();

    std::vector<std::string> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    std::vector<std::string> expected = {"apple", "banana", "cherry", "date", "fig", "grape"};
    EXPECT_EQ(result, expected);
}

TEST(CollatingIteratorTest, UnevenLength) {
    std::vector<int> vec1 = {1, 4, 7, 10, 13, 16, 19};
    std::vector<int> vec2 = {2, 5};
    std::vector<int> vec3 = {3, 6, 9, 12, 15, 18};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()},
        {vec3.begin(), vec3.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    std::vector<int> result;
    while (iter != end) {
        result.push_back(*iter);
        ++iter;
    }

    std::vector<int> expected = {1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 13, 15, 16, 18, 19};
    EXPECT_EQ(result, expected);
}

TEST(CollatingIteratorTest, PostIncrement) {
    std::vector<int> vec1 = {1, 3};
    std::vector<int> vec2 = {2, 4};

    std::vector<std::pair<std::vector<int>::iterator, std::vector<int>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()}
    };

    auto iter = make_collating_iterator(ranges);
    auto end = collating_iterator_end<std::vector<int>::iterator>();

    EXPECT_EQ(*iter++, 1);
    EXPECT_EQ(*iter++, 2);
    EXPECT_EQ(*iter++, 3);
    EXPECT_EQ(*iter++, 4);
    EXPECT_EQ(iter, end);
}

TEST(CollatingIteratorTest, ArrowOperator) {
    std::vector<std::string> vec1 = {"apple"};
    std::vector<std::string> vec2 = {"banana"};

    std::vector<std::pair<std::vector<std::string>::iterator,
                          std::vector<std::string>::iterator>> ranges = {
        {vec1.begin(), vec1.end()},
        {vec2.begin(), vec2.end()}
    };

    auto iter = make_collating_iterator(ranges);

    EXPECT_EQ(iter->length(), 5u);
    ++iter;
    EXPECT_EQ(iter->length(), 6u);
}
