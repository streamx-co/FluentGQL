# Fluent Java Interface to Cypher

Streamx has a line of products offering Java fluent interface to other languages. The key differentiator is natural Java integration, since we "recompile - transpile" Java Byte Code to target language (e.g. Cypher). As a result, there is no semantic gaps and learning curve is short. Using our technology, the Cypher queries over OGM would look like this ([see OGM type declarations](types.md)):

```java
public Map<String, Object> getDirectorAndMovies(String name,
                                                String title,
                                                int someYear) {
    // Query variables are clearly defined as Lambda parameters
    FluentQuery query = FluentGQL.cypher((Person p,
                                          Movie movie,
                                          Movie otherMovie) -> {

        // "title" parameter is automatically captured
        movie = properties(movie, entry(movie::getTitle, title));

        // Labels and Relationship types are automatically calculated.
        // The user fully enjoys Java type safety and auto completion.

        //
        // We decided to be very clear and type-safe in the common case:
        // i.e. since getDirectedMovies() returns a Set<Movie>,
        // to() accepts only Movie instance (compiler verified)
        MATCH(from(p).byMany(p::getDirectedMovies).to(movie), from(p).byMany(p::getWroteMovies).to(otherMovie));

        // "name" and "someYear" parameters are automatically captured.
        // the condition is written as a normal business logic Java expression,
        // using standard getters, operators, String methods etc
        WHERE(p.getName().matches(name) && p.getBorn() < someYear);

        RETURN(p, otherMovie);
    });

    // returns the resulting Cypher statement
    // query.toString();

    // returns captured parameters Map
    // query.parameters();

    // now we can
    return client.query(query.toString()).bindAll(query.parameters()).fetch().all();
}
```

The `query.toString()` returns the following statement:

```SQL
MATCH (e0:Person) - [:DIRECTED] -> (e1:Movie {title: $p0}), (e0) - [:WROTE] -> (e2:Movie)
WHERE e0.name =~ $p1
  AND e0.born < $p2
RETURN e0, e2
```

The actual compiling code can be inspected [here](src/test/java/co/streamx/fluent/GQL/Basic.java#L84-L120) + few additional [examples](src/test/java/co/streamx/fluent/GQL/Basic.java#L25-L73). You may also want to look at our [JPA product](https://github.com/streamx-co/FluentJPA), which lets write in Java practically any SQL query on top of JPA entities. The code is open source, so you may examine the usability, quality and completeness.

> This project demoes what we can achieve. At the moment there is no implementation behind. Please [contact me](mailto:kostat@streamx.co), I would be very happy to hear what you think about this idea.
