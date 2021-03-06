package com.example.root.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Emergency.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Emergency#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Emergency extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static TextView countdownLabel;

    //For the timer:
    private CountDownTimer emergencyCountDown;
    private boolean timerIsRunning = false;

    public Emergency() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Emergency.
     */
    // TODO: Rename and change types and number of parameters
    public static Emergency newInstance(String param1, String param2) {
        Emergency fragment = new Emergency();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Bad practice, but this method declares the listeners for most of the button on the screen (ie most of the functionality)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        countdownLabel = (TextView) view.findViewById(R.id.countdown_labal);
        final ImageView emergencyStopButton = (ImageView) getView().findViewById(R.id.emergency_stop_button);
        final ImageView countdownButton = (ImageView) getView().findViewById(R.id.countdown_emergency_button);
        final FrameLayout cancel_frame = (FrameLayout) getView().findViewById(R.id.cancel_frame);
        final Button emergency_stop_stop = (Button) getView().findViewById(R.id.emergency_stop_stop);
        final Button emergency_stop_cancel = (Button) getView().findViewById(R.id.emergency_stop_cancel);

        countdownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                countdownLabel.setVisibility(View.VISIBLE);
                countdownButton.setVisibility(View.INVISIBLE);
                emergencyStopButton.setVisibility(View.VISIBLE);
                if(!timerIsRunning) {
                    emergencyCountDown = new CountDownTimer(11000, 1000) {
                        @Override
                        public void onTick(long l) {
                            timerIsRunning = true;
                            int currentTime = Integer.parseInt(countdownLabel.getText().toString());
                            currentTime--;
                            countdownLabel.setText("" + currentTime);
                        }

                        @Override
                        public void onFinish() {
                            timerIsRunning = false;
                            countdownLabel.setText("11");
                            countdownLabel.setVisibility(View.INVISIBLE);
                            countdownButton.setVisibility(View.VISIBLE);
                            emergencyStopButton.setVisibility(View.INVISIBLE);
                            cancel_frame.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                }else {
                    emergencyCountDown.cancel();
                    emergencyCountDown.onFinish();
                }
                return false;
            }
        });
        emergencyStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_frame.setVisibility(View.VISIBLE);
            }
        });
        emergency_stop_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Copied and pasted from OnFinish method above ^
                timerIsRunning = false;
                countdownLabel.setText("11");
                countdownLabel.setVisibility(View.INVISIBLE);
                countdownButton.setVisibility(View.VISIBLE);
                emergencyStopButton.setVisibility(View.INVISIBLE);
                cancel_frame.setVisibility(View.INVISIBLE);
            }
        });
        emergency_stop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_frame.setVisibility(View.INVISIBLE);
            }
        });
        getView().setOnTouchListener(((MainActivity)getActivity()).touchListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.emergency, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
