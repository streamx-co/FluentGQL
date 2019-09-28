package co.streamx.fluent.GQL;

import java.util.Map;

public interface FluentQuery {

    Map<String, Object> parameters();

    /**
     * @return The resulting Cypher statement
     */
    @Override
    String toString();
}
