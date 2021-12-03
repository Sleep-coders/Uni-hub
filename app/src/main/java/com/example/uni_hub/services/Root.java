package com.example.uni_hub.services;

import java.util.List;

public class Root{
    public List<Object> geocoded_waypoints;
    public List<Route> routes;
    public String status;

    public class Bounds{
    }

    public static class Distance{
        public String text;
        public int value;

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class Duration{
        public String text;
        public int value;

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class EndLocation{
        public double lat;
        public double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class StartLocation{
        public double lat;
        public double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class Polyline{
        public String points;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }

    public static class Step{
        public Distance distance;
        public Duration duration;
        public EndLocation end_location;
        public String html_instructions;
        public Polyline polyline;
        public StartLocation start_location;
        public String travel_mode;
        public String maneuver;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public EndLocation getEnd_location() {
            return end_location;
        }

        public void setEnd_location(EndLocation end_location) {
            this.end_location = end_location;
        }

        public String getHtml_instructions() {
            return html_instructions;
        }

        public void setHtml_instructions(String html_instructions) {
            this.html_instructions = html_instructions;
        }

        public Polyline getPolyline() {
            return polyline;
        }

        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }

        public StartLocation getStart_location() {
            return start_location;
        }

        public void setStart_location(StartLocation start_location) {
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

    public static class Leg{
        public Distance distance;
        public Duration duration;
        public String end_address;
        public EndLocation end_location;
        public String start_address;
        public StartLocation start_location;
        public List<Step> steps;
        public List<Object> traffic_speed_entry;
        public List<Object> via_waypoint;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public EndLocation getEnd_location() {
            return end_location;
        }

        public void setEnd_location(EndLocation end_location) {
            this.end_location = end_location;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public StartLocation getStart_location() {
            return start_location;
        }

        public void setStart_location(StartLocation start_location) {
            this.start_location = start_location;
        }

        public List<Step> getSteps() {
            return steps;
        }

        public void setSteps(List<Step> steps) {
            this.steps = steps;
        }

        public List<Object> getTraffic_speed_entry() {
            return traffic_speed_entry;
        }

        public void setTraffic_speed_entry(List<Object> traffic_speed_entry) {
            this.traffic_speed_entry = traffic_speed_entry;
        }

        public List<Object> getVia_waypoint() {
            return via_waypoint;
        }

        public void setVia_waypoint(List<Object> via_waypoint) {
            this.via_waypoint = via_waypoint;
        }
    }

    public static class OverviewPolyline{
        public String points;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }

    public static class Route{
        public Bounds bounds;
        public String copyrights;
        public List<Leg> legs;
        public OverviewPolyline overview_polyline;
        public String summary;
        public List<Object> warnings;
        public List<Object> waypoint_order;

        public Bounds getBounds() {
            return bounds;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }

        public List<Leg> getLegs() {
            return legs;
        }

        public void setLegs(List<Leg> legs) {
            this.legs = legs;
        }

        public OverviewPolyline getOverview_polyline() {
            return overview_polyline;
        }

        public void setOverview_polyline(OverviewPolyline overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<Object> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<Object> warnings) {
            this.warnings = warnings;
        }

        public List<Object> getWaypoint_order() {
            return waypoint_order;
        }

        public void setWaypoint_order(List<Object> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }
    }

    public List<Object> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }
}


