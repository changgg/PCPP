package test;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    public void push(E item){
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do{
            oldHead = top.get();
            newHead.next = oldHead;
        }while(!top.compareAndSet(oldHead,newHead));
    }
    public E pop(){
        Node<E> newHead;
        Node<E> oldHead;
        do{
            oldHead = top.get();
            if(oldHead == null) return null;
            newHead = oldHead.next;
        }while(!top.compareAndSet(oldHead,newHead));
        return oldHead.item;
    }
    private static class Node<E> {
        public final E item;
        public Node<E> next;

        private Node(E item) {
            this.item = item;
        }
    }
}
