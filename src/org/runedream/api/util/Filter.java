package org.runedream.api.util;

/**
 * A filter function designed to filter through types by checking if {@link Filter#accept(Object)} allows the typed object to pass.
 * 
 * @author Dang
 */
public interface Filter<T> {

	/**
	 * Tests an argument for acceptance by the filter.
	 * @param t The object to test.
	 * @return <tt>true</tt> if the object is accepted by the filter; otherwise <tt>false</tt>.
	 */
	public boolean accept(T t);

}