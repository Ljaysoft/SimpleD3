package com.game.simpled3.UI.windows;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.game.simpled3.R;
import com.game.simpled3.utils.FontHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JFCaron on 2015-05-15.
 */
public class DeathPage extends DialogFragment {

    private OnDeathPageInteractionListener mListener;

    @InjectView(R.id.okDeathButton) Button mOkButton;
    private DeathCountDownTimer mCountDownTimer;
    private final long DEATH_TIMER = 5000;
    private final long UPDATE_INTERVAL = 100;
    private boolean isRunning = false;

    public DeathPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        mCountDownTimer = new DeathCountDownTimer(DEATH_TIMER, UPDATE_INTERVAL);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.death_page_layout, container, false);
        ButterKnife.inject(this, rootView);
        mOkButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDeathPageInteraction(mOkButton);
                    }
                }
        );
        FontHelper.applyFont(rootView, false, true);
        startDeathTimer();
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDeathPageInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDeathPageInteractionListener");
        }
    }

    private class DeathCountDownTimer extends CountDownTimer {
        public DeathCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            mOkButton.setText("Ressurect Now.");
            mOkButton.setEnabled(true);
            isRunning = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mOkButton.setText("Waiting to revive " + String.valueOf((int) (millisUntilFinished / 1000)));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void startDeathTimer() {
        if (isRunning)
            return;
        mOkButton.setEnabled(false);
        mCountDownTimer.start();
        isRunning = true;
    }

    public interface OnDeathPageInteractionListener {
        void onDeathPageInteraction(View view);
    }
}
