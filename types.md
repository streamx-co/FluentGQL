```java
@NodeEntity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int born;

    @Relationship(type = "ACTED_IN")
    private List<Movie> actedMovies = new ArrayList<>();

    @Relationship(type = "DIRECTED")
    private List<Movie> directedMovies = new ArrayList<>();

    @Relationship(type = "WROTE")
    private List<Movie> wroteMovies = new ArrayList<>();
}

@NodeEntity
@Data
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int released;
    private String tagline;

    @JsonIgnoreProperties("movie")
    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<Role> roles;

    @Relationship(type = "DIRECTED", direction = Relationship.INCOMING)
    private Person director;
}

@RelationshipEntity(type = "ACTED_IN")
@Setter
@Getter
public class Role extends Entity implements Relation<Person, Movie> {

    @Id
    @GeneratedValue
    private Long id;
    private List<String> roles = new ArrayList<>();

    @StartNode
    private Person person;

    @EndNode
    private Movie movie;
}

@NodeEntity
@Setter
@Getter
class Department extends Entity {
    private String name;

    @Relationship(type = "CURRICULUM")
    private Set<Subject> subjects;
}

@NodeEntity
@Setter
@Getter
class Subject extends Entity {
    private String name;

    @Relationship(type = "CURRICULUM", direction = Relationship.INCOMING)
    private Department department;

    @Relationship(type = "TAUGHT_BY")
    private Set<Teacher> teachers;

    @Relationship(type = "SUBJECT_TAUGHT", direction = "INCOMING")
    private Set<Course> courses;
}

@NodeEntity
@Setter
@Getter
class Teacher extends Entity {
    private String name;

    @Relationship(type = "TEACHES_CLASS")
    private Set<Course> courses;

    @Relationship(type = "TAUGHT_BY", direction = Relationship.INCOMING)
    private Set<Subject> subjects;
}

@NodeEntity(label = "Class")
@Setter
@Getter
class Course extends Entity {
    private String name;

    @Relationship(type = "SUBJECT_TAUGHT")
    private Subject subject;

    @Relationship(type = "TEACHES_CLASS", direction = Relationship.INCOMING)
    private Teacher teacher;

    @Relationship(type = "ENROLLED", direction = Relationship.INCOMING)
    private Set<Enrollment> enrollments = new HashSet<>();
}

@NodeEntity
@Setter
@Getter
class Student extends Entity {
    private String name;

    @Relationship(type = "ENROLLED")
    private Set<Enrollment> enrollments;

    @Relationship(type = "BUDDY", direction = Relationship.INCOMING)
    private Set<StudyBuddy> studyBuddies;
}

@RelationshipEntity(type = "ENROLLED")
@Setter
@Getter
class Enrollment extends Entity {

    @StartNode
    private Student student;

    @EndNode
    private Course course;

    private Date enrolledDate;

}

@NodeEntity
@Setter
@Getter
class StudyBuddy extends Entity {
    @Relationship(type = "BUDDY", direction = Relationship.OUTGOING)
    private Set<Student> students;
}

@Setter
@Getter
abstract class Entity {

    @Id
    @GeneratedValue
    private Long id;

}
```