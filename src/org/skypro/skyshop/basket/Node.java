package org.skypro.skyshop.basket;

public class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    public Node(E item) {
        this.item = item;
    }
}
