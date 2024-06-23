package uy.edu.um.tad.tree;

public interface BinarySearchTree<V> extends BinaryTree<V> {

    public boolean insert(V value);
    public V remove(Comparable key);
    public V search(Comparable key);


}
