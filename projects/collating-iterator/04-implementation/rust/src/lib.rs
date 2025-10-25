use std::cmp::Ordering;
use std::collections::BinaryHeap;

/// CollatingIterator merges k sorted iterators into a single sorted output stream.
/// Uses a binary min-heap to achieve O(N log k) time complexity.
///
/// # Type Parameters
/// * `T` - Element type, must implement Ord
/// * `I` - Iterator type
///
/// # Examples
///
/// ```
/// use collating_iterator::CollatingIterator;
///
/// let vec1 = vec![1, 3, 5];
/// let vec2 = vec![2, 4, 6];
///
/// let iterators = vec![vec1.into_iter(), vec2.into_iter()];
/// let collating = CollatingIterator::new(iterators);
///
/// let result: Vec<i32> = collating.collect();
/// assert_eq!(result, vec![1, 2, 3, 4, 5, 6]);
/// ```
pub struct CollatingIterator<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    heap: BinaryHeap<HeapEntry<T, I>>,
}

struct HeapEntry<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    element: T,
    iterator: I,
    index: usize,
}

impl<T, I> PartialEq for HeapEntry<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    fn eq(&self, other: &Self) -> bool {
        self.element == other.element && self.index == other.index
    }
}

impl<T, I> Eq for HeapEntry<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
}

impl<T, I> PartialOrd for HeapEntry<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl<T, I> Ord for HeapEntry<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    fn cmp(&self, other: &Self) -> Ordering {
        other
            .element
            .cmp(&self.element)
            .then_with(|| other.index.cmp(&self.index))
    }
}

impl<T, I> CollatingIterator<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    /// Creates a new CollatingIterator from a vector of sorted iterators.
    ///
    /// # Arguments
    /// * `iterators` - Vector of sorted iterators
    ///
    /// # Examples
    ///
    /// ```
    /// use collating_iterator::CollatingIterator;
    ///
    /// let vec1 = vec![1, 3, 5];
    /// let vec2 = vec![2, 4, 6];
    ///
    /// let iterators = vec![vec1.into_iter(), vec2.into_iter()];
    /// let collating = CollatingIterator::new(iterators);
    ///
    /// let result: Vec<i32> = collating.collect();
    /// assert_eq!(result, vec![1, 2, 3, 4, 5, 6]);
    /// ```
    pub fn new(iterators: Vec<I>) -> Self {
        let mut heap = BinaryHeap::with_capacity(iterators.len());

        for (index, mut iter) in iterators.into_iter().enumerate() {
            if let Some(element) = iter.next() {
                heap.push(HeapEntry {
                    element,
                    iterator: iter,
                    index,
                });
            }
        }

        CollatingIterator { heap }
    }
}

impl<T, I> Iterator for CollatingIterator<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    type Item = T;

    fn next(&mut self) -> Option<Self::Item> {
        if let Some(mut entry) = self.heap.pop() {
            let result = entry.element;

            if let Some(next_element) = entry.iterator.next() {
                entry.element = next_element;
                self.heap.push(entry);
            }

            Some(result)
        } else {
            None
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_empty_iterators() {
        let iterators: Vec<std::vec::IntoIter<i32>> = vec![];
        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert!(result.is_empty());
    }

    #[test]
    fn test_single_iterator() {
        let vec = vec![1, 2, 3];
        let iterators = vec![vec.into_iter()];
        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert_eq!(result, vec![1, 2, 3]);
    }

    #[test]
    fn test_two_sorted_iterators() {
        let vec1 = vec![1, 3, 5];
        let vec2 = vec![2, 4, 6];
        let iterators = vec![vec1.into_iter(), vec2.into_iter()];
        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert_eq!(result, vec![1, 2, 3, 4, 5, 6]);
    }

    #[test]
    fn test_multiple_sorted_iterators() {
        let vec1 = vec![1, 6, 11];
        let vec2 = vec![2, 7, 12];
        let vec3 = vec![3, 8, 13];
        let vec4 = vec![4, 9, 14];
        let vec5 = vec![5, 10, 15];

        let iterators = vec![
            vec1.into_iter(),
            vec2.into_iter(),
            vec3.into_iter(),
            vec4.into_iter(),
            vec5.into_iter(),
        ];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        let expected: Vec<i32> = (1..=15).collect();
        assert_eq!(result, expected);
    }

    #[test]
    fn test_with_duplicates() {
        let vec1 = vec![1, 3, 5, 5];
        let vec2 = vec![2, 3, 5, 6];
        let iterators = vec![vec1.into_iter(), vec2.into_iter()];
        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert_eq!(result, vec![1, 2, 3, 3, 5, 5, 5, 6]);
    }

    #[test]
    fn test_some_empty_iterators() {
        let vec1 = vec![1, 3, 5];
        let vec2 = vec![];
        let vec3 = vec![2, 4, 6];
        let vec4 = vec![];

        let iterators = vec![
            vec1.into_iter(),
            vec2.into_iter(),
            vec3.into_iter(),
            vec4.into_iter(),
        ];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert_eq!(result, vec![1, 2, 3, 4, 5, 6]);
    }

    #[test]
    fn test_all_empty_iterators() {
        let vec1: Vec<i32> = vec![];
        let vec2: Vec<i32> = vec![];
        let vec3: Vec<i32> = vec![];

        let iterators = vec![vec1.into_iter(), vec2.into_iter(), vec3.into_iter()];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert!(result.is_empty());
    }

    #[test]
    fn test_single_element_iterators() {
        let vec1 = vec![3];
        let vec2 = vec![1];
        let vec3 = vec![2];

        let iterators = vec![vec1.into_iter(), vec2.into_iter(), vec3.into_iter()];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();
        assert_eq!(result, vec![1, 2, 3]);
    }

    #[test]
    fn test_large_k() {
        let mut vecs = Vec::new();
        for i in 0..100 {
            let mut vec = Vec::new();
            for j in 0..10 {
                vec.push(i + j * 100);
            }
            vecs.push(vec);
        }

        let iterators: Vec<_> = vecs.into_iter().map(|v| v.into_iter()).collect();
        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();

        assert_eq!(result.len(), 1000);
        assert!(result.windows(2).all(|w| w[0] <= w[1]));
    }

    #[test]
    fn test_strings() {
        let vec1 = vec!["apple", "cherry", "grape"];
        let vec2 = vec!["banana", "date", "fig"];

        let iterators = vec![vec1.into_iter(), vec2.into_iter()];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<&str> = collating.collect();

        assert_eq!(
            result,
            vec!["apple", "banana", "cherry", "date", "fig", "grape"]
        );
    }

    #[test]
    fn test_uneven_length() {
        let vec1 = vec![1, 4, 7, 10, 13, 16, 19];
        let vec2 = vec![2, 5];
        let vec3 = vec![3, 6, 9, 12, 15, 18];

        let iterators = vec![vec1.into_iter(), vec2.into_iter(), vec3.into_iter()];

        let collating = CollatingIterator::new(iterators);
        let result: Vec<i32> = collating.collect();

        assert_eq!(result, vec![1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 13, 15, 16, 18, 19]);
    }
}
