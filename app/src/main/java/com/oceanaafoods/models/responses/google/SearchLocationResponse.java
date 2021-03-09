package com.oceanaafoods.models.responses.google;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchLocationResponse {
    @SerializedName("predictions")
    public List<Candidates> predictions;
    @SerializedName("geometry")
    public Geometry geometry;

    public class Candidates {
        @SerializedName("description")
        public String description;
        @SerializedName("place_id")
        public String place_id;
    }

    public class Geometry {
        @SerializedName("location")
        public Location location;
    }

    public class Location {
        @SerializedName("lat")
        public Double lat;
        @SerializedName("lng")
        public Double lng;
    }
}

