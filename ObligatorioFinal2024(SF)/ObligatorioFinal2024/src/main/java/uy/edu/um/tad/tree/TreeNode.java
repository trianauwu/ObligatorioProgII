package uy.edu.um.tad.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.edu.um.tad.linkedlist.MyList;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeNode<V> {
    private V value;
    private TreeNode<V> left;
    private TreeNode<V> right;
    private int key;

    public TreeNode(V value, int key, TreeNode<V> right, TreeNode<V> left) {
        this.value = null;
        this.key = key;
        this.right = null;
        this.left = null;
    }

    public TreeNode(V value) {
        this.value = value;
    }

    public int depth() {
        if (left == null && right == null)
            return 0;

        int depthLeft = 0, depthRight = 0;
        if (left != null)
            depthLeft = left.depth();
        if (right != null)
            depthRight = right.depth();
        return Math.max(depthLeft, depthRight) + 1;
    }

    public int breadthSize() {
        if (left == null && right == null)
            return 1;
        int counter = 0;
        if (left != null)
            counter += left.breadthSize();
        if (right != null)
            counter += right.breadthSize();
        return counter;
    }

    public int size() {
        int size = 1;
        size += left == null ? 0 : left.size();
        size += right == null ? 0 : right.size();
        return size;
    }

    public void printPreOrden() {
        System.out.print(this.value.toString() + ",");
        if (left != null)
            left.printPreOrden();
        if (right != null)
            right.printPreOrden();
    }

    public void preorder(MyList<V> laLista) {
        laLista.add(this.value);
        if (left != null)
            left.preorder(laLista);
        if (right != null)
            right.preorder(laLista);
    }

    public void posorden(MyList<V> laLista) {
        if (left != null)
            left.posorden(laLista);
        if (right != null)
            right.posorden(laLista);
        laLista.add(this.value);
    }


    public void inorden(MyList<V> laLista) {
        if (left != null)
            left.inorden(laLista);
        laLista.add(this.value);
        if (right != null)
            right.inorden(laLista);

    }

    public void breathRecorrido(MyList<V> laLista) {
        if (left == null && right == null) {
            laLista.add(this.value);
            return;
        }
        if (left != null)
            left.breathRecorrido(laLista);
        if (right != null)
            right.breathRecorrido(laLista);
    }

    public boolean insert(V value, Comparator<V> comparator) {
        int compare = comparator.compare(value, this.value);
        if (compare == 0)
            return false;

        if (compare > 0) {
            if (right == null) {
                right = new TreeNode<>(value);
                return true;
            } else
                return right.insert(value, comparator);
        } else {
            if (left == null) {
                left = new TreeNode<>(value);
                return true;
            } else
                return left.insert(value, comparator);
        }

    }

    public TreeNode<V> search(V value, Comparator<Integer> comparator, int key) {
        int compare = comparator.compare(key, this.key);
        if (compare == 0)
            return this;

        if (compare > 0) {
            if (right != null) {
                if (key == right.key)
                    return right;
                else {
                    right = right.search(value, comparator, key);
                }
            }
        } else {
            if (left != null) {
                if (key == left.key)
                    return left;
                else {
                    left = left.search(value, comparator, key);
                }
            }
        }
        return null;
    }
}
