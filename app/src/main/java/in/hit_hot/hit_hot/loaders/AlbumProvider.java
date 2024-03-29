package in.hit_hot.hit_hot.loaders;

import in.hit_hot.hit_hot.models.Album;
import in.hit_hot.hit_hot.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class AlbumProvider {

    @NonNull
    static List<Album> retrieveAlbums(@Nullable final List<Song> songs) {
        final List<Album> albums = new ArrayList<>();
        if (songs != null) {
            try {
                for (Song song : songs) {
                    final Album album = getAlbum(albums, song.getAlbumName());
                    album.getSongs().add(song);
                    song.setSongAlbum(album);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (albums.size() > 1) {
            sortAlbums(albums);
        }
        return albums;
    }

    private static void sortAlbums(@NonNull final List<Album> albums) {
        Collections.sort(albums, (obj1, obj2) -> Integer.compare(obj1.getYear(), obj2.getYear()));
    }

    @NonNull
    private static Album getAlbum(@NonNull final List<Album> albums, @NonNull final String albumName) {
        for (Album album : albums) {
            if (!album.getSongs().isEmpty() && album.getSongs().get(0).getAlbumName().equals(albumName)) {
                return album;
            }
        }
        final Album album = new Album();
        albums.add(album);
        return album;
    }
}
