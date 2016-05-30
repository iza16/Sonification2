package com.example.iza.sonifikacja.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.iza.sonifikacja.pictures.filters.Average;
import com.example.iza.sonifikacja.pictures.filters.Canny;
import com.example.iza.sonifikacja.pictures.filters.Gaussian;
import com.example.iza.sonifikacja.pictures.filters.Gray;
import com.example.iza.sonifikacja.pictures.filters.HP1;
import com.example.iza.sonifikacja.pictures.filters.Laplacian;
import com.example.iza.sonifikacja.pictures.filters.MeanRemoval;
import com.example.iza.sonifikacja.pictures.filters.Median;
import com.example.iza.sonifikacja.pictures.filters.Original;
import com.example.iza.sonifikacja.pictures.filters.Sobel;
import com.example.iza.sonifikacja.pictures.test.OriginalMatSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GestureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GestureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestureFragment extends Fragment {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    private int x;
    private int y;
    public List<String> listOfFilter = new ArrayList<String>();
    public Spinner spinner;
    public LinearLayout linearLayout;

    public GestureFragment() {
    }

    public static GestureFragment newInstance(int x, int y) {
        GestureFragment fragment = new GestureFragment();
        Bundle args = new Bundle();
        args.putInt(HEIGHT, y);
        args.putInt(WIDTH, x);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            x = getArguments().getInt(WIDTH);
            y = getArguments().getInt(HEIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams((x - y), y));
        linearLayout.requestLayout();

        spinner = new Spinner(getActivity());
        spinner.setLayoutParams(new LinearLayout.LayoutParams((x - y), 250));
        listOfFilter.add("Original");
        listOfFilter.add("Average");
        listOfFilter.add("Canny");
        listOfFilter.add("Gaussian");
        listOfFilter.add("Gray");
        listOfFilter.add("HP1");
        listOfFilter.add("Laplacian");
        listOfFilter.add("Mean Removal");
        listOfFilter.add("Median");
        listOfFilter.add("Sobel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listOfFilter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPosition = listOfFilter.get(position);
                switch (selectedPosition) {
                    case "Original":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Original());
                        break;
                    case "Average":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Average());
                        break;
                    case "Canny":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Canny());
                        break;
                    case "Gaussian":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Gaussian());
                        break;
                    case "Gray":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Gray());
                        break;
                    case "HP1":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new HP1());
                        break;
                    case "Laplacian":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Laplacian());
                        break;
                    case "Mean Removal":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new MeanRemoval());
                        break;
                    case "Median":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Median());
                        break;
                    case "Sobel":
                        OriginalMatSingleton.getInstance().setCurretlyFilter(new Sobel());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                OriginalMatSingleton.getInstance().setCurretlyFilter(new Original());
            }
        });
        linearLayout.addView(spinner);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                Log.i("->>>>>>>>>>>>>> gesture", x + " " + y);
                return false;
            }
        });
        return linearLayout;
    }





    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
