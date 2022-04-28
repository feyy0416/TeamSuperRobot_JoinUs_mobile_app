package edu.neu.madcourse.joinus;

public class Event {

    private String eventId;
    private long latitude;
    private long longitude;
    private long time;
    private String announcer;
    private int imageId;
    private String category;
    private String title;
    private String description;

    public Event(){}

    public Event(String eventId, long latitude, long longitude, long time, String announcer, int imageId, String category, String title, String description){
        this.eventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.announcer = announcer;
        this.imageId = imageId;
        this.category = category;
        this.title = title;
        this.description = description;
    }
    public String getEventId() {
        return eventId;
    }
    public long getLatitude() {
        return latitude;
    }
    public long getLongitude() { return longitude; }
    public long getTime() {
        return time;
    }
    public String getAnnouncer() {
        return announcer;
    }
    public int getImageId() {
        return imageId;
    }
    public String getCategory() {
        return category;
    }
    public String getTitle() { return title;}
    public String getDescription() {
        return description;
    }
}
