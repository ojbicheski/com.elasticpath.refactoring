/**
 * 
 */
package com.elasticpath.rules;

/**
 * @author orlei
 *
 */
public class Result<T> {
	private T value;

	/**
	 * @return the value
	 */
	public T get() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void set(T value) {
		this.value = value;
		System.out.println("SET VALUE: " + value);
	}
}
