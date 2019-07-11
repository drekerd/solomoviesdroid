package com.example.solomovies.movie;

import lombok.Data;

@Data
public class Movie  {

    private long mId;
    private String mName;
    private String mDescription;
    private String mThumbnailFullLink;

    public Movie(long mId, String mName, String mDescription, String mThumbnailFullLink) {
        this.mId = mId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mThumbnailFullLink = mThumbnailFullLink;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mThumbnailFullLink='" + mThumbnailFullLink + '\'' +
                '}';
    }
}
