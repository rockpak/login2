package com.example.ExcelentChild;

import java.util.HashMap;
import java.util.Map;

public class Member {
    private String name;
    private String ParentId;

    private String cat1;
    private String cat2;
    private String cat3;
    private String cat4;
    private String cat1value;
    private String cat2value;
    private String cat3value;
    private String cat4value;
    private HashMap<String, Object> dates;



    public Member(){}

    public Member(String name, String parentId, String cat1, String cat2, String cat3, String cat4, String cat1value, String cat2value, String cat3value, String cat4value, HashMap dates) {
        this.name = name;
        ParentId = parentId;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.cat4 = cat4;
        this.cat1value = cat1value;
        this.cat2value = cat2value;
        this.cat3value = cat3value;
        this.cat4value = cat4value;
        this.dates = dates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getCat4() {
        return cat4;
    }

    public void setCat4(String cat4) {
        this.cat4 = cat4;
    }

    public String getCat1value() {
        return cat1value;
    }

    public void setCat1value(String cat1value) {
        this.cat1value = cat1value;
    }

    public String getCat2value() {
        return cat2value;
    }

    public void setCat2value(String cat2value) {
        this.cat2value = cat2value;
    }

    public String getCat3value() {
        return cat3value;
    }

    public void setCat3value(String cat3value) {
        this.cat3value = cat3value;
    }

    public String getCat4value() {
        return cat4value;
    }

    public void setCat4value(String cat4value) {
        this.cat4value = cat4value;
    }

    public void addDatesMap(){

    }

    public HashMap getDates(){
        return this.dates;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> hash = new HashMap<>();

        if(getCat1() != null && !getCat1().isEmpty()){
            hash.put("cat1", getCat1());
        }

        if(getCat2() != null && !getCat2().isEmpty()){
            hash.put("cat2", getCat2());
        }

        if(getCat3() != null && !getCat3().isEmpty()){
            hash.put("cat3", getCat3());
        }

        if(getCat4() != null && !getCat4().isEmpty()){
            hash.put("cat4", getCat4());
        }

        if(getName() != null && !getName().isEmpty()){
            hash.put("name", getName());
        }

        return hash;
    }
}
