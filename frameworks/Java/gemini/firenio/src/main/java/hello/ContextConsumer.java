package hello;

import com.techempower.gemini.Context;

/**
 * Consumer wrapper for handling method references which throw exceptions.
 * @param <R> The type of the throwable
 */
@FunctionalInterface
public interface ContextConsumer<R extends Throwable> {
    void accept(Context context) throws R;
}
