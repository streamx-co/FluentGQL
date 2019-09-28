package co.streamx.fluent.GQL;


import co.streamx.fluent.notation.Function;

public interface By extends SortOrder, Comparable<By> {
    @Function(omitParentheses = true)
    SortOrder ASC();

    @Function(omitParentheses = true)
    SortOrder DESC();

    @Function(omitParentheses = true)
    SortOrder USING(String operator);
}
