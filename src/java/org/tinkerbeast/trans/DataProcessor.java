/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.trans;

import java.util.HashMap;
import java.util.Map;
import org.tinkerbeast.util.Array;

/**
 *
 * @author rishin.goswami
 */
public class DataProcessor {

    private Map<String, String[]> data;
    private String[] groups;
    private ConvertData[] converters;
    private ValidateData[] validators;
    private StringBuffer log;

    private DataProcessor() {
    }

    public DataProcessor(Map<String, String[]> data, String[] groups) {
        this(data, groups, null, null);
    }

    public DataProcessor(Map<String, String[]> data, ConvertData[] converters) {
        this(data, null, converters, null);
    }

    public DataProcessor(Map<String, String[]> data, ValidateData[] validators) {
        this(data, null, null, validators);
    }

    public DataProcessor(Map<String, String[]> data, String[] groups, ConvertData[] converters, ValidateData[] validators) {
        this.data = data;
        this.groups = groups;
        this.converters = converters;
        this.validators = validators;

        this.log = new StringBuffer();
    }

    public Map<String, Array<?>> getProcessedData() {


        log.append("INFO: Starting data processing\n");
        try {

            // PRECON: data existence
            if (data == null) {
                log.append("EROR: provided data is null\n");
                log.append("INFO: Exiting data processing\n");
                return null;
            }


            // STAGE 1: data filtering
            Map<String, String[]> tempData = new HashMap<String, String[]>();

            if (groups == null) {
                log.append("WARN: taking default groups\n");
                groups = (String[]) data.keySet().toArray();
                tempData = data;
            } else {
                for (int i = 0; i < groups.length; i++) {
                    tempData.put(groups[i], data.get(groups[i]));
                }
            }


            // STAGE 2: data conversion
            Map<String, Array<?>> convData = new HashMap<String, Array<?>>();
            int numConv;

            if (converters == null) {
                log.append("WARN: no conversion occurs\n");
                numConv = 0;
            } else {
                numConv = (groups.length < converters.length) ? groups.length : converters.length;
                for (int i = 0; i < numConv; i++) {
                    convData.put(groups[i],
                            new Array(converters[i].convert(tempData.get(groups[i]))));
                }
            }

            for (int i = numConv; i < groups.length; i++) {
                convData.put(groups[i], new Array<String>(tempData.get(groups[i])));
            }


            // STAGE 3: data validation
            boolean flag;
            if (validators == null) {
                log.append("WARN: no validation occurs\n");
            } else {
                for (int i = 0; i < validators.length; i++) {
                    flag = validators[i].isValid(convData);
                    if (flag == false) {
                        log.append("EROR: validation failed\n");
                        log.append(validators[i].getLog());
                        log.append("INFO: Exiting data processing\n");
                        return null;
                    }
                }
            }

            // POSTCON: return data
            log.append("INFO: Exiting data processing\n");
            return convData;


        } catch (Exception e) {
            log.append("EROR: unhandled exception\n");
            log.append(e.toString());
            log.append("INFO: Exiting data processing\n");
            return null;
        }

    }

    public String getLog() {
        return log.toString();
    }

    public void clearLog() {
        log = new StringBuffer();
    }
}
