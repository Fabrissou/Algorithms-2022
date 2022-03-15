package lesson3;

import java.util.*;
import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// attention: Comparable is supported but Comparator is not
public class BinarySearchTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    public Node<T> getRoot() {
        return root;
    }

    Node<T> findPrevious(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return findPrevious(root, start.value);
            if (start.left.value == value) {
                return start;
            } else return findPrevious(start.left, value);
        }
        else {
            if (start.right == null) return findPrevious(root, start.value);
            if (start.right.value == value) {
                return start;
            } else return findPrevious(start.right, value);
        }
    }

//make this private
    static class Node<T> {
        T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    /**
     * Добавление элемента в дерево
     *
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     *
     * Спецификация: {@link Set#add(Object)} (Ctrl+Click по add)
     *
     * Пример
     */
    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    /**
     * Удаление элемента из дерева
     *
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     */
    //    Трдоемкость: O(log(N))
//    Ресурсоемкость: T(1)
    @Override
    public boolean remove(Object o) {
        if (!(contains(o))) return false;
        @SuppressWarnings("unchecked")
        T t = (T) o;

        root = deleteNode(root, t);
        size--;
        return true;
    }

//    проверяем 4 случая: у узла нет дочерних узлов, eсть левый дочерний узел, есть правый дочерний узел, есть оба дочерних узла.
    private Node<T> deleteNode(Node<T> currentNode, T value) {
        int comparison = value.compareTo(currentNode.value);

        if (comparison == 0) {
            Node<T> left = currentNode.left;
            Node<T> right = currentNode.right;
            T v = currentNode.value;

            if (left == null && right == null) {
                return null;
            }

            if (left == null) {
                return right;
            }

            if (right == null) {
                return left;
            }

            Node<T> minInRightSubtree = findMinElement(right);
            currentNode.value = minInRightSubtree.value;
            currentNode.right = deleteNode(currentNode.right, minInRightSubtree.value);
            return currentNode;
        }

        if (comparison < 0) {
            currentNode.left = deleteNode(currentNode.left, value);
            return currentNode;
        }

        if (comparison > 0) {
            currentNode.right = deleteNode(currentNode.right, value);
            return currentNode;
        }

        return currentNode;
    }

    private Node<T> findMinElement(Node<T> start) {
        if (start == null) {
            return null;
        }
        if (start.left == null) return start;
        return findMinElement(start.left);
    }

    private Node<T> findMaxElement(Node<T> start) {
        if (start == null) {
            return null;
        }
        if (start.right == null) return start;
        return findMaxElement(start.right);
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinarySearchTreeIterator();
    }

    public class BinarySearchTreeIterator implements Iterator<T> {
        private Node<T> currentNode;
        private Node<T> maxNode = findMaxElement(root);

        private BinarySearchTreeIterator() {

        }

        /**
         * Проверка наличия следующего элемента
         *
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         *
         * Спецификация: {@link Iterator#hasNext()} (Ctrl+Click по hasNext)
         *
         * Средняя
         */
//    Трдоемкость: O(1)
//    Ресурсоемкость: T(1)
        @Override
        public boolean hasNext() {
            if (root == null) {
                return false;
            } else {
                if (currentNode == null) {
                    return true;
                }
            }
            return currentNode.value != maxNode.value;
        }

        /**
         * Получение следующего элемента
         *
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         *
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         *
         * Спецификация: {@link Iterator#next()} (Ctrl+Click по next)
         *
         * Средняя
         */
        private Node<T> findNextElement(Node<T> currentNode, Node<T> start) {
            if (currentNode.right != null) {
                return findMinElement(currentNode.right);
            } else {
                int compare = currentNode.value.compareTo(findPrevious(root, currentNode.value).value);

                if (compare < 0) {
                    if (currentNode.right != null) {
                        return findMinElement(currentNode.right);
                    } else {
                        return findPrevious(root, currentNode.value);
                    }
                } else {
                    while (compare > 0) {
                        currentNode = findPrevious(root, currentNode.value);
                        compare = currentNode.value.compareTo(findPrevious(root, currentNode.value).value);
                    }

                    if (currentNode.right != null) {
                        return findPrevious(root, currentNode.value);
                    }
                }


                return currentNode;
            }
        }

        //    Трдоемкость: O(log(N))
//    Ресурсоемкость: T(1)
        @Override
        public T next() {
            if (currentNode == null) {
                currentNode = findMinElement(root);
                return currentNode.value;
            }
            if (hasNext()) {
                currentNode = findNextElement(currentNode, root);
                return currentNode.value;
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * Удаление предыдущего элемента
         *
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         *
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         *
         * Спецификация: {@link Iterator#remove()} (Ctrl+Click по remove)
         *
         * Сложная
         */
//    Трдоемкость: O(log(N))
//    Ресурсоемкость: T(1)
        @Override
        public void remove() {
            if (!((currentNode != null) && (BinarySearchTree.this.remove(currentNode.value)))) {
                throw new IllegalStateException();
            }

        }
    }

    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#subSet(Object, Object)} (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#headSet(Object)} (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: {@link SortedSet#tailSet(Object)} (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

}