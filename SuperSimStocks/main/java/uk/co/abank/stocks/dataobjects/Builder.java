package uk.co.abank.stocks.dataobjects;

/**
 * Interface to define the behaviour of a builder
 * Returns a generic type depending on the object being built
 * @author JohnM
 *
 * @param <T>
 */
public interface Builder<T> {

	T build();

}