package com.challengeandresponse.utils;

import java.util.*;

/**
 * Provides a shielded view of a list allowing read access but no writing.
 * All methods that would alter the list are NO-OPs.
 * 
 * TODO The returned iterator may allow removal operations on the iterated objects, so it's not completely isolated yet
 * @author jim
 *
 */
public class ReadonlyList <E> extends Object implements List <E> {

	private List <E> list;
	
	public ReadonlyList(List <E> list) {
		this.list = list;
	}
	
	/** Does nothing, always returns false  **/
	public boolean add(E o) {
		return false;
	}

	/** Does nothing */
	public void add(int index, E element) {
	}

	/** Does nothing, always returns false  */
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	/** Does nothing, always returns false  */
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	/** Does nothing **/
	public void clear() {
	}

	
	public boolean contains(Object o) {
		return list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public E get(int index) {
		return list.get(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator <E> iterator() {
		return list.iterator();
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator <E> listIterator() {
		return list.listIterator();
	}

	public ListIterator <E> listIterator(int index) {
		return list.listIterator(index);
	}

	/** Does nothing, always returns false  */
	public boolean remove(Object o) {
		return false;
	}

	/** Does nothing, always returns null  */
	public E remove(int index) {
		return null;
	}

	/** Does nothing, always returns false  */
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	/** Does nothing, always returns false */
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	/** Does nothing, always returns null */
	public E set(int index, E element) {
		return null;
	}

	public int size() {
		return list.size();
	}

	public List <E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return null;
	}

	public <T> T[] toArray(T[] a) {
		return null;
	}

	



}
