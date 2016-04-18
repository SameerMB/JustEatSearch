package com.example.sameer.justeatsearch;

import java.util.ArrayList;

public class SearchedContent {

    // list of VenueItem
    public static ArrayList<RestaurantItem> restaurantItemsList = new ArrayList<>();

    /**
     * Venue item.
     */
    public static class RestaurantItem {
        private int id;
        private String name;
        private String logo;
        private ArrayList<String> cuisineTypes;
        private double rating;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public ArrayList<String> getCuisineTypes() {
            return cuisineTypes;
        }

        public void setCuisineTypes(ArrayList<String> cuisineTypes) {
            this.cuisineTypes = cuisineTypes;
        }

    }
}
