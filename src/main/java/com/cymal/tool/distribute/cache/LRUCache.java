package com.cymal.tool.distribute.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, Node> cache;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public V get(K key) {
        Node node = cache.get(key);
        if (node != null) {
            // 将访问的节点移到链表头部
            moveToHead(node);
            return node.value;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Node node = cache.get(key);
        if (node == null) {
            // 缓存中不存在该节点，需创建新节点并添加到链表头部和缓存中
            node = new Node(key, value);
            cache.put(key, node);
            addToHead(node);

            if (cache.size() > capacity) {
                // 缓存已满，需要移除最近最少使用的节点（尾部节点）
                Node lastNode = removeTail();
                cache.remove(lastNode.key);
            }
        } else {
            // 缓存中存在该节点，更新节点值，并将节点移到链表头部
            node.value = value;
            moveToHead(node);
        }
        return value;
    }

    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        Node lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }

    private class Node {
        private K key;
        private V value;
        private Node prev;
        private Node next;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
