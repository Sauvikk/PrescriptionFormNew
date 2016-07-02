package com.example.souvik.prescriptionformnew;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.CardHeader;


public class CustomHeaderForMedicineAllCard extends CardHeader {

    public CustomHeaderForMedicineAllCard(Context context) {
        super(context, R.layout.custom_header_layout_for_medicine_all_card);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {


    }
}