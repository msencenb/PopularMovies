package com.mattsencenbaugh.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mattsencenbaugh.popularmovies.databinding.PlotBinding;

/**
 * Created by msencenb on 12/19/17.
 */

public class PlotActivity extends AppCompatActivity {
    PlotBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.plot);
        mBinding = DataBindingUtil.setContentView(this, R.layout.plot);

        Intent intent = getIntent();
        if (intent.hasExtra("Plot")) {
            mBinding.tvMoviePlot.setText((String) intent.getSerializableExtra("Plot"));
        }
    }
}
