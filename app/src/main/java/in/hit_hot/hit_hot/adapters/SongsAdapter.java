package in.hit_hot.hit_hot.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.hit_hot.hit_hot.R;
import in.hit_hot.hit_hot.models.Album;
import in.hit_hot.hit_hot.models.Song;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SimpleViewHolder> {

    private final SongSelectedListener mSongSelectedListener;
    private final Context mContext;
    private List<Song> mSongs;
    private Album mAlbum;

    public SongsAdapter(@NonNull final Context context, @NonNull final Album album) {
        mContext = context;
        mAlbum = album;
        mSongs = mAlbum.getSongs();
        mSongSelectedListener = (SongSelectedListener) mContext;
    }

    public void swapSongs(@NonNull final Album album) {
        mAlbum = album;
        mSongs = mAlbum.getSongs();
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public SimpleViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.song_item, parent, false);
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, final int position) {

        final Song song = mSongs.get(holder.getAdapterPosition());
        final String songTitle = song.getSongTitle();

        final int songTrack = Song.formatTrack(song.getTrackNumber());

        holder.track.setText(String.valueOf(songTrack));
        holder.title.setText(songTitle);
        holder.duration.setText(Song.formatDuration(song.getSongDuration()));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public interface SongSelectedListener {
        void onSongSelected(@NonNull final Song song, @NonNull final List<Song> songs);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

        final TextView track, title, duration;
        private boolean sSongLongPressed = false;

        SimpleViewHolder(@NonNull final View itemView) {
            super(itemView);

            track = itemView.findViewById(R.id.track);
            title = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.duration);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnTouchListener(this);
        }

        @Override
        public void onClick(@NonNull final View v) {
            final Song song = mSongs.get(getAdapterPosition());
            mSongSelectedListener.onSongSelected(song, mAlbum.getSongs());
        }

        @Override
        public boolean onLongClick(@NonNull final View v) {
            if (!sSongLongPressed) {
                itemView.setSelected(true);
                sSongLongPressed = true;
            }
            return true;
        }

        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(@NonNull final View v, @NonNull final MotionEvent event) {
            if (sSongLongPressed && event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE || event.getAction() == MotionEvent.ACTION_MOVE) {
                itemView.setSelected(false);
                sSongLongPressed = false;
            }
            return false;
        }
    }
}