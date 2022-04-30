package edu.neu.madcourse.joinus;

public class Event {

    private String eventId;
    private double latitude;
    private double longitude;
    private String time;
    private String announcer;
    private int imageId;
    private String category;
    private String title;
    private String description;
    private String email;
    private double distance;

    public Event(){}

    public Event(String eventId, double latitude, double longitude, String time, String announcer, int imageId, String category, String title, String description, String email){
        this.eventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.announcer = announcer;
        this.category = category;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.email = email;
        distance = 0;
    }
    public String getEventId() {
        return eventId;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() { return longitude; }
    public String getTime() {
        return time;
    }
    public String getAnnouncer() {
        return announcer;
    }
    public String getCategory() {
        return category;
    }
    public String getTitle() { return title;}
    public String getDescription() {
        return description;
    }
    public String getEmail(){ return email;}
    public int getImageId(){return imageId;}
    public void setDistance(double distance){this.distance = distance;}
    public double getDistance() { return distance; }
}
