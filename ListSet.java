import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.awt.*;




/** Combined implementation of both the List and Set interfaces.
 *  Represents an ordered collection that contains no duplicate values.
 */
public class ListSet<E> implements List<E>, Set<E>
{
	// FIELDS
	// ------------------------------------------------------------------------
	private int mySize;				// Number of items in this list-set

	private ListNode<E> myHeadTail;	// Node that serves as both a head
									//   and tail pointer


	// CONSTRUCTORS
	// ------------------------------------------------------------------------
	/** postcondition: Initializes an empty list-set.
	 *      algorithm: Assign to mySize a value of 0.
	 *                 Assign to myHeadTail a new ListNode<E> object whose
	 *                   "data" is null and whose "previous" and "next" pointers 
	 *                   are both null.
	 *                 Set myHeadTail's "previous" pointer to point to myHeadTail.
	 *                 Set myHeadTail's "next" pointer to point to myHeadTail.
	 *   performance: O(1)
	 */
	public ListSet()
	{
		mySize = 0;
		myHeadTail = new ListNode(null, null, null);
		myHeadTail.setPrev(myHeadTail);
		myHeadTail.setNext(myHeadTail);
	}



	// PRIVATE METHODS
	// ------------------------------------------------------------------------
	/** precondition: ListNode<E> n is a node in a circular, doubly linked list.
	 *                The "next" and "previous" pointers of n are non-null.
	 * postcondition: A new ListNode<E> is inserted into the doubly linked list
	 *                  in between ListNode<E> n and the ListNode<E> originally
	 *                  preceding n. Returns the newly inserted ListNode<E>.
	 *     algorithm: Declare a ListNode<E> variable called p and assign to it
	 *                  the ListNode<E> referenced by the "previous" pointer
	 *                  of n.
	 *                Declare a ListNode<E> variable called addedNode and assign
	 *                  to it a new ListNode<E> whose "data" is null, whose
	 *                  "previous" pointer points to p, and whose "next" pointer
	 *                  points to n.
	 *                Set the "next" pointer of p to point to addedNode.
	 *                Set the "previous" pointer of n to point to addedNode.
	 *                Return the ListNode<E> that is referenced by addedNode.
	 *   performance: O(1)
	 */
	private ListNode<E> insertNodeBefore(ListNode<E> n)
	{
		ListNode<E> p = n.getPrev();
		ListNode<E> addedNode = new ListNode(null, p, n);
		p.setNext(addedNode);
		n.setPrev(addedNode);
		return addedNode;
	}


	/** precondition: ListNode<E> r is a node in a circular, doubly linked list.
	 *                The "next" and "previous" pointers of r are non-null.
	 * postcondition: Removes ListNode<E> r from the doubly linked list. The
	 *                  two nodes originally neighboring r are linked together.
	 *                  The "previous" and "next" pointers of r are set to null.
	 *     algorithm: Declare a ListNode<E> variable called p and assign to it
	 *                  the ListNode<E> that is referenced by the "previous" 
	 *                  pointer of r.
	 *                Declare a ListNode<E> variable called n and assign to it
	 *                  the ListNode<E> that is referenced by the "next" pointer
	 *                  of r.
	 *                Set the "next" pointer of p to point to n.
	 *                Set the "previous" pointer of n to point to p.
	 *                Set the "next" pointer of r to point to null.
	 *                Set the "previous" pointer of r to point to null.
	 *   performance: O(1)
	 */
	private void removeNode(ListNode<E> r)
	{
		ListNode<E> p = r.getPrev();
		ListNode<E> n = r.getNext();
		p.setNext(n);
		n.setPrev(p);
		r.setNext(null);
		r.setPrev(null);
		return;	
	}



	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: If i is a valid index position in this list-set, returns
	 *                the ListNode<E> representing index position i. Otherwise,
	 *                throws an IndexOutOfBoundsException.
	 *                NOTE: For this method, -1 is a valid index position and
	 *                is necessary for adding elements at the head of the list.
	 *     algorithm: If i is less than -1 or i is greater than the size of
	 *                  this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Declare a ListNode<E> variable called n and initialize
	 *                  it to the node referenced by myHeadTail.
	 *                While i is greater than or equal to 0...
	 *                    ...assign to n the ListNode<E> that is referenced by
	 *                         the "next" pointer of n.
	 *                    ...decrement the value of i.
	 *                Return the ListNode<E> that is referenced by n.
	 *   performance: O(N)
	 */
	private ListNode<E> getNode(int i)
	{
		if (i < -1 || i > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<E> n = myHeadTail;
		while (i >= 0) {
			n = n.getNext();
			i-=1;
		}
		
		return n;
	}


	// METHODS
	// ------------------------------------------------------------------------
	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns the number of elements in this list-set.
	 *     algorithm: Return the value of mySize.
	 *   performance: O(1)
	 */
	public int size()
	{
		return mySize;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns false if the number of elements currently in
	 *                this list-set is greater than 0. Returns true if
	 *                this list-set currently contains no elements.
	 *     algorithm: Return the result of comparing this.size() with 0.
	 *   performance: O(1)
	 */
	public boolean isEmpty()
	{
		return this.size() == 0;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: If i is a valid index position in this list-set, returns
	 *                the object stored at index position i. Otherwise, throws
	 *                an IndexOutOfBoundsException.
	 *     algorithm: If i is less than 0 or i is greater than or equal to the
	 *                  size of this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Return the data stored in the node at position i.
	 *   performance: O(N)
	 */
	public E get(int i)
	{
		if (i < 0 || i >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.getNode(i).getData();
	}


	/** precondition: This list-set may or may not already contain x. Object x
	 *                may be null.
	 * postcondition: Returns the index position of x in this list-set or -1
	 *                if this list-set does not contain a reference to x.
	 *     algorithm: Declare an integer variable called i and assign to it a 
	 *                  value of 0.
	 *                Declare a ListNode<E> variable called n and assign to it 
	 *                  the node referenced by myHeadTail's "next" pointer.
	 *                While n does not reference myHeadTail...
	 *                    ...if x is null and the data stored in n is null...
	 *                         ...return the value of i.
	 *                    ...if x is not null and x is equivalent to data 
	 *                         stored in n...
	 *                           ...return the value of i.
	 *                    ...assign to n the ListNode<E> that is referenced
	 *                         by the "next" pointer of n.
	 *                    ...increment i.
	 *                Return a value of -1.
	 *   performance: O(N)
	 */
	public int indexOf(Object x)
	{
		int i = 0;
		ListNode<E> n = myHeadTail.getNext();
		
		while (n != myHeadTail) {
			if (x == null && n.getData() == null) {
				return i;
			}
			else if (x != null && x == n.getData()) {
				return i;
			}
			else {
				n = n.getNext();
				i+=1;
			}
			
		}
		return -1;
	}


	/** precondition: This list-set may or may not already contain x. Object x
	 *                may be null.
	 * postcondition: Returns true if this list-set contains x and false if it
	 *                does not.
	 *     algorithm: Return the result of comparing to see if the index
	 *                  position of x is greater than -1.
	 *   performance: O(N)
	 */
	public boolean contains(Object x)
	{
		return this.indexOf(x) > -1;
	}


	/** precondition: This list-set contains 0 or more elements. This list-set
	 *                may or may not already contain x. Object x may be null.
	 * postcondition: If this list-set already contains object x, throws an
	 *                IllegalArgumentException. If this list-set does not
	 *                already contain object x, replaces the element at
	 *                index position i with object x and returns the object
	 *                originally stored in that position. If i is not a
	 *                valid index position (i.e., i < 0 or i >= this.size()),
	 *                an IndexOutOfBoundsException will be thrown.
	 *     algorithm: If this list-set contains x...
	 *                    ...throw a new IllegalArgumentException.
	 *                If i is less than 0 or i is greater than or equal to the
	 *                  size of this list-set...
	 *                      ...throw a new IndexOutOfBoundsException.
	 *                Declare a ListNode<E> variable called n and assign to it
	 *                  a reference to node i of this list-set.
	 *                Declare an E variable called origVal and assign to it the
	 *                  data stored in node n.
	 *                Set the data stored in node n to be x.
	 *                Return the value of origVal.
	 *   performance: O(N)
	 */
	public E set(int i, E x)
	{
		if (this.contains(x)) {
			throw new IllegalArgumentException();
		}
		
		if (i < 0 || i >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<E> n = this.getNode(i);
		E origVal = n.getData();
		n.setData(x);
		return origVal;
	}


	/** precondition: This list-set may or may not already contain x.
	 *                Parameter x may be null.
	 * postcondition: If this list-set does not already contain x and i is
	 *                a valid index position within this list-set, x will be
	 *                inserted at the specified position and the size will
	 *                increase by 1. If this list-set already contains x,
	 *                the list-set will remain unchanged. If i is not a
	 *                valid index position (i.e., i < 0 or i > this.size()),
	 *                an IndexOutOfBoundsException will be thrown.
	 *     algorithm: If i is less than 0 or i is greater than the size of
	 *                  this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                If this list-set does not contain x...
	 *                    ...increment the value of mySize.
	 *                    ...declare a ListNode<E> variable called n and assign
	 *                         to it a reference to node i of this list-set.
	 *                    ...declare a ListNode<E> variable called addedNode
	 *                         and assign to it the ListNode<E> that results
	 *                         from inserting a node before n.
	 *                    ...set the "data" of addedNode to be x.
	 *   performance: O(N)
	 */
	public void add(int i, E x)
	{
		if (i < 0 || i > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (!this.contains(x)) {
			mySize++;
			ListNode<E> n = this.getNode(i);
			ListNode<E> addedNode = this.insertNodeBefore(n);
			addedNode.setData(x);
		}
	}


	/** precondition: This list-set may or may not already contain x. Object
	 *                x may be null.
	 * postcondition: If this list-set does not already contain x, it will be
	 *                appended to the end of the list-set and the size will
	 *                increase by 1. Otherwise, this list-set will remain
	 *                unchanged. Returns true if this list-set is changed as a
	 *                result of the method call and false if it is not.
	 *     algorithm: If this list-set contains x...
	 *                    ...return false.
	 *                Declare a ListNode<E> variable called addedNode and 
	 *                  assign to it the result of inserting a node before 
	 *                  myHeadTail.
	 *                Set the "data" of addedNode to be x.
	 *                Increment the value of mySize.
	 *                Return true.
	 *   performance: O(N)
	 */
	public boolean add(E x)
	{
		if (this.contains(x)) {
			return false;
		}
		
		ListNode<E> addedNode = this.insertNodeBefore(myHeadTail);
		addedNode.setData(x);
		mySize++;
		return true;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Removes and returns the element currently stored at index
	 *                position i. If i is not a valid index position (i.e.,
	 *                i < 0 or i >= this.size()), an IndexOutOfBoundsException
	 *                will be thrown.
	 *     algorithm: If i is less than 0 or i is greater than or equal to the
	 *                  size of this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Decrement the value of mySize.
	 *                Declare a ListNode<E> variable called r and assign to it
	 *                  a reference to node i of this list-set.
	 *                Remove ListNode<E> r from the doubly linked list.
	 *                Return the data stored in r.
	 *   performance: O(N)
	 */
	public E remove(int i)
	{
		if (i < 0 || i >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		mySize--;
		ListNode<E> r = this.getNode(i);
		this.removeNode(r);
		return r.getData();
	}


	/** precondition: This list-set may or may not already contain x. Object x
	 *                may be null.
	 * postcondition: Removes the first occurrence of x in the list-set.
	 *                Returns true if this list-set is changed as a result of
	 *                the method call and false if it is not.
	 *     algorithm: Declare an integer variable called i and assign to it the
	 *                  index position of x in this list-set.
	 *                If the value of i is equal to -1...
	 *                    ...return false.
	 *                Remove the element at position i from this list-set.
	 *                Return true.
	 *   performance: O(N)
	 */
	public boolean remove(Object x)
	{
		int i = this.indexOf(x);
		if (i == -1) {
			return false;
		}
		this.remove(i);
		return true;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a string containing a concatenation of the string
	 *                representations of all elements in this list-set in the
	 *                order of their index positions.
	 *     algorithm: If this list-set is empty...
	 *                    ...return the string "[]".
	 *                Declare a String variable called s and initialize it
	 *                  with a value of "[".
	 *                Declare a ListNode<E> variable called n and assign to it 
	 *                  the node referenced by myHeadTail's "next" pointer.
	 *                While n does not reference myHeadTail...
	 *                    ...assign to s the string that results from
	 *                         concatenating s with the "data" stored in n.
	 *                    ...assign to s the string that results from
	 *                         concatenating s with the string ", " (i.e.,
	 *                         a comma followed by a space).
	*                    ...assign to n the ListNode<E> that is referenced by
	*                         the "next" pointer of n.
	 *                Reassign s to the value of the substring of s from
	 *                  index position 0 through s.length() - 2 (exclusive).
	 *                Assign to s the string that results from concatenating
	 *                  s with the string "]".
	 *                Return the value of s.
	 *   performance: O(N)
	 */
	public String toString()
	{
		if (this.isEmpty()) {
			return "[]";
		}
		
		String s = "[";
		ListNode<E> n = myHeadTail.getNext();
		while (n != myHeadTail) {
			s = s+n.getData();
			s = s+", ";
			n = n.getNext();
		}
		s = s.substring(0,s.length()-2);
		s = s+"]";
		return s;
	}


	/** precondition: This list-set contains 0 or more E objects.
	 * postcondition: Returns true if that is an initialized List
	 *                  that contains the same sequence of items as this
	 *                  list-set.
	 *     algorithm: If the size() of this list-set does not equal the 
	 *                  size of that List...
	 *                  ...return false.
	 *                Declare a ListNode<E> variable called thisN and 
	 *                  initialize it to the node referenced by myHeadTail of
	 *                  this list-set.
	 *                Declare a ListNode<E> variable called thatN and 
	 *                  initialize it to the node referenced by myHeadTail of 
	 *                  the parameter called that.
	 *                While thisN does not equal myHeadTail...
	 *                    ...assign to thisN the ListNode<E> that is referenced
	 *                         by the "next" pointer of thisN.
	 *                    ...assign to thatN the ListNode<E> that is referenced
	 *                         by the "next" pointer of thatN.
	 *                    ...if the "data" stored in thisN is note equivalent
	 *                         to (i.e., using the equals() method) the "data"
	 *                         stored in thatN...
	 *                         ...return false
	 *                Return true.
	 *   performance: O(N)
	 */
	private boolean allItemsMatch(ListSet<E> that)
	{
		if (this.size() != that.size()) {
			return false;
		}
		
		ListNode<E> thisN = this.myHeadTail;
		ListNode<E> thatN = that.myHeadTail;
		
		while (thisN != myHeadTail) {
			thisN = thisN.getNext();
			thatN = thatN.getNext();
			if (!thisN.getData().equals(thatN.getData())) {
				return false;
			}
		}
		return true;
	}
	

	/** precondition: Object x may or not be a List or a Set. Object x may
	 *                be null.
	 * postcondition: Returns true if and only if the specified object is also
	 *                a List and a Set, both list-sets have the same size, and
	 *                all corresponding pairs of elements in the two list-sets
	 *                are equivalent (that is, for each element e1.equals(e2)).
	 *                In other words, two list-sets are defined to be equal if
	 *                they contain the same elements in the same order.
	 *   performance: O(N)
	 */
	public boolean equals(Object that)
	{		
		if (this == that) { return true; }
		if ( !(that instanceof List<?>) ) { return false; }
		if ( !(that instanceof Set<?>) ) { return false; }
				
		@SuppressWarnings("unchecked")
		boolean isEquivalent = this.allItemsMatch((ListSet<E>) that);
		return isEquivalent;
	}


	/** precondition: This list-set may or may not already contain x. Object x
	 *                may be null.
	 * postcondition: Returns the index position of x in this list-set or -1
	 *                if this list-set does not contain a reference to x.
	 *                NOTE: While it is not the case for most lists, because
	 *                this list-set is a Set and there may only be at most 1
	 *                instance of a value, x, in the list-set, it will always
	 *                be true that indexOf() == lastIndexOf() for any ListSet.
	 *     algorithm: Return the index position of x in this list-set.
	 *   performance: O(N)
	 */
	public int lastIndexOf(Object x)
	{
		return this.indexOf(x);
	}


	/** precondition: This list-set may or may not contain the elements of
	 *                collection c. The elements of c may be null.
	 * postcondition: Returns true if this list-set contains all elements of
	 *                collection c and false if it does not.
	 *     algorithm: For each Object x in Collection c ("for-each" loop)...
	 *                    ...if this list-set does not contain x...
	 *                         ...return false.
	 *                Return true.
	 *   performance: O(N * M), where N = this.size() and M = c.size()
	 */
	public boolean containsAll(Collection<?> c)
	{
		for (Object x : c) {
			if (!this.contains(x)) {
				return false;
			}
		}
		return true;
	}



	/** precondition: Collection c contains items to be added to this list-set.
	 *                This list-set may already contain some of the elements of
	 *                c. The elements of c may be null.
	 * postcondition: Attempts to insert each element of c into this list-set
	 *                at position i in the order that the items are returned by
	 *                the specified collection's iterator. Returns true if this
	 *                list-set is changed as a result of the method call and
	 *                false if it is not. If i is not a valid index position
	 *                (i.e., i < 0 or i > this.size()), an
	 *                IndexOutOfBoundsException will be thrown.
	 *     algorithm: If i is less than 0 or i is greater than or equal to the
	 *                  size of this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Declare a boolean variable called wasModified and
	 *                  initialize it with a value of false.
	 *                Declare a ListNode<E> variable called n and assign to it 
	 *                  the node at index position i.
	 *                For each E x in Collection c (i.e., "for-each" loop)...
	 *                    ...if this list-set does not contain x...
	 *                         ...insert a ListNode<E> before n.
	 *                         ...declare a ListNode<E> variable called p and 
	 *                              assign to it the ListNode<E> referenced by
	 *                              the "prev" pointer of n.
	 *                         ...set the "data" for p to be x.
	 *                         ...assign to wasModified the value of true.
	 *                Return the value of wasModified.
	 *   performance: O(N + M), where N = this.size() and M = c.size()
	 */
	public boolean addAll(int i, Collection<? extends E> c)
	{
		if (i < 0 || i >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		boolean wasModified = false;
		ListNode<E> n = this.getNode(i);
		
		for (E x : c) {
			if (!this.contains(x)) {
				this.insertNodeBefore(n);
				ListNode<E> p = n.getPrev();
				p.setData(x);
				wasModified = true;
			}
		}
		return wasModified;
	}


	/** precondition: Collection c contains items to be added to this list-set.
	 *                This list-set may already contain some of the elements of
	 *                c. The elements of c may be null.
	 * postcondition: Attempts to append each element of c to the end of this
	 *                list-set in the order that the items are returned by the
	 *                specified collection's iterator. Returns true if this
	 *                list-set is changed as a result of the method call and
	 *                false if it is not.
	 *     algorithm: Declare an integer variable called i and assign to it 
	 *                  the size of this list-set.
	 *                Return the result of adding all elements of c to index
	 *                  position i of this list-set.
	 *   performance: O(M), where N = this.size() and M = c.size()
	 */
	public boolean addAll(Collection<? extends E> c)
	{
		int i = this.size();
		return this.addAll(i, c);
	}


	/** precondition: Collection c contains items to be removed from this
	 *                list-set. This list-set may or may not contain each
	 *                element of Collection c. The elements of c may be null.
	 * postcondition: Removes all occurrences of each element of c from this
	 *                list-set. Returns true if this list-set is changed as a
	 *                result of the method call and false if it is not.
	 *     algorithm: Declare a boolean variable called wasModified and
	 *                  initialize it with a value of false.
	 *                For each Object x in Collection c ("for-each" loop)...
	 *                     ...if removing x from this list-set returns true...
	 *                         ...assign wasModified a value of true.
	 *                Return the value of wasModified.
	 *   performance: O(N * M), where N = this.size() and M = c.size()
	 */
	public boolean removeAll(Collection<?> c)
	{
		boolean wasModified = false;
		
		for (Object x : c) {
			if (this.remove(x)) {
				wasModified = true;
			}
		}
		return wasModified;
	}


	/** precondition: Collection c contains items to be retained in this
	 *                list-set. This list-set may or may not contain each
	 *                element of Collection c. The elements of c may be null.
	 * postcondition: Removes all elements of this list-set except for those
	 *                in c. Returns true if this list-set is changed as a
	 *                result of the method call and false if it is not.
	 *     algorithm: Declare a boolean variable called wasModified and
	 *                  initialize it with a value of false.
	 *                For each index position, i, in this list-set from
	 *                  this.size() - 1 through 0 (inclusive)...
	 *                    ...if c does not contain the element stored at
	 *                         position i in this list-set...
	 *                           ...remove the element stored at position i
	 *                                from this list-set.
	 *                           ...assign wasModified a value of true.
	 *                Return the value of wasModified.
	 *   performance: O(N * M), where N = this.size() and M = c.size()
	 */
	public boolean retainAll(Collection<?> c)
	{
		boolean wasModified = false;
		
		for (int i = 0; i <= this.size()-1; i++) {
			if (!c.contains(this.get(i))) {
			this.remove(i);
			wasModified = true;
			}
		}
		return wasModified;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Removes all of the elements from this list-set. This
	 *                list-set will be empty after this call returns.
	 *     algorithm: While this list-set is not empty...
	 *                    ...remove the element at index position 0.
	 *   performance: O(N)
	 */
	public void clear()
	{
		while (!this.isEmpty()) {
			this.remove(0);
		}
	}


	/** precondition: Parameter firstIndex is the lower end-point (inclusive)
	 *                of the sublist and parameter lastIndex is the upper
	 *                end-point (exclusive) of the sublist.
	 * postcondition: Returns a new List<E> that contains all of the elements
	 *                of this list-set within the range of firstIndex
	 *                (inclusive) through lastIndex (exclusive) in the same
	 *                order that they occur in this list-set. Throws an
	 *                IndexOutOfBoundsException if firstIndex and lastIndex
	 *                specify an invalid range (i.e., firstIndex < 0 or
	 *                lastIndex > size or firstIndex > lastIndex).
	 *     algorithm: If firstIndex is less than 0 or lastIndex is greater than
	 *                  the size of this list-set or if firstIndex is greater
	 *                  than lastIndex...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Declare a new List<E> called sub and assign to it a
	 *                  new ListSet<E>.
	 *                Declare a ListNode<E> variable called n and assign to 
	 *                  it the node at index position firstIndex.
	 *                Declare a ListNode<E> variable called stopHere and assign  
	 *                  to it the node at index position lastIndex.
	 *                While n does not reference stopHere...
	 *                    ...append to sub the "data" stored in n.
	*                    ...assign to n the ListNode<E> that is referenced by
	*                         the "next" pointer of n.
	 *                Return the sub list-set.
	 *   performance: O(N)
	 */
	public List<E> subList(int firstIndex, int lastIndex)
	{
		if (firstIndex < 0 || lastIndex > this.size() || firstIndex > lastIndex) {
			throw new IndexOutOfBoundsException();
		}
		
		List<E> sub = new ListSet<E>();
		ListNode<E> n = this.getNode(firstIndex);
		ListNode<E> stopHere = this.getNode(lastIndex);
		
		while (n != stopHere) {
			sub.add(n.getData());
			n = n.getNext();
		}
		
		return sub;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a primitive array containing all of the elements
	 *                of this list-set in the same order that they are currently
	 *                stored. The length of the returned array is equal to the
	 *                number of elements in this list-set.
	 *     algorithm: Declare a new Object array called arr and initialize
	 *                  it to have a length equal to the size of this
	 *                  list-set.
	 *                Declare an integer variable called i and assign to it
	 *                  a value of 0.
	 *                Declare a ListNode<E> variable called n and assign to it 
	 *                  the node referenced by myHeadTail's "next" pointer.
	 *                While n does not reference myHeadTail...
	 *                    ...assign into position i of arr the "data" stored
	 *                         in n.
	*                    ...assign to n the ListNode<E> that is referenced by
	*                         the "next" pointer of n.
	 *                    ...increment the value of i.
	 *                Return arr.
	 *   performance: O(N)
	 */
	public Object[] toArray()
	{
		Object[] arr = new Object[this.size()];
		int i = 0;
		ListNode<E> n = myHeadTail.getNext();
		
		while (n != myHeadTail) {
			arr[i] = n.getData();
			n = n.getNext();
			i++;
		}
		return arr;
	}


	/** precondition: Parameter dest is a properly initialized array of some
	 *                data type, T.
	 * postcondition: If dest.length is greater than the size of this list-set,
	 *                copies all of the elements of this list-set into dest,
	 *                assigns null to the first index position beyond the last
	 *                element copied, and then returns dest. Otherwise, returns
	 *                a new array of T of the appropriate length containing
	 *                all of the elements of this list-set.
	 *   performance: O(N)
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] dest)
	{
		Object[] src = this.toArray();

		if (dest.length < this.size())
		{
			return (T[]) Arrays.copyOf(src, this.size(), dest.getClass());
		}
		else
		{
			System.arraycopy(src, 0, dest, 0, this.size());
			if (dest.length > this.size()) { dest[this.size()] = null; }
			return dest;
		}
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a new ListSetIterator for this list-set.
	 *     algorithm: Return a new ListSetIterator (see private inner class).
	 *   performance: O(1)
	 */
	public Iterator<E> iterator()
	{
		return new ListSetIterator();
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a new ListSetIterator for this list-set.
	 *     algorithm: Return a new ListSetIterator (see private inner class).
	 *   performance: O(1)
	 */
	public ListIterator<E> listIterator()
	{
		return new ListSetIterator();
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a new ListSetIterator for this list-set that is
	 *                initialized to start at index position i. If i is not a
	 *                valid index position (i.e., i < 0 or i >= this.size()),
	 *                an IndexOutOfBoundsException will be thrown.
	 *     algorithm: If i is less than 0 or i is greater than or equal to the
	 *                  size of this list-set...
	 *                    ...throw a new IndexOutOfBoundsException.
	 *                Declare a ListIterator<E> object called iter and assign
	 *                  to it a new ListSetIterator (see private inner class).
	 *                While i is greater than or equal to 0...
	 *                    ...use next() on iter to increment it to the next
	 *                       element in this list-set.
	 *                    ...decrement the value of i.
	 *                Return the iterator, iter.
	 *   performance: O(N)
	 */
	public ListIterator<E> listIterator(int i)
	{
		if (i < 0 || i >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListIterator<E> iter = new ListSetIterator();
		
		while (i >= 0) {
			iter.next();
			i--;
		}
		return iter;
	}


	/** precondition: This list-set contains 0 or more elements.
	 * postcondition: Returns a new Spliterator for this list-set using
	 *                the default method inherited from the List interface
	 *                (required for disambiguation with the similar method
	 *                inherited from Set).
	 *   performance: O(1)
	 */
	public Spliterator<E> spliterator()
	{
		return List.super.spliterator();
	}



	/** Private, inner class representing an object that can iterate over the
	 *  elements of a ListSet.
	 */
	private class ListSetIterator implements ListIterator<E>
	{
		// FIELDS
		//---------------------------------------------------------------------
		private int myLatestIndex;	// Index position of the last element
									// referenced by this iterator

		private ListNode<E> myLatestNode;	// Node containing the last element
											// referenced by this iterator

		private boolean myEditableState;	// true if set() or remove()
											// may be invoked, otherwise
											// false if they cannot


		// CONSTRUCTORS
		//---------------------------------------------------------------------
		/** postcondition: Initializes a ListSetIterator for its list-set such
		 *                 that the first invocation of next() will return the
		 *                 element at index position 0 of its list-set.
		 *      algorithm: Initialize myLatestIndex to a value of -1.
		 *                 Initialize myLatestNode to be the node referenced
		 *                   by myHeadTail (NOTE: Use ListSet.this.myHeadTail to
		 *                   access an instance variable from the outer class).
		 *                 Initialize myEditableState to false.
		 *   performance: O(1)
		 */
		public ListSetIterator()
		{
			myLatestIndex = -1;
			myLatestNode = ListSet.this.myHeadTail;
			myEditableState = false;
		}


		// METHODS
		//---------------------------------------------------------------------
		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns true if this list iterator has more elements
		 *                when traversing the list in the forward direction.
		 *     algorithm: Return the result of comparing to see if
		 *                  myLatestIndex is less than the size of the
		 *                  list-set - 1 (NOTE: Use ListSet.this.size() to
		 *                  access methods from the outer class).
		 *   performance: O(1)
		 */
		public boolean hasNext()
		{
			return myLatestIndex < ListSet.this.size()-1;
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns true if this list iterator has more elements
		 *                when traversing the list in the backward direction.
		 *     algorithm: Return the result of comparing to see if
		 *                  myLatestIndex is greater than -1.
		 *   performance: O(1)
		 */
		public boolean hasPrevious()
		{
			return myLatestIndex>-1;
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns the index of the element that would be
		 *                returned by a subsequent call to next().
		 *     algorithm: Return the value of myLatestIndex + 1.
		 *   performance: O(1)
		 */
		public int nextIndex()
		{
			return myLatestIndex + 1;
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns the index of the element that would be
		 *                returned by a subsequent call to previous().
		 *     algorithm: Return the value of myLatestIndex.
		 *   performance: O(1)
		 */
		public int previousIndex()
		{
			return myLatestIndex;
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns the next element in this list-set or throws a
		 *                NoSuchElementException if there is no next element.
		 *     algorithm: If myLatestIndex is equal to the size of the
		 *                  list-set - 1 (NOTE: Use ListSet.this.size()
		 *                  to access methods from the outer class)...
		 *                    ...throw a new NoSuchElementException.
		 *                Increment the value of myLatestIndex.
		 *                Assign to myLatestNode the ListNode<E> referenced by
		 *                  the "next" pointer of myLatestNode.
		 *                Assign to myEditableState the value of true.
		 *                Return the data stored in myLatestNode.
		 *   performance: O(1)
		 */
		public E next()
		{
			if (myLatestIndex == ListSet.this.size() -1) {
				throw new NoSuchElementException();
			}
			
			myLatestIndex++;
			myLatestNode = myLatestNode.getNext();
			myEditableState = true;
			return myLatestNode.getData();
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Returns the previous element in this list-set or
		 *                throws a NoSuchElementException if there is no
		 *                previous element.
		 *     algorithm: If myLatestIndex is equal to -1...
		 *                    ...throw a new NoSuchElementException.
		 *                Decrement the value of myLatestIndex.
		 *                Assign to myLatestNode the ListNode<E> referenced by
		 *                  the "previous" pointer of myLatestNode.
		 *                Assign to myEditableState the value of true.
		 *                Return the data stored in myLatestNode.
		 *   performance: O(1)
		 */
		public E previous()
		{
			if (myLatestIndex == -1) {
				throw new NoSuchElementException();
			}
			
			myLatestIndex--;
			myLatestNode = myLatestNode.getPrev();
			myEditableState = true;
			return myLatestNode.getData();
		}


		/** precondition: This iterator's list-set may or may not already
		 *                contain x. Object x may be null.
		 * postcondition: If this list-set does not already contain x, x will be
		 *                inserted at the position of the element that will be
		 *                returned by the next invocation of next() and the size
		 *                will increase by 1. If this list-set already contains
		 *                x, the list-set will remain unchanged.
		 *     algorithm: If this list-set does not contain x (NOTE: Use 
		 *                  ListSet.this.contains() to access methods from the 
		 *                  outer class)...
		 *                  ...assign to myEditableState the value of false.
		 *                  ...increment the value of mySize (NOTE: Use
		 *                       ListSet.this.mySize to instance variables from
		 *                       the outer class).
		 *                  ...declare a ListNode<E> variable called n and
		 *                       assign to it the ListNode<E> referenced by
		 *                       myLatestNode.
		 *                  ...assign to myLatestNode the ListNode<E> that
		 *                       results from inserting a new node before n.
		 *                  ...set the "data" of myLatestNode to be x.
		 *   performance: O(1)
		 */
		public void add(E x)
		{
			if (!ListSet.this.contains(x)) {
				myEditableState = false;
				ListSet.this.mySize++;
				ListNode<E> n = myLatestNode;
				myLatestNode = ListSet.this.insertNodeBefore(n);
				myLatestNode.setData(x);
			}
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 * postcondition: Removes from this list-set the last element returned
		 *                by either the next() or previous() methods. Throws an
		 *                IllegalStateException if neither next() nor previous()
		 *                have been called, or remove() or add() have been
		 *                called after the last call to next() or previous().
		 *     algorithm: If myEditableState is false...
		 *                    ...throw a new IllegalStateException.
		 *                Assign to myEditableState the value of false.
		 *                Decrement the value of mySize (NOTE: Use
		 *                  ListSet.this.mySize to access instance variables
		 *                  from the outer class).
		 *                Declare a ListNode<E> variable called r and assign to
		 *                  it the node referenced by myLatestNode.
		 *                Assign to myLatestNode the ListNode<E> referenced by
		 *                  the "next" pointer of myLatestNode.
		 *                Remove ListNode<E> r from the doubly linked list (NOTE:
		 *                  Use ListSet.this.removeNode() to access methods from
		 *                  the outer class).
		 *   performance: O(1)
		 */
		public void remove()
		{
			if (!myEditableState) {
				throw new IllegalStateException();
			}
			
			myEditableState = false;
			ListSet.this.mySize--;
			ListNode<E> r = myLatestNode;
			myLatestNode = myLatestNode.getNext();
			ListSet.this.removeNode(r);
		}


		/** precondition: This iterator's list-set contains 0 or more elements.
		 *                This list-set may or may not already contain x.
		 *                Object x may be null.
		 * postcondition: If this list-set already contains object x, throws
		 *                an IllegalArgumentException. If this list-set does
		 *                not already contain object x, replaces the last
		 *                element by an invocation of next() or previous()
		 *                with object x. Throws an IllegalStateException if
		 *                neither next() nor previous() have been called, or
		 *                remove() or add() have been called after the last
		 *                call to next() or previous().
		 *     algorithm: If myEditableState is false...
		 *                  ...throw a new IllegalStateException.
		 *                If this list-set does not contain x (NOTE: Use 
		 *                  ListSet.this.contains() to access methods from the 
		 *                  outer class)...
		 *                  ...declare a ListNode<E> variable called n and
		 *                       assign to it a reference to myLatestNode.
		 *                  ...set the "data" stored in node n to x.
		 *   performance: O(1)
		 */
		public void set(E x)
		{
			if (!myEditableState) {
				throw new IllegalStateException();
			}
			
			if (!ListSet.this.contains(x)) {
				ListNode<E> n = myLatestNode;
				n.setData(x);
			}
		}
	}

}
