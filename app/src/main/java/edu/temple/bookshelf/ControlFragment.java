package edu.temple.bookshelf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ControlFragment extends Fragment {

    TextView nowPlayingTextView;
    ImageButton playButton;
    ImageButton pauseButton;
    ImageButton stopButton;
    SeekBar seekBar;
    Book book;

    private ControlFragmentInterface listener;

    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance(Book book) {

        ControlFragment newFragment = new ControlFragment();

        Bundle newBundle = new Bundle();
        newBundle.putParcelable("book", book);
        newFragment.setArguments(newBundle);

        return newFragment;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ControlFragmentInterface) {
            listener = (ControlFragmentInterface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement ControlFragment.ControlFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle myBundle = getArguments();

        if (myBundle != null) {
            book = myBundle.getParcelable("book");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_control, container, false);

        nowPlayingTextView = (TextView) layout.findViewById(R.id.nowPlayingTextView);
        playButton = layout.findViewById(R.id.playButton);
        pauseButton = layout.findViewById(R.id.pauseButton);
        stopButton = layout.findViewById(R.id.stopButton);
        seekBar = layout.findViewById(R.id.seekBar);
        String np = "Now Playing: ";
        String title = "";

        if (book != null) {
            title = book.getTitle();
            title = np + title;
        }

        nowPlayingTextView.setText(title);

        playButton.setOnClickListener(v -> listener.onPlayClick());

        pauseButton.setOnClickListener(v -> listener.onPauseClick());

        stopButton.setOnClickListener(v -> listener.onStopClick());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    listener.onSeekBarChange(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return layout;
    }

    interface ControlFragmentInterface {
        void onPlayClick();
        void onPauseClick();
        void onStopClick();
        void onSeekBarChange(int position);
    }
}