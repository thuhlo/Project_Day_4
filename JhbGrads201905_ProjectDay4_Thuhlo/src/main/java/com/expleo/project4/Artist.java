package com.expleo.project4;

public class Artist
{
    int artistId;
    String artistName;

    public Artist(int artistId, String artistName)
    {
        setArtistId(artistId);
        setArtistName(artistName);
    }
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
