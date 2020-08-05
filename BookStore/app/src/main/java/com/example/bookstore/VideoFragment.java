package com.example.bookstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/* *
 *Author: Goat Chen
 */

public class VideoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_video,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        VideoView videoView = getActivity().findViewById(R.id.vvVideo1);
        VideoView videoView1 = getActivity().findViewById(R.id.vvVideo2);
        VideoView videoView2 = getActivity().findViewById(R.id.vvVideo3);

        String videoPath = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.howtostudy;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        String videoPath1 = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.howtogetyourbraintofocus;
        Uri uri1 = Uri.parse(videoPath1);
        videoView1.setVideoURI(uri1);

        MediaController mediaController1 = new MediaController(getContext());
        videoView1.setMediaController(mediaController1);
        mediaController1.setAnchorView(videoView1);

        String videoPath2 = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.howtomakestressyourfriend;
        Uri uri2 = Uri.parse(videoPath2);
        videoView2.setVideoURI(uri2);

        MediaController mediaController2 = new MediaController(getContext());
        videoView2.setMediaController(mediaController2);
        mediaController2.setAnchorView(videoView2);

        videoView.setFocusable(true);
        videoView.setFocusableInTouchMode(true);
        videoView.requestFocus();

    }
}
