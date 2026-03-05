package com.evently.events.artists.dto.mapper;

import com.evently.events.artists.dto.ArtistDetails;
import com.evently.events.artists.dto.ArtistSeed;
import com.evently.events.artists.dto.request.ArtistRequest;
import com.evently.events.artists.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {
    public Artist toArtist(ArtistRequest artistRequest, String imageUrl) {
        Artist artist = new Artist();
        artist.setName(artistRequest.getName());
        artist.setBio(artistRequest.getBio());
        artist.setImageUrl(imageUrl);
        return artist;
    }

    public ArtistDetails toArtistDetails(Artist artist) {
        ArtistDetails artistDetails = new ArtistDetails();
        artistDetails.setId(artist.getId());
        artistDetails.setName(artist.getName());
        artistDetails.setBio(artist.getBio());
        artistDetails.setImageUrl(artist.getImageUrl());
        return artistDetails;
    }

    public Artist toArtist(ArtistSeed artistSeed) {
        Artist artist = new Artist();
        artist.setName(artistSeed.getName());
        artist.setBio(artistSeed.getBio());
        artist.setImageUrl(artistSeed.getImageUrl());
        return artist;
    }
}