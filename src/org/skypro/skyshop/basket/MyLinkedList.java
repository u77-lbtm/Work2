package org.skypro.skyshop.basket;
import java.util.ArrayList;
import java.util.List;

public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    public void add (E element){
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(element);
        newNode.prev = l;
        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }
    public List<E> toList() {
        List<E> result = new ArrayList<>();
        Node<E> current = first;
        while (current != null) {
            result.add(current.item);
            current = current.next;
        }
        return result;
    }
    //      элементуказанного узла prevNode

    public Node<E> insertAfter(Node<E> prevNode, E element) {
        if (prevNode == null) {
            throw new IllegalArgumentException("Предыдущий узел не может быть null");
        }
        // новый узел
        Node<E> newNode = new Node<>(element);
        // связи для нового узла
        newNode.prev = prevNode;
        newNode.next = prevNode.next;

        // связь следующего узла
        if (prevNode.next != null) {
            prevNode.next.prev = newNode;
        } else {
            // tеперь хвостом списка становится наш новый узел!
            last = newNode;
        }
        //  ссылкa next у prevNode на новый узел
        prevNode.next = newNode;
        size++;
        return newNode;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }
}
