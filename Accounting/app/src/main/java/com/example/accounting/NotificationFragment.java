package com.example.accounting;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/* *
 *Author: Goat Chen
 */

public class NotificationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification,container,false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        VideoView videoView = getActivity().findViewById(R.id.vvVideo1);
        VideoView videoView1 = getActivity().findViewById(R.id.vvVideo2);

        //video 1
        String videoPath = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.howtomakestressyourfriend;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        //video 2
        String videoPath1 = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.howtogetyourbraintofocus;
        Uri uri1 = Uri.parse(videoPath1);
        videoView1.setVideoURI(uri1);

        MediaController mediaController1 = new MediaController(getContext());
        videoView1.setMediaController(mediaController1);
        mediaController1.setAnchorView(videoView1);

        // let it focus on first one video view when initial
        videoView.setFocusable(true);
        videoView.setFocusableInTouchMode(true);
        videoView.requestFocus();
    }
}
