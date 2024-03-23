public class ListNode<E>
{
	// FIELDS
	// ------------------------------------------------------------------------
	private E myData;
	private ListNode<E> myPrev;
	private ListNode<E> myNext;

	// CONSTRUCTORS
	// ------------------------------------------------------------------------
	public ListNode(E data, ListNode<E> prevNode, ListNode<E> nextNode)
	{
		myData = data;
		myPrev = prevNode;
		myNext = nextNode;
	}

	// ACCESSOR METHODS
	// ------------------------------------------------------------------------
	public E getData() 			 { return myData; }
	public ListNode<E> getPrev() { return myPrev; }
	public ListNode<E> getNext() { return myNext; }

	// MODIFIER METHODS
	// ------------------------------------------------------------------------
	public void setData(E data) 			  { myData = data; }
	public void setPrev(ListNode<E> prevNode) { myPrev = prevNode; }
	public void setNext(ListNode<E> nextNode) { myNext = nextNode; }

	// OVERRIDDEN METHODS
	// ------------------------------------------------------------------------
	public String toString() { return myData.toString(); }

}
