package com.example.souvik.prescriptionformnew;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.souvik.prescriptionformnew.DB.DatabaseHandler;
import com.example.souvik.prescriptionformnew.DB.Prescription;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Souvik on 3/19/2016.
 */
public class MedicineIndividualCard extends CardWithList {

    DatabaseHandler db = new DatabaseHandler(getContext());
    public MedicineIndividualCard(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {
       CardHeader cardHeader = new CustomHeaderForMedicineAllCard(getContext());
        return cardHeader;
    }

    @Override
    protected void initCard() {
        setSwipeable(false);
    }


    @Override
    protected List<ListObject> initChildren() {

        List<ListObject> mObjects = new ArrayList<ListObject>();
        List<Prescription> medicineList = db.get();
        for (Prescription p : medicineList) {
            MedicineObject m = new MedicineObject(this);
            m.medicineName = p.getMedicineName();
            m.medicineIcon = R.drawable.tablet;
            m.setObjectId(p.get_id());
            mObjects.add(m);

        }
        return mObjects;
    }


    public void updateItems() {

        List<Prescription> medicineList = db.get();
        if(medicineList.size()>0) {
            Prescription p = medicineList.get(medicineList.size() - 1);
            MedicineObject m = new MedicineObject(this);
            m.medicineName = p.getMedicineName();
            m.medicineIcon = R.drawable.tablet;
            m.setObjectId(p.get_id());
            mLinearListAdapter.add(m);
        }

    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        TextView medicine = (TextView) convertView.findViewById(R.id.medicine_name);
        ImageView icon = (ImageView) convertView.findViewById(R.id.medicine_icon);
        MedicineObject medicineObject = (MedicineObject) object;
        icon.setImageResource(medicineObject.medicineIcon);
        medicine.setText(medicineObject.medicineName);
        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.medicine_individual_card_layout;
    }


    // -------------------------------------------------------------
    // Medicine Object
    // -------------------------------------------------------------

    public class MedicineObject extends DefaultListObject {

        public String medicineName;
        public int medicineId;
        public int medicineIcon;
        public MedicineObject(Card parentCard){
            super(parentCard);
            init();
        }


        private void init(){
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Prescription p = db.get(object.getObjectId());
                    String toastText = "Medicine name : " + p.getMedicineName();
                    Toast.makeText(getContext(), "Click on " + toastText, Toast.LENGTH_SHORT).show();
                }
            });

            setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    Prescription p = db.get(object.getObjectId());
                    db.delete(object.getObjectId());
                    Toast.makeText(getContext(), "deleted " +p.getMedicineName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}

