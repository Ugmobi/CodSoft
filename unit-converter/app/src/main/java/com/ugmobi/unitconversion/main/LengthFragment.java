package com.ugmobi.unitconversion.main;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ugmobi.unitconversion.R;

public class LengthFragment extends Fragment {
    AppCompatTextView milimeter, centimeter, meter, kilomter, inch;
    EditText value;
    AppCompatSpinner lengthitems;
    String[] items = {"mm", "cm", "m", "km", "inch"};
    int choice;
    AppCompatButton convert;

    public LengthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_length, container, false);
        milimeter = view.findViewById(R.id.milimiter);
        centimeter = view.findViewById(R.id.centimeter);
        meter = view.findViewById(R.id.meter);
        kilomter = view.findViewById(R.id.kilometer);
        inch = view.findViewById(R.id.inch);
        value = view.findViewById(R.id.value);
        lengthitems = view.findViewById(R.id.lengthitems);
        choice = 1;
        convert = view.findViewById(R.id.convert);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        lengthitems.setAdapter(adapter);

        lengthitems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        convert.setOnClickListener(v -> {
            try {
                double getvalue = Integer.parseInt(value.getText().toString());
                choice = lengthitems.getSelectedItemPosition();
                if (choice == 0) {
                    getActivity().runOnUiThread(() -> {
                        double lengthInCentimeters = convertMillimetersToCentimeters(getvalue);
                        milimeter.setText(String.valueOf(getvalue));
                        centimeter.setText(String.valueOf(lengthInCentimeters));
                        double lengthInMeters = convertMillimetersToMeters(getvalue);
                        meter.setText(String.valueOf(lengthInMeters));
                        double lengthInKilometers = convertMillimetersToKilometers(getvalue);
                        kilomter.setText(String.valueOf(lengthInKilometers));
                        double lengthInInches = convertMillimetersToInches(getvalue);
                        inch.setText(String.valueOf(lengthInInches));
                    });

                } else if (choice == 1) {
                    getActivity().runOnUiThread(() -> {
                        double lengthInMilimeter = convertCentimetersToMillimeters(getvalue);
                        milimeter.setText(String.valueOf(lengthInMilimeter));
                        centimeter.setText(String.valueOf(getvalue));
                        double lengthInMeters = convertCentimetersToMeters(getvalue);
                        meter.setText(String.valueOf(lengthInMeters));
                        double lengthInKilometers = convertCentimetersToKilometers(getvalue);
                        kilomter.setText(String.valueOf(lengthInKilometers));
                        double lengthInInches = convertCentimetersToInches(getvalue);
                        inch.setText(String.valueOf(lengthInInches));
                    });

                } else if (choice == 2) {
                    getActivity().runOnUiThread(() -> {
                        double lengthInMilimeter = convertMetersToMillimeters(getvalue);
                        milimeter.setText(String.valueOf(lengthInMilimeter));
                        double lengthincentimeter = convertMetersToCentimeters(getvalue);
                        centimeter.setText(String.valueOf(lengthincentimeter));
                        meter.setText(String.valueOf(getvalue));
                        double lengthInKilometers = convertMetersToKilometers(getvalue);
                        kilomter.setText(String.valueOf(lengthInKilometers));
                        double lengthInInches = convertMetersToInches(getvalue);
                        inch.setText(String.valueOf(lengthInInches));
                    });
                } else if (choice == 3) {
                    getActivity().runOnUiThread(() -> {
                        double lengthInMilimeter = convertKilometersToMillimeters(getvalue);
                        milimeter.setText(String.valueOf(lengthInMilimeter));
                        double lengthincentimeter = convertKilometersToCentimeters(getvalue);
                        centimeter.setText(String.valueOf(lengthincentimeter));
                        double lengthinmeter = convertKilometersToMeters(getvalue);
                        meter.setText(String.valueOf(lengthinmeter));
                        kilomter.setText(String.valueOf(getvalue));
                        double lengthInInches = convertKilometersToInches(getvalue);
                        inch.setText(String.valueOf(lengthInInches));
                    });

                } else if (choice == 4) {
                    getActivity().runOnUiThread(() -> {
                        double lengthInMilimeter = convertInchesToMillimeters(getvalue);
                        milimeter.setText(String.valueOf(lengthInMilimeter));
                        double lengthincentimeter = convertInchesToCentimeters(getvalue);
                        centimeter.setText(String.valueOf(lengthincentimeter));
                        double lengthinmeter = convertInchesToMeters(getvalue);
                        meter.setText(String.valueOf(lengthinmeter));
                        double lengthInKilometers = convertInchesToKilometers(getvalue);
                        kilomter.setText(String.valueOf(lengthInKilometers));
                        inch.setText(String.valueOf(getvalue));
                    });
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        });

        return view;
    }

    public static double convertInchesToKilometers(double inches) {
        // Conversion factor: 1 inch = 2.54e-5 kilometers
        final double CONVERSION_FACTOR = 2.54e-5;

        // Perform the conversion
        double kilometers = inches * CONVERSION_FACTOR;

        return kilometers;
    }

    public static double convertInchesToMeters(double inches) {
        // Conversion factor: 1 inch = 0.0254 meters
        final double CONVERSION_FACTOR = 0.0254;

        // Perform the conversion
        double meters = inches * CONVERSION_FACTOR;

        return meters;
    }


    public static double convertInchesToCentimeters(double inches) {
        // Conversion factor: 1 inch = 2.54 centimeters
        final double CONVERSION_FACTOR = 2.54;

        // Perform the conversion
        double
                centimeters = inches * CONVERSION_FACTOR;

        return centimeters;
    }
    public static double convertInchesToMillimeters(double inches) {
        // Conversion factor: 1 inch = 25.4 millimeters
        final double CONVERSION_FACTOR = 25.4;

        // Perform the conversion
        double millimeters = inches * CONVERSION_FACTOR;

        return millimeters;
    }

    public static double convertKilometersToInches(double kilometers) {
        // Conversion factor: 1 kilometer = 39370.1 inches
        final double CONVERSION_FACTOR = 39370.1;

        // Perform the conversion
        double inches = kilometers * CONVERSION_FACTOR;

        return inches;
    }

    public static double convertKilometersToMeters(double kilometers) {
        // Conversion factor: 1 kilometer = 1000 meters
        final double CONVERSION_FACTOR = 1000.0;

        // Perform the conversion
        double meters = kilometers * CONVERSION_FACTOR;

        return meters;
    }

    public static double convertKilometersToCentimeters(double kilometers) {
        // Conversion factor: 1 kilometer = 100,000 centimeters
        final double CONVERSION_FACTOR = 100_000.0;

        // Perform the conversion
        double centimeters = kilometers * CONVERSION_FACTOR;

        return centimeters;
    }

    public static double convertKilometersToMillimeters(double kilometers) {
        // Conversion factor: 1 kilometer = 1,000,000 millimeters
        final double CONVERSION_FACTOR = 1_000_000.0;

        // Perform the conversion
        double millimeters = kilometers * CONVERSION_FACTOR;

        return millimeters;
    }

    public static double convertMetersToInches(double meters) {
        // Conversion factor: 1 meter = 39.3701 inches
        final double CONVERSION_FACTOR = 39.3701;

        // Perform the conversion
        double inches = meters * CONVERSION_FACTOR;

        return inches;
    }

    public static double convertMetersToKilometers(double meters) {
        // Conversion factor: 1 kilometer = 1000 meters
        final double CONVERSION_FACTOR = 0.001;

        // Perform the conversion
        double kilometers = meters * CONVERSION_FACTOR;

        return kilometers;
    }

    public static double convertMetersToCentimeters(double meters) {
        // Conversion factor: 1 meter = 100 centimeters
        final double CONVERSION_FACTOR = 100.0;

        // Perform the conversion
        double centimeters = meters * CONVERSION_FACTOR;

        return centimeters;
    }

    public static double convertMetersToMillimeters(double meters) {
        // Conversion factor: 1 meter = 1000 millimeters
        final double CONVERSION_FACTOR = 1000.0;

        // Perform the conversion
        double millimeters = meters * CONVERSION_FACTOR;

        return millimeters;
    }

    public static double convertCentimetersToInches(double centimeters) {
        // Conversion factor: 1 inch = 2.54 centimeters
        final double CONVERSION_FACTOR = 2.54;

        // Perform the conversion
        double inches = centimeters / CONVERSION_FACTOR;

        return inches;
    }


    public static double convertCentimetersToKilometers(double centimeters) {
        // Conversion factor: 1 kilometer = 100,000 centimeters
        final double CONVERSION_FACTOR = 100000.0;

        // Perform the conversion
        double kilometers = centimeters / CONVERSION_FACTOR;

        return kilometers;
    }


    public static double convertCentimetersToMeters(double centimeters) {
        // Conversion factor: 1 meter = 100 centimeters
        final double CONVERSION_FACTOR = 100.0;

        // Perform the conversion
        double meters = centimeters / CONVERSION_FACTOR;

        return meters;
    }

    public static double convertCentimetersToMillimeters(double centimeters) {
        // Conversion factor: 1 centimeter = 10 millimeters
        final double CONVERSION_FACTOR = 10.0;

        // Perform the conversion
        double millimeters = centimeters * CONVERSION_FACTOR;

        return millimeters;
    }

    public static double convertMillimetersToKilometers(double millimeters) {
        // Conversion factor: 1 kilometer = 1,000,000 millimeters
        final double CONVERSION_FACTOR = 1_000_000.0;

        // Perform the conversion
        double kilometers = millimeters / CONVERSION_FACTOR;

        return kilometers;
    }


    public static double convertMillimetersToMeters(double millimeters) {
        // Conversion factor: 1 meter = 1000 millimeters
        final double CONVERSION_FACTOR = 1000.0;

        // Perform the conversion
        double meters = millimeters / CONVERSION_FACTOR;

        return meters;
    }

    public static double convertMillimetersToCentimeters(double millimeters) {
        // Conversion factor: 1 centimeter = 10 millimeters
        final double CONVERSION_FACTOR = 10.0;
        // Perform the conversion
        double centimeters = millimeters / CONVERSION_FACTOR;
        return centimeters;
    }

    public static double convertMillimetersToInches(double millimeters) {
        // Conversion factor: 1 inch = 25.4 millimeters
        final double CONVERSION_FACTOR = 25.4;

        // Perform the conversion
        double inches = millimeters / CONVERSION_FACTOR;

        return inches;
    }
}