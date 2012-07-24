/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.util;

/**
 *
 * @author rishin.goswami
 */
public class Array<T> {

    T[] array;

    public Array() {
        this.array = null;
    }

    public Array(T[] array) {
        this.array = array;
    }

    public T[] get() {
        return array;
    }

    public void set(T[] array) {
        this.array = array;
    }

    public int length() {
        return array.length;
    }
}
