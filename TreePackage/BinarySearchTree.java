package TreePackage;
/**
   A class that implements the ADT binary search tree by extending BinaryTree.
   Recursive version.

 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> implements SearchTreeInterface<T> {

	public BinarySearchTree() {
		super();
	} 

	public BinarySearchTree(T rootEntry) {
		super();
		setRootNode(new BinaryNode<>(rootEntry));
	} 

	public void setTree(T rootData)  {
		throw new UnsupportedOperationException();
	} 

	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, 
			BinaryTreeInterface<T> rightTree) {
		throw new UnsupportedOperationException();
	} 

	// Recursively finds the given entry in the binary tree rooted at the given node.
	private T findEntry(BinaryNode<T> rootNode, T entry){	
		T result = null;
		if (rootNode == null) {
			return null;
		} else if (entry == rootNode.getData()){
			return rootNode.getData();
		} else if ( entry.compareTo(rootNode.getData()) < 0) {
			
			return findEntry(rootNode.getLeftChild(), entry);
		} else if ( entry.compareTo(rootNode.getData()) > 0) {
			
			return findEntry(rootNode.getRightChild(), entry);
		}
		return result;
	} 

	public T getEntry(T entry){
		return findEntry(getRootNode(), entry);
	} 

	public boolean contains(T entry) {
		return getEntry(entry) != null;
	} 

	public T add(T newEntry) {
		T result = null;

		if (isEmpty())
			setRootNode(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);
		return result;
	} 

	// Adds newEntry to the nonempty subtree rooted at rootNode.
	private T addEntry(BinaryNode<T> rootNode, T newEntry) {
		assert rootNode != null;
		T result = null;
		int comparison = newEntry.compareTo(rootNode.getData());

		if (comparison == 0)	{
			result = rootNode.getData();
		}
		else if (comparison < 0)	{
			if (rootNode.hasLeftChild()) {
				result = addEntry(rootNode.getLeftChild(), newEntry);
			} else {
				rootNode.setLeftChild(new BinaryNode<>(newEntry));
			}
		}
		else if ( comparison > 0 ){
			if (rootNode.hasRightChild()) {
				result = addEntry(rootNode.getRightChild(), newEntry);
			} else {
				rootNode.setRightChild(new BinaryNode<>(newEntry));
			}
		} 
		return result;
	} 

	public T remove(T entry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRootNode(), entry, oldEntry);
		setRootNode(newRoot);
		return oldEntry.get();
	} 

	// Removes an entry from the tree rooted at a given node.
	// Parameters: 	rootNode  A reference to the root of a tree.
	//    			entry     The object to be removed.
	//    			oldEntry  An object whose data field is null.
	// Returns:  The root node of the resulting tree; if entry matches
	//           an entry in the tree, oldEntry's data field is the entry
	//           that was removed from the tree; otherwise it is null.
	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry, ReturnObject oldEntry) {
		if (rootNode != null) {
			T rootData = rootNode.getData();
			int comparison = entry.compareTo(rootData);

			if (comparison == 0)  {     // entry == root entry
				oldEntry.set(rootNode.getData());
				rootNode = removeFromRoot(rootNode);
			}
			else if (comparison < 0) {  // entry < root entry
				BinaryNode<T> leftChild = rootNode.getLeftChild() /*TODO*/;
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, entry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
			}
			else  {                     // entry > root entry
				BinaryNode<T> rightChild = rootNode.getRightChild()/*TODO*/;
				rootNode.setRightChild(removeEntry(rightChild, entry, oldEntry));
			} 
		} 
		return rootNode;
	} 

	// Removes the entry in a given root node of a subtree.
	// Parameter:  rootNode  A reference to the root of the subtree.
	// Returns:    The root node of the revised subtree.
	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: rootNode has two children 
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find node with largest entry in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

			// Replace entry in root
			rootNode.setData(largestNode.getData());

			// Remove node with largest entry in left subtree
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		} 

		// Case 2: rootNode has at most one child
		else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();
		return rootNode; 
	} 

	// Finds the node containing the largest entry in a given tree.
	// Parameter:
	//    rootNode  A reference to the root node of the tree.
	// Returns:  The node containing the largest entry in the tree.
	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = findLargest(rootNode.getRightChild());
		return rootNode;
	} 

	// Removes the node containing the largest entry in a given tree.
	// Parameter:rootNode  A reference to the root node of the tree.
	// Returns:  The root node of the revised tree.
	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
		}
		else 
			rootNode = rootNode.getLeftChild();
		return rootNode;
	} 
	
	// An object input parameter to the removeEntry method that allows
	// for the return of the element stored in the removed node.
	private class ReturnObject{
		private T item;

		private ReturnObject(T entry){
			item = entry;
		} 

		public T get(){
			return item;
		} 

		public void set(T entry){
			item = entry;
		} 
	} 
} 