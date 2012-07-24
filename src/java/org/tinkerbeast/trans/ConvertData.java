/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.trans;

/**
 *
 * @author rishin.goswami
 */
public interface ConvertData<U, T> {

    U convert(T data);

    U[] convert(T[] data);
}
