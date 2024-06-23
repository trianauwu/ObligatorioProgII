package uy.edu.um.tad.tree;

import uy.edu.um.tad.linkedlist.MyList;

public interface BinaryTree<V> {

    /**
     * Máxima profundidad o altura del árbol
     * */
    public int depth();

    /**
     * Cantidad total de nodos
     * */
    public int size();

    /**
     * Número de hojas de un arbol
     * */
    public int breadthSize();

    /**
     * Devuelve una lista con los datos contenidos en las hojas
     * */
    public MyList<V> breadth();

    public MyList<V> preorder();
    public MyList<V> postorder();
    public MyList<V> inorder();

    public void printPreOrden();

    public V getRoot();
    public TreeNode<V> getRootNode();




}
