package com.ugmobi.unitconversion.main;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.ugmobi.unitconversion.R;

public class WeightFragmnet extends Fragment {
    AppCompatTextView miligram, gram, kilogram;
    EditText value;
    AppCompatSpinner lengthitems;
    String[] items = {"mg", "g", "kg"};
    int choice;
    AppCompatButton convert;

    public WeightFragmnet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_fragmnet, container, false);
        miligram = view.findViewById(R.id.miligram);
        gram = view.findViewById(R.id.gram);
        kilogram = view.findViewById(R.id.kilogram);
        value = view.findViewById(R.id.value);
        lengthitems = view.findViewById(R.id.lengthitems);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        lengthitems.setAdapter(adapter);
        convert = view.findViewById(R.id.convert);
        convert.setOnClickListener(v -> {
            double getvalue = Integer.parseInt(value.getText().toString());
            choice = lengthitems.getSelectedItemPosition();
            if (choice == 0) {
                getActivity().runOnUiThread(() -> {
                    double lengthIngram = convertMilligramsToGrams(getvalue);
                    miligram.setText(String.valueOf(getvalue));
                    gram.setText(String.valueOf(lengthIngram));
                    double lengthkilogram = convertMilligramsToKilograms(getvalue);
                    kilogram.setText(String.valueOf(lengthkilogram));
                });

            } else if (choice == 1) {
                getActivity().runOnUiThread(() -> {
                    double lengthInMIligram = convertGramsToMilligrams(getvalue);
                    miligram.setText(String.valueOf(lengthInMIligram));
                    gram.setText(String.valueOf(getvalue));
                    double lengthkilogram = convertGramsToKilograms(getvalue);
                    kilogram.setText(String.valueOf(lengthkilogram));
                });
            } else if (choice == 2) {
                getActivity().runOnUiThread(() -> {
                    double lengthInMIligram = convertKilogramsToMilligrams(getvalue);
                    miligram.setText(String.valueOf(lengthInMIligram));
                    double lengthtogram = convertKilogramsToGrams(getvalue);
                    gram.setText(String.valueOf(lengthtogram));
                    kilogram.setText(String.valueOf(getvalue));
                });
            }
        });

        return view;
    }

    public static double convertKilogramsToGrams(double kilograms) {
        // Conversion factor: 1 kilogram = 1000 grams
        final double CONVERSION_FACTOR = 1000.0;
        // Perform the conversion
        double grams = kilograms * CONVERSION_FACTOR;
        return grams;
    }

    public static double convertKilogramsToMilligrams(double kilograms) {
        // Conversion factor: 1 kilogram = 1,000,000 milligrams
        final double CONVERSION_FACTOR = 1_000_000.0;
        // Perform the conversion
        double milligrams = kilograms * CONVERSION_FACTOR;
        return milligrams;
    }

    public static double convertGramsToKilograms(double grams) {
        // Conversion factor: 1 kilogram = 1000 grams
        final double CONVERSION_FACTOR = 0.001;
        // Perform the conversion
        double kilograms = grams * CONVERSION_FACTOR;
        return kilograms;
    }

    public static double convertGramsToMilligrams(double grams) {
        // Conversion factor: 1 gram = 1000 milligrams
        final double CONVERSION_FACTOR = 1000.0;
        // Perform the conversion
        double milligrams = grams * CONVERSION_FACTOR;
        return milligrams;
    }

    public static double convertMilligramsToKilograms(double milligrams) {
        // Conversion factor: 1 kilogram = 1,000,000 milligrams
        final double CONVERSION_FACTOR = 1_000_000.0;
        // Perform the conversion
        double kilograms = milligrams / CONVERSION_FACTOR;
        return kilograms;
    }


    public static double convertMilligramsToGrams(double milligrams) {
        // Conversion factor: 1 gram = 1000 milligrams
        final double CONVERSION_FACTOR = 1000.0;
        // Perform the conversion
        double grams = milligrams / CONVERSION_FACTOR;

        return grams;
    }
}