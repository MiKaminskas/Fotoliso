package com.fotoliso.mikaminskas.fotoliso;

import java.util.List;

/**
 * Created by misha on 3/18/2018.
 */

public class Performer {
    private String id;
    private String login;
    private String fio;
    private String contacts;
    private String avatar;
    private int registered;
    private String name;
    private String surname;
    private String stringid;
    private String city;
    private String ava_thumb;
    private double rating;
    private int reviews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStringid() {
        return stringid;
    }

    public void setStringid(String stringid) {
        this.stringid = stringid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAva_thumb() {
        return ava_thumb;
    }

    public void setAva_thumb(String ava_thumb) {
        this.ava_thumb = ava_thumb;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getVk_url() {
        return vk_url;
    }

    public void setVk_url(String vk_url) {
        this.vk_url = vk_url;
    }

    public String getFb_url() {
        return fb_url;
    }

    public void setFb_url(String fb_url) {
        this.fb_url = fb_url;
    }

    public String getGp_url() {
        return gp_url;
    }

    public void setGp_url(String gp_url) {
        this.gp_url = gp_url;
    }

    public String getYt_url() {
        return yt_url;
    }

    public void setYt_url(String yt_url) {
        this.yt_url = yt_url;
    }

    public String getV1_url() {
        return v1_url;
    }

    public void setV1_url(String v1_url) {
        this.v1_url = v1_url;
    }

    public String getV2_url() {
        return v2_url;
    }

    public void setV2_url(String v2_url) {
        this.v2_url = v2_url;
    }

    public String getV3_url() {
        return v3_url;
    }

    public void setV3_url(String v3_url) {
        this.v3_url = v3_url;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    private String vk_url;
    private String fb_url;
    private String gp_url;
    private String yt_url;
    private String v1_url;
    private String v2_url;
    private String v3_url;
    private String languages;
    private String about;
    //public List<Foto> fotos;
    Performer(){

    }
}
