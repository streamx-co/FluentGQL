package co.streamx.fluent.GQL;

import co.streamx.fluent.functions.Function0;
import co.streamx.fluent.functions.Function1;
import co.streamx.fluent.notation.Alias;
import co.streamx.fluent.notation.Context;
import co.streamx.fluent.notation.Function;
import co.streamx.fluent.notation.ParameterContext;

public interface Cypher {
    static <T> Node<T> from(T node) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    static <T, X> T properties(T entity,
                           Property<X>... props) {
        throw new UnsupportedOperationException();
    }

    static <T> Property<T> entry(Function0<T> key,
                                    T value) {
        throw new UnsupportedOperationException();
    }

    static void MATCH(Pattern... patterns) {
        throw new UnsupportedOperationException();
    }

    static void RETURN(Object... patterns) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an alias
     */
    @Alias
    static <T extends Comparable<? super T>, T1> T alias(T field,
                                                         @Context(ParameterContext.ALIAS) Function1<T1, T> alias) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an alias
     */
    @Alias
    static <T> T alias(T fieldOrEntity,
                       String alias) {
        throw new UnsupportedOperationException();
    }

    /**
     * Use previously created alias outside of SELECT to beautify the produced SQL. (Inside SELECT it's used
     * automatically, where it actually has an effect).
     */
    @Alias.Use
    static <T> T aliasOf(T expression) {
        throw new UnsupportedOperationException();
    }

    @Function
    static int COUNT(Object expression) {
        throw new UnsupportedOperationException();
    }

    @Function(name = "ORDER BY", omitParentheses = true)
    static void ORDER(SortOrder... sorts) {
        throw new UnsupportedOperationException();
    }

    @Function(name = "", omitParentheses = true)
    static By BY(Comparable<?> expression) {
        throw new UnsupportedOperationException();
    }
}
