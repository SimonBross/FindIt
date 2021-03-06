package com.hska.simon.findit.model;

public class Job {

    private int id;
    private int type;
    private String company;
    private String position;
    private String description;
    private int isfavorite;
    private String pdfbase64;

    public Job() {
        super();
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(int isfavorite) {
        this.isfavorite = isfavorite;
    }

    public String getPdfbase64() {
        return pdfbase64;
    }

    public void setPdfbase64(String pdfbase64) {
        this.pdfbase64 = pdfbase64;
    }

    public Job(int id, int type, String company, String position, String description, int isfavorite) {
        this.id = id;
        this.type = type;
        this.company = company;
        this.position = position;
        this.description = description;
        this.isfavorite = isfavorite;
    }

    public Job(int id, int type, String company, String position, String description, int isfavorite, String pdfbase64) {
        this.id = id;
        this.type = type;
        this.company = company;
        this.position = position;
        this.description = description;
        this.isfavorite = isfavorite;
        this.pdfbase64 = pdfbase64;
    }

    public Job(int type, String company, String position, String description, int isfavorite) {
        this.type = type;
        this.company = company;
        this.position = position;
        this.description = description;
        this.isfavorite = isfavorite;
    }

    public Job(int type, String company, String position, String description, int isfavorite, String pdfbase64) {
        this.type = type;
        this.company = company;
        this.position = position;
        this.description = description;
        this.isfavorite = isfavorite;
        this.pdfbase64 = pdfbase64;
    }


    @Override
    public String toString() {
        return "<b>" + company + "</b><br>" + position;
    }
}
