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
    	
    	//If list is empty, set object node to start and end
    	if(start == null) {
    		start = aNode;
    		end = start;
    	}
    	//If list is not empty, put object node at start
    	else {
    		aNode.setNextNode(start);
    		start = aNode;
    	}
    }

    public void addToEnd(E Object) {
    	ListNode<E> aNode = new ListNode<E>(Object);
    	size++;
    	
    	//If list is empty, set object node to start and end
    	if(start == null) {
    		start = aNode;
    		end = start;
    	}
    	
    	//If list is not empty, put object node at end
    	else {
    		end.setNextNode(aNode);
    		end = aNode;
    	}
    }
    
    

    public void addInvoiceOrdered(E Object) {
    	ListNode<E> aNode = new ListNode<E>(Object);
    	ListNode<E> currentNode = start;
    	TotalComparator<E> c = new TotalComparator<E>();
    	
    	//If list is empty, set object node to start and end
    	if(start == null) {
    		this.addToStart(Object);
    	}
    	
    	//If list is size 1, use comparator to determine if object node should be ordered before/after the node already in the list
    	//This comparison is based on the grand total amount charged for each invoice, ordered from largest grand total to smallest grand total
    	else if(size == 1) {
    		int comp = c.compare(Object, start.getObject());
    		
    		//If object node has a larger grand total than the current start node, place the object node at the start
    		if(comp > 0) {
    			this.addToStart(Object);
    		}
    		
    		//If object node has a smaller grand total than the current start node, place the object node after the existing node
    		else {
    			start.setNextNode(aNode);
    			end = aNode;
    			size++;
    		}
    	}
    	
    	//If list is size greater than 1, loop through each node in the list and use comparator to determine where the object node should be added
    	else {
    		for(int i = 1; i < size + 1; i++) {
    			
    			//If object node should become the start node, place it accordingly
    			if(i == 1) {
    				int comp = c.compare(Object, start.getObject());
    				if(comp > 0) {
    					this.addToStart(Object);
    					return;
    				}
    			}
    			
    			else {
    				
    				//If object node should become the 2nd-to-last or end node, place it accordingly
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
    				}
    				
    				//If object node should be placed in the middle, place it in its appropriate position
    				else {
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
    	
    	//If removing start node
    	if(position == 1) {
    		start = start.getNextNode();
    		size--;
    		return true;
    	}
    	
    	//If removing end node
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
    	
    	//If removing a node in the middle of the list
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
    	
    	//If removing start node
    	if(position == 1) {
    		aNode = start;
    	}
    	
    	//If removing end node
    	else if(position == size) {
    		aNode = end;
    	}
    	
    	//If removing node in the middle of the list
    	else {
    		ListNode<E> currentNode = start;
    		for(int i = 1; i < position; i++) {
    			currentNode = currentNode.getNextNode();
    		}
    		aNode = currentNode;
    	}
    	return aNode;
    }
    
    public E getObject(int position) {
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
