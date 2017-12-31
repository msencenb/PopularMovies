package com.mattsencenbaugh.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattsencenbaugh.popularmovies.R;

import org.w3c.dom.Text;

/**
 * Created by msencenb on 12/19/17.
 */

public class PlotFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plot, container, false);
        String plot = getArguments().getString("plot");
        TextView plotTv = (TextView)view.findViewById(R.id.tv_movie_plot);
        plotTv.setText(plot);
        return view;
    }
}
