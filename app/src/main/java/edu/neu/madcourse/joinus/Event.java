package edu.neu.madcourse.joinus;

public class Event {

    private String eventId;
    private long latitude;
    private long longitude;
    private String time;
    private String announcer;
    private int imageId;
    private String category;
    private String title;
    private String description;
    private String email;

    public Event(String eventId, long latitude, long longitude, String time, String announcer, int imageId, String category, String title, String description, String email){
        this.eventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.announcer = announcer;
        this.category = category;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }
    public String getEventId() {
        return eventId;
    }
    public long getLatitude() {
        return latitude;
    }
    public long getLongitude() { return longitude; }
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
}
