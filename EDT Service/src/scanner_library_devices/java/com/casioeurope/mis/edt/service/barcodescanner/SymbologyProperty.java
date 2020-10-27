package com.casioeurope.mis.edt.service.barcodescanner;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class SymbologyProperty {

    @Attribute
    private int id;
    @Attribute
    private int value;

    @SuppressWarnings({"unused", "RedundantSuppression"})
    SymbologyProperty() {
        this.id = 0;
        this.value = 0;
    }

    SymbologyProperty(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
