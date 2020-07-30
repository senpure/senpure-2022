package com.senpure.config.metadata;

import java.util.ArrayList;
import java.util.List;


public class Record {


    private List<Value> values=new ArrayList<>();

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Value value : values) {
            sb.append(value.getField().getClassType()).append(" ").append(value.getField().getName()).append(" = ").append(value.getValue());

            sb.append(" ");
        }
        return sb.toString();
    }
}
