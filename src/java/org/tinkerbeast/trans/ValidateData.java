/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tinkerbeast.trans;

import java.util.Map;
import org.tinkerbeast.util.Array;

/**
 *
 * @author rishin.goswami
 */
public interface ValidateData {

    boolean isValid(Map<String, Array<? extends Object>> data);

    String getLog();
}
