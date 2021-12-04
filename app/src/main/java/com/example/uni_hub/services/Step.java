package com.example.uni_hub.services;

public class Step {
    public Root.Distance distance;
    public Root.Duration duration;
    public Root.EndLocation end_location;
    public String html_instructions;
    public Root.Polyline polyline;
    public Root.StartLocation start_location;
    public String travel_mode;
    public String maneuver;


    public Step() {
    }

    public Step(Root.Distance distance, Root.Duration duration, Root.EndLocation end_location, String html_instructions, Root.Polyline polyline, Root.StartLocation start_location, String travel_mode, String maneuver) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.html_instructions = html_instructions;
        this.polyline = polyline;
        this.start_location = start_location;
        this.travel_mode = travel_mode;
        this.maneuver = maneuver;
    }

    public Root.Distance getDistance() {
        return distance;
    }

    public void setDistance(Root.Distance distance) {
        this.distance = distance;
    }

    public Root.Duration getDuration() {
        return duration;
    }

    public void setDuration(Root.Duration duration) {
        this.duration = duration;
    }

    public Root.EndLocation getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Root.EndLocation end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public Root.Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Root.Polyline polyline) {
        this.polyline = polyline;
    }

    public Root.StartLocation getStart_location() {
        return start_location;
    }

    public void setStart_location(Root.StartLocation start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }
}
