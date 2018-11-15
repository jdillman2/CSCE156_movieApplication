package lists;

public class ListNode<E> {
	private E object;
	private ListNode<E> nextNode;
	
	public ListNode() {
		this.nextNode = null;
		this.object = null;
	}
	
	public ListNode(E object) {
		this.object = object;
	}

	public E getObject() {
		return object;
	}

	public void setObject(E object) {
		this.object = object;
	}

	public ListNode<E> getNextNode() {
		return nextNode;
	}

	public void setNextNode(ListNode<E> nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
