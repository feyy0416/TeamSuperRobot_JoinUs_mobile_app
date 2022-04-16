package edu.neu.madcourse.joinus;

public class Event {

    private String eventId;
    private long latitude;
    private long longitude;
    private long time;
    private String announcer;
    private String imageId;

    public Event(){}

    public Event(String eventId, long latitude, long longitude, long time, String announcer, String imageId){
        this.eventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.announcer = announcer;
        this.imageId = imageId;
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
    public String getImageId() {
        return imageId;
    }

}
