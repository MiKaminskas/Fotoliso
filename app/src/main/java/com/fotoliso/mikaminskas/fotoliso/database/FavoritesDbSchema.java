package com.fotoliso.mikaminskas.fotoliso.database;

public class FavoritesDbSchema {
    public static final class FavoritesTable{
        public static final String NAME = "favorites";

        public static final class Cols{
            public static final String PERFORMER_NAME = "performer_name";
            public static final String ID = "id";
            public static final String REVIEWS = "reviews";
            public static final String RATING = "rating";
            public static final String AVA_THUMB = "ava_thumb";
        }
    }
}
