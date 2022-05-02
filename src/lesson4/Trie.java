package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        private String currentString;
        private final ArrayDeque<String> stack;

//    Трдоемкость: O(N)
//    Ресурсоемкость: T(N)
        private TrieIterator() {
            stack = new ArrayDeque<>();
            stackPush(root, "");
        }

        private void stackPush(Node node, String word) {
            for (char child: node.children.keySet()) {
                if (child == (char) 0) {
                    stack.push(word);
                } else {
                    stackPush(node.children.get(child), word + child);
                }
            }
        }

//    Трдоемкость: O(1)
//    Ресурсоемкость: T(1)
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

//    Трдоемкость: O(1)
//    Ресурсоемкость: T(1)
        @Override
        public String next() {
            if (hasNext()) {
                currentString = stack.pop();
                return currentString;
            } else {
                throw new NoSuchElementException();
            }
        }

//    Трдоемкость: O(N)
//    Ресурсоемкость: T(N)
        @Override
        public void remove() {
            if (currentString == null) {throw new IllegalStateException();}
            Trie.this.remove(currentString);
            currentString = null;
        }
    }
}