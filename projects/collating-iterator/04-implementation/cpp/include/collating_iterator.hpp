#pragma once

#include <algorithm>
#include <iterator>
#include <memory>
#include <queue>
#include <stdexcept>
#include <vector>

namespace iterator {

/**
 * CollatingIterator merges k sorted input ranges into a single sorted output sequence.
 * Uses a binary min-heap to achieve O(N log k) time complexity.
 *
 * @tparam Iterator Forward iterator type
 * @tparam Compare  Comparison function object type
 */
template<typename Iterator,
         typename Compare = std::less<typename std::iterator_traits<Iterator>::value_type>>
class CollatingIterator {
public:
    using value_type = typename std::iterator_traits<Iterator>::value_type;
    using reference = const value_type&;
    using pointer = const value_type*;
    using difference_type = std::ptrdiff_t;
    using iterator_category = std::input_iterator_tag;

private:
    struct HeapEntry {
        value_type element;
        Iterator current;
        Iterator end;
        size_t index;

        bool operator>(const HeapEntry& other) const {
            Compare comp;
            int cmp_result = comp(element, other.element) ? -1 :
                             comp(other.element, element) ? 1 : 0;
            if (cmp_result != 0) {
                return cmp_result > 0;
            }
            return index > other.index;
        }
    };

    std::priority_queue<HeapEntry,
                        std::vector<HeapEntry>,
                        std::greater<HeapEntry>> heap_;
    value_type current_value_;
    bool has_current_;

public:
    /**
     * Constructs a CollatingIterator from a vector of iterator pairs (begin, end).
     *
     * @param ranges Vector of (begin, end) iterator pairs, each representing a sorted range
     */
    explicit CollatingIterator(const std::vector<std::pair<Iterator, Iterator>>& ranges)
        : has_current_(false) {
        for (size_t i = 0; i < ranges.size(); ++i) {
            auto [begin, end] = ranges[i];
            if (begin != end) {
                heap_.push(HeapEntry{*begin, std::next(begin), end, i});
            }
        }
        advance();
    }

    /**
     * Default constructor creates an end iterator.
     */
    CollatingIterator() : has_current_(false) {}

    /**
     * Dereference operator.
     */
    reference operator*() const {
        if (!has_current_) {
            throw std::out_of_range("Dereferencing end iterator");
        }
        return current_value_;
    }

    /**
     * Arrow operator.
     */
    pointer operator->() const {
        if (!has_current_) {
            throw std::out_of_range("Dereferencing end iterator");
        }
        return &current_value_;
    }

    /**
     * Pre-increment operator.
     */
    CollatingIterator& operator++() {
        if (!has_current_) {
            throw std::out_of_range("Incrementing end iterator");
        }
        advance();
        return *this;
    }

    /**
     * Post-increment operator.
     */
    CollatingIterator operator++(int) {
        CollatingIterator temp = *this;
        ++(*this);
        return temp;
    }

    /**
     * Equality operator.
     */
    bool operator==(const CollatingIterator& other) const {
        return has_current_ == other.has_current_;
    }

    /**
     * Inequality operator.
     */
    bool operator!=(const CollatingIterator& other) const {
        return !(*this == other);
    }

    /**
     * Check if iterator is at end.
     */
    bool has_next() const {
        return has_current_;
    }

private:
    void advance() {
        if (heap_.empty()) {
            has_current_ = false;
            return;
        }

        HeapEntry entry = heap_.top();
        heap_.pop();

        current_value_ = entry.element;
        has_current_ = true;

        if (entry.current != entry.end) {
            heap_.push(HeapEntry{*entry.current,
                                std::next(entry.current),
                                entry.end,
                                entry.index});
        }
    }
};

/**
 * Helper function to create a CollatingIterator from multiple ranges.
 *
 * @tparam Iterator Forward iterator type
 * @param ranges Vector of (begin, end) iterator pairs
 * @return CollatingIterator instance
 */
template<typename Iterator>
CollatingIterator<Iterator> make_collating_iterator(
        const std::vector<std::pair<Iterator, Iterator>>& ranges) {
    return CollatingIterator<Iterator>(ranges);
}

/**
 * Helper function to create an end sentinel for CollatingIterator.
 *
 * @tparam Iterator Forward iterator type
 * @return End sentinel
 */
template<typename Iterator>
CollatingIterator<Iterator> collating_iterator_end() {
    return CollatingIterator<Iterator>();
}

} // namespace iterator
