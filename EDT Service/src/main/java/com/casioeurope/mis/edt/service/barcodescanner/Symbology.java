package com.casioeurope.mis.edt.service.barcodescanner;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Root
public class Symbology {
    @Attribute
    private int id;
    @Attribute
    private boolean enabled;
    @Attribute
    private int min;
    @Attribute
    private int max;
    @Attribute
    private int checkCount;

    @ElementList
    private ArrayList<SymbologyProperty> properties;

    public Symbology() {
        this.id=0;
        this.enabled = false;
        this.min=0;
        this.max=0;
        this.checkCount = 0;
        this.properties = new ArrayList<>();
    }

    public Symbology(int id) {
        this.id=id;
        this.enabled = false;
        this.min=0;
        this.max=0;
        this.checkCount = 0;
        this.properties = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    int getMin() {
        return min;
    }

    void setMin(int min) {
        this.min = min;
    }

    int getMax() {
        return max;
    }

    void setMax(int max) {
        this.max = max;
    }

    int getCheckCount() {
        return checkCount;
    }

    void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

    List<SymbologyProperty> getProperties() {
        return properties;
    }
}
