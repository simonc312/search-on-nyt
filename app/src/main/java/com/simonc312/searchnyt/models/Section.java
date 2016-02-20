package com.simonc312.searchnyt.models;

/**
 * Created by Simon on 2/20/2016.
 */
public class Section {
    private final String section;
    private boolean isChecked;

    public Section(String section, boolean isChecked){
        this.section = section;
        this.isChecked = isChecked;
    }

    public String getSection(){return section;}

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
