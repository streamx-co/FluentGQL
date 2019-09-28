package co.streamx.fluent.GQL;

import static co.streamx.fluent.GQL.Cypher.BY;
import static co.streamx.fluent.GQL.Cypher.COUNT;
import static co.streamx.fluent.GQL.Cypher.MATCH;
import static co.streamx.fluent.GQL.Cypher.ORDER;
import static co.streamx.fluent.GQL.Cypher.RETURN;
import static co.streamx.fluent.GQL.Cypher.WHERE;
import static co.streamx.fluent.GQL.Cypher.alias;
import static co.streamx.fluent.GQL.Cypher.entry;
import static co.streamx.fluent.GQL.Cypher.from;
import static co.streamx.fluent.GQL.Cypher.properties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Basic implements BasicTypes {

    public void test2() {

        FluentGQL.cypher((Student p,
                          StudyBuddy s) -> {

            MATCH(from(p).byMany(p::getStudyBuddies).to(s));

            Integer buddies = alias(COUNT(s), "buddies");

            RETURN(p, buddies);
            ORDER(BY(buddies).DESC());
        });

    }

    public void test3() {
        FluentGQL.cypher((Person p,
                          Role r,
                          Movie m) -> {

            MATCH(from(p).by(r).to(m));
            RETURN(p);
        });
    }

    public void test4() {
        FluentGQL.cypher((Course c,
                          Subject s) -> {

            MATCH(from(c).by(c::getSubject).to(s));
            RETURN(s);
        });
    }

    public void test5() {

        String name = "Charlie Sheen";

        FluentGQL.cypher((Person p,
                          Person director,
                          Role actedIn,
                          Movie movie) -> {

            Person charlie = properties(p, entry(p::getName, name));

            MATCH(from(charlie).by(actedIn).to(movie).by(movie::getDirector).to(director));
            RETURN(movie.getTitle(), director.getName());
        });
    }

    public void test6() {

        String name = "Li.*";
        String title = "The Matrix";
        int someYear = 1979;

        getDirectorAndMovies(name, title, someYear);
    }

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

        // the resulting Cypher statement
        // query.toString();

        // captured parameters Map
        // query.parameters();

        // now we can
        // return client.query(query.toString()).bindAll(query.parameters()).fetch().all();
        return null;
    }

    @SuppressWarnings("serial")
    @Test
    public void test1() {
        GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(new File("D:\\neo4j"));

        try (org.neo4j.graphdb.Transaction tx = db.beginTx()) {
            Result result = db.execute(
            // @formatter:off
                    "CREATE (a:Greeting) " +
                    "SET a.message = $message " +
                    "RETURN a.message + ', from node ' + id(a)",
            // @formatter:on
                    new HashMap<String, Object>() {
                        {
                            put("message", "hello, world");
                        }
                    });
            Map<String, Object> next = result.next();

            System.out.println(next);
        }

//        Person p = null;
//        Role r = null;
//        Movie m = null;

//        from(p).by(Role::getPerson, r).to(r::getMovie, m);
//
//        from(p).by(r, r::getPerson).to(r::getMovie, m);
//
//        from(p).by(r.getPerson(), r).to(r::getMovie, m);
//        from(p).to(p.getActedMovies(), m);
//        from(p).by(r).to(m);
//        from(p).to(m);

//        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"))) {
//            try ( Session session = driver.session() )
//            {
//                String greeting = session.writeTransaction( new TransactionWork<String>()
//                {
//                    @Override
//                    public String execute( Transaction tx )
//                    {
//                        StatementResult result = tx.run( "CREATE (a:Greeting) " +
//                                                         "SET a.message = $message " +
//                                                         "RETURN a.message + ', from node ' + id(a)",
//                                new HashMap<String, Object>() {
//                                    {
//                                        put("message", "hello, world");
//                                    }
//                                });
//                        return result.single().get( 0 ).asString();
//                    }
//                } );
//                System.out.println( greeting );
//            }
//        }
    }
}
