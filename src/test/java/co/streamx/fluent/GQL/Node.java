package co.streamx.fluent.GQL;

import java.util.Collection;

import co.streamx.fluent.functions.Function0;

public interface Node<N> extends Pattern {

//    <R> Relationship<R> by(R relationship);
//
//    <R> Relationship<R> by(Collection<R> relationship);

    <R> Relationship<R> by(Function0<R> relationship);

    <R> Relationship<R> byMany(Function0<Collection<R>> relationship);

//    <R> ImplicitRelationship<R> by(Function1<R, N> relation);

//    <R> ImplicitRelationship<R> byMany(Function1<N, Collection<R>> relation);

//    <R> ImplicitRelationship<R> byMany(Function0<Collection<R>> relation);

//    <R> ImplicitRelationship<R> by(Function1<R, N> relation,
//                           R relationship);
//
//    <R> ImplicitRelationship<R> by(R relationship,
//                           Function0<N> relation);

    <R> Relationship<R> by(Relation<N, R> relation);
//
//    <R> ImplicitRelationship<R> by(N property,
//                                   R relationship);
//
//    <M> Node<M> to(Collection<M> property,
//                       M variable);
}
