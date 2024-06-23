package uy.edu.um.tad.tree;

import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
public class BinarySearchTreeImpl<V> extends BinaryTreeImpl<V> implements BinarySearchTree<V>
{
    private Comparator<V> comparator;

    public BinarySearchTreeImpl(Comparator<V> comparator)
    {
        this.comparator = comparator;
    }

    @Override
    public boolean insert(V value) {
        if(root == null) {
            root = new TreeNode<>(value);
            return true;
        }
        return root.insert(value, comparator);
    }

    @Override
    public V remove(Comparable key) {
        return null;
    }

    @Override
    public V search(Comparable key) {
        return null;
    }

    public void setComparator(Comparator<Integer> comparator) {
    }
}

