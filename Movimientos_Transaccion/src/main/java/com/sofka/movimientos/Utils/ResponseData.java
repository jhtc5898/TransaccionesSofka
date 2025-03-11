package com.sofka.movimientos.Utils;

import com.sofka.movimientos.entities.Movements;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResponseData {
    private String message;
    private Object data;

    public ResponseData() {
        this.data = new HashMap<>();
    }


    public ResponseData withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseData withData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public ResponseData withData(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        this.data = map;
        return this;
    }


    public Map<String, Object> getData() {
        return (Map<String, Object>) data;
    }

    public ResponseData withData(String listMovements, List<Movements> movements) {
        this.data = movements;
        this.message = listMovements;
        return this;
    }

    public ResponseData withData(String listMovements, Movements movements) {
        this.data = movements;
        this.message = listMovements;
        return this;
    }

    public ResponseData withError(String message) {
        this.message = message;
        this.data = new HashMap<>();
        return this;
    }

    public ResponseData withSuccess(String message) {
        this.message = message;
        return this;
    }
}
