package com.shlomisasportas;

public class Post {

    private String firstName;
    private String lastName;
    private String age;
    private String dateOfBirth;
    private String dadName;
    private String deathLocation;
    private String dateOfDeath;
    private String momName;
    private String profilePicture;
    private String city;
    private String part;
    private String parcel;
    private String row;
    private String gravenumber;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setGravenumber(String gravenumber) {
        this.gravenumber = gravenumber;
    }

    public void setParcel(String parcel) {
        this.parcel = parcel;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getRow() {
        return row;
    }

    public String getCity() {
        return city;
    }

    public String getParcel() {
        return parcel;
    }

    public String getGravenumber() {
        return gravenumber;
    }

    public String getPart() {
        return part;
    }

    public String getProfilePicture(){
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDadName(String dadName){
        this.dadName = dadName;
    }
    public String getDadName(){
        return dadName;
    }

    public void setDeathLocation(String deathLocation){
        this.deathLocation = deathLocation;
    }

    public String getDeathLocation(){
        return deathLocation;
    }

    public String getDateOfDeath(){
        return dateOfDeath;
    }
    public void setDateOfDeath(String dateOfDeath){
        this.dateOfDeath = dateOfDeath;
    }

    public String getMomName(){
        return momName;
    }
    public void setMomName(String momName){
        this.momName = momName;
    }

}