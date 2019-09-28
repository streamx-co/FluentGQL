package co.streamx.fluent.GQL;

public interface Relationship<T> extends Pattern {

    Node<T> to();
    Node<T> to(T node);
}
