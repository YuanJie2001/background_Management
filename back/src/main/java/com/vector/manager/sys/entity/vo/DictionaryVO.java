package com.vector.manager.sys.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictionaryVO implements Serializable {

    private String label;

    private Integer value;

    public DictionaryVO(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

}
