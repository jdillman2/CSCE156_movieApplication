package lists;

import java.util.Iterator;

public class LinkedList<E> extends TotalComparator<E> implements Iterable<E> {
	private ListNode<E> start;
	private ListNode<E> end;
	private int size;
	
    public void clear() {
    	this.start = null;
    	this.end = null;
    	size = 0;
    }

    public void addToStart(E object) {
    	ListNode<E> aNode = new ListNode<E>(object);
    	size++;
    	if(start == null) {
    		start = aNode;
    		end = start;
    	}else {
    		aNode.setNextNode(start);
    		start = aNode;
    	}
    }

    public void addToEnd(E Object) {
    	ListNode<E> aNode = new ListNode<E>(Object);
    	size++;
    	if(start == null) {
    		start = aNode;
    		end = start;
    	}else {
    		end.setNextNode(aNode);
    		end = aNode;
    	}
    }
    
    

    public void addInvoiceOrdered(E Object) {
    	ListNode<E> aNode = new ListNode<E>(Object);
    	ListNode<E> currentNode = start;
    	TotalComparator<E> c = new TotalComparator<E>();
    	if(start == null) {
    		this.addToStart(Object);
    	}else if(size == 1) {
    		int comp = c.compare(Object, start.getObject());
    		if(comp > 0) {
    			this.addToStart(Object);
    		}else {
    			start.setNextNode(aNode);
    			end = aNode;
    			size++;
    		}
    	}else {
    		for(int i = 1; i < size + 1; i++) {
    			if(i == 1) {
    				int comp = c.compare(Object, start.getObject());
    				if(comp > 0) {
    					this.addToStart(Object);
    					return;
    				}
    			}else {
    				if(currentNode.getNextNode().equals(end)) {
    					int comp = c.compare(Object, end.getObject());
    					if(comp > 0) {
    						aNode.setNextNode(end);
    						currentNode.setNextNode(aNode);
    						size++;
    					}else {
    						this.addToEnd(Object);
    					}
    					return;
    				}else {
    					int comp = c.compare(Object, currentNode.getNextNode().getObject());
    					if(comp > 0) {
    						aNode.setNextNode(currentNode.getNextNode());
    						currentNode.setNextNode(aNode);
    						size++;
    						return;
    					}else {
    						currentNode = currentNode.getNextNode();
    					}
    				}
    			}
    		}
    	}
    }

    public boolean remove(int position) {
    	if(position < 1 || position > size) {
    		throw new IndexOutOfBoundsException("Index out of Bounds");
    	}
    	
    	ListNode<E> currentNode = start;
    	
    	if(position == 1) {
    		start = start.getNextNode();
    		size--;
    		return true;
    	}
    	if(position == size) {
    		for(int i = 1; i < position - 1; i++) {
    			if(currentNode.getNextNode() == null) 
    				return false;
    			currentNode = currentNode.getNextNode();
    		}
    		currentNode.setNextNode(null);
    		end = currentNode;
    		size--;
    		return true;
    	}
    	ListNode<E> previousNode = start;
    	for(int i = 1; i < position-1; i++) {
    		if(previousNode.getNextNode() == null)
    			return false;
    		
    		previousNode = previousNode.getNextNode();
    	}
    	
    	ListNode<E> removeNode = previousNode.getNextNode();
    	ListNode<E> nextNode = removeNode.getNextNode();
    	previousNode.setNextNode(nextNode);
    	end = nextNode;
    	size--;
    	return true;
    }
    
    private ListNode<E> getListNode(int position) {
    	if(position < 1 || position > size) {
    		throw new IndexOutOfBoundsException("There is no item at index: " + position);
    	}
    	ListNode<E> aNode = null;
    	
    	if(position == 1) {
    		aNode = start;
    	}else if(position == size) {
    		aNode = end;
    	}else {
    		ListNode<E> currentNode = start;
    		for(int i = 1; i < position; i++) {
    			currentNode = currentNode.getNextNode();
    		}
    		aNode = currentNode;
    	}
    	return aNode;
    }
    
    public E getObject(int position) {
    	//throw new UnsupportedOperationException("Not yet implemented.");
    	ListNode<E> aNode = this.getListNode(position);
    	
    	return aNode.getObject();
    }

	public int getSize() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
