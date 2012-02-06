/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.serli.chell.framework.util;

import java.util.Collections;
import java.util.Iterator;

/**
 * Utilities for everyday stuff.
 * 
 * Highly inspired by : https://github.com/playframework/play/blob/master/framework/src/play/libs/F.java
 *
 * @author Mathieu Ancelin (mathieu.ancelin@serli.com)
 */
public class F {

    final static None<Object> none = new None<Object>();

    public static abstract class Option<T> implements Iterable<T> {

        public abstract boolean isDefined();

        public abstract boolean isEmpty();

        public abstract Option<T> orElse(T value);

        public abstract T get();

        public abstract T getOrElse(T value);

        public static <T> None<T> none() {
            return (None<T>) (Object) none;
        }

        public static <T> Some<T> some(T value) {
            return new Some<T>(value);
        }

        public static <T> Maybe<T> maybe(T value) {
            return new Maybe<T>(value);
        }
    }

    public static class None<T> extends Option<T> {

        public boolean isDefined() {
            return false;
        }

        public T get() {
            throw new IllegalStateException("No value");
        }

        public Iterator<T> iterator() {
            return Collections.<T>emptyList().iterator();
        }

        @Override
        public String toString() {
            return "None";
        }

        public T getOrElse(T value) {
            return value;
        }

        public boolean isEmpty() {
            return true;
        }

        public Option<T> orElse(T value) {
            return Option.some(value);
        }
    }

    public static class Some<T> extends Option<T> {

        final T value;

        public Some(T value) {
            this.value = value;
        }

        public boolean isDefined() {
            return true;
        }

        public T get() {
            return value;
        }

        public Iterator<T> iterator() {
            return Collections.singletonList(value).iterator();
        }

        @Override
        public String toString() {
            return "Some ( " + value + " )";
        }

        public T getOrElse(T value) {
            return this.value;
        }

        public boolean isEmpty() {
            return false;
        }

        public Option<T> orElse(T value) {
            return this;
        }
    }

    public static class Any<T> extends Some<Object> {

        public Any(T value) {
            super(value);
        }

        public Class<?> type() {
            return value.getClass();
        }

        public boolean isTyped(Class<?> type) {
            return type.isAssignableFrom(type());
        }

        public <A> Option<A> typed(Class<A> type) {
            if (isTyped(type)) {
                return Option.some(type.cast(value));
            } else {
                return (Option<A>) F.none;
            }
        }
    }

    public static class Either<A, B> {

        final public Option<A> left;
        final public Option<B> right;

        private Either(Option<A> left, Option<B> right) {
            this.left = left;
            this.right = right;
        }

        public static <A, B> Either<A, B> left(A value) {
            return new Either(Option.some(value), none);
        }

        public static <A, B> Either<A, B> right(B value) {
            return new Either(none, Option.some(value));
        }

        public boolean isLeft() {
            return left.isDefined();
        }

        public boolean isRight() {
            return right.isDefined();
        }

        public Either<B, A> swap() {
            return new Either<B, A>(right,left);
        }

        @Override
        public String toString() {
            return "Either ( left: " + left + ", right: " + right + " )";
        }
    }

    public static class Tuple<A, B> {

        final public A _1;
        final public B _2;

        public Tuple(A _1, B _2) {
            this._1 = _1;
            this._2 = _2;
        }

        public Tuple<B, A> swap() {
            return new Tuple<B, A>(_2, _1);
        }

        @Override
        public String toString() {
            return "Tuple ( _1: " + _1 + ", _2: " + _2 + " )";
        }
    }

    /**
     * A not so good version of some. Mostly used to wrap
     * return of library methods.
     *
     * @param <T>
     */
    public static class Maybe<T> extends Option<T> {

        private final T input;

        public Maybe(T input) {
            this.input = input;
        }

        public boolean isDefined() {
            return !(input == null);
        }

        public T get() {
            return input;
        }

        public T getOrElse(T value) {
            if (input == null) {
                return value;
            } else {
                return input;
            }
        }

        public Iterator<T> iterator() {
            if (input == null) {
                return Collections.<T>emptyList().iterator();
            } else {
                return Collections.singletonList(input).iterator();
            }
        }

        @Override
        public String toString() {
            return "Maybe ( " + input + " )";
        }

        public boolean isEmpty() {
            return !isDefined();
        }

        public Option<T> orElse(T value) {
            if (isDefined()) {
                return this;
            } else {
                return Option.some(value);
            }
        }
    }

    public static <A, B> Tuple<A, B> tuple(A a, B b) {
        return new Tuple(a, b);
    }

    public static <A> Maybe<A> maybe(A a) {
        return new Maybe(a);
    }
}
