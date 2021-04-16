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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_control, container, false);

        nowPlayingTextView = layout.findViewById(R.id.nowPlayingTextView);
        nowPlayingTextView.setVisibility(View.INVISIBLE);

        playButton = layout.findViewById(R.id.playButton);
        pauseButton = layout.findViewById(R.id.pauseButton);
        stopButton = layout.findViewById(R.id.stopButton);
        seekBar = layout.findViewById(R.id.seekBar);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPlayingTextView.setVisibility(View.VISIBLE);
                listener.onPlayClick();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPauseClick();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStopClick();
            }
        });

        return layout;
    }

    interface ControlFragmentInterface {
        void onPlayClick();
        void onPauseClick();
        void onStopClick();
    }
}