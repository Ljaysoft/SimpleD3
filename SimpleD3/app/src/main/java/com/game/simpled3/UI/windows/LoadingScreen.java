package com.game.simpled3.UI.windows;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.game.simpled3.BuildConfig;
import com.game.simpled3.R;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.utils.FontHelper;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JFCaron on 2015-06-06.
 */
public class LoadingScreen extends DialogFragment {
    private OnLoadingScreenInteractionListener mListener;
    private static final int UPDATE_TIME_MS = 200;
    private final Timer mTimer = new Timer();

    @InjectView(R.id.loadingProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.okSkipLoadButton)
    ImageButton mSkipButton;
    private final Handler mHandler = new Handler();

    public LoadingScreen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.loading_screen_layout, container, false);
        ButterKnife.inject(this, rootView);
        if (BuildConfig.DEBUG) {
            mSkipButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ItemFactory.skipLoading();
                            onStopLoading();
                        }
                    }
            );
        } else {
            mSkipButton.setVisibility(View.GONE);
        }

        FontHelper.applyFont(rootView, false, true);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoadingScreenInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLoadingScreenInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Start updateUI task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadingScreen loadingScreen = (LoadingScreen) getFragmentManager().findFragmentByTag("loading_screen");
                        if (loadingScreen != null) {
                            loadingScreen.updateUI();
                        }
                    }
                });
            }
        };
        mTimer.schedule(task, 0, UPDATE_TIME_MS);
    }

    private void updateUI() {
        if (ItemFactory.isReady()) {
            onStopLoading();
            return;
        }
        mProgressBar.setProgress(ItemFactory.getLoadProgress());
    }

    private void onStopLoading() {
        mTimer.cancel();
        mTimer.purge();
        mListener.OnLoadingDone();
    }

    public interface OnLoadingScreenInteractionListener {
        void OnLoadingDone();
    }
}
