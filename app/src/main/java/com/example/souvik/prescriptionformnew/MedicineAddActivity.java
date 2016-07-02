package com.example.souvik.prescriptionformnew;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.example.souvik.prescriptionformnew.DB.DatabaseHandler;
import com.example.souvik.prescriptionformnew.DB.Prescription;
import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.kyleduo.switchbutton.SwitchButton;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class MedicineAddActivity extends AppCompatActivity implements NumberPickerDialogFragment.NumberPickerDialogHandlerV2{

    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    NestedScrollView nestedScrollView;
    CardView medicationScheduleCard;
    CardView medicationQuantityCard;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;

    AutoCompleteTextView medicineName;

    TextView isMorning ;
    SwitchButton isMorningAfter;
    SwitchButton isMorningBefore;

    TextView isAfternoon;
    SwitchButton isAfternoonAfter;
    SwitchButton isAfternoonBefore;

    TextView isEvening;
    SwitchButton isEveningAfter;
    SwitchButton isEveningBefore;

    TextView isNight;
    SwitchButton isNightAfter;
    SwitchButton isNightBefore;

    SwitchButton buttonClicked = null;
    SwitchButton buttonToCheck = null;
    TextView textView = null;
    String text = null;
    View viewHideOrShow;

    TableRow morningLinearLayout;
    TableRow afternoonLinearLayout;
    TableRow eveningLinearLayout;
    TableRow nightLinearLayout;
    TableRow daysLinearLayout;

    HorizontalNumberPicker morningNumberPicker;
    HorizontalNumberPicker  afternoonNumberPicker;
    HorizontalNumberPicker  eveningNumberPicker;
    HorizontalNumberPicker  nightNumberPicker;
    HorizontalNumberPicker  daysNumberPicker;

    TextView numOfDaysTv;
    DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_add_activity_layout);
        initialization();
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.patient_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));
        floatingActionButton.setImageResource(R.drawable.check);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.LightBlue)));
        floatingActionButton.setRippleColor(getResources().getColor(R.color.pressed_lightblue));
        numOfDaysTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerBuilder npb = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                npb.show();
            }
        });
        setSwitchButtonListeners();
        numberPickerInit();
        if(floatingActionButton!=null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setDB();
                    onBackPressed();
                }
            });
        }
    }

    private void numberPickerInit() {
        ArrayList<HorizontalNumberPicker > numberPickers= new ArrayList< >();
        numberPickers.add(morningNumberPicker);
        numberPickers.add(afternoonNumberPicker);
        numberPickers.add(eveningNumberPicker);
        numberPickers.add(nightNumberPicker);
        numberPickers.add(daysNumberPicker);

        for (final HorizontalNumberPicker  horizontalNumberPicker : numberPickers){
            if(horizontalNumberPicker == daysNumberPicker){
                horizontalNumberPicker.setValue(7);
            }else {
                horizontalNumberPicker.setValue(1);
            }
            horizontalNumberPicker.setMinValue(1);
            if(horizontalNumberPicker == daysNumberPicker){
                horizontalNumberPicker.setMaxValue(100);
            }else {
                horizontalNumberPicker.setMaxValue(10);
            }
            horizontalNumberPicker.getButtonMinusView().setText("<");
            horizontalNumberPicker.getButtonPlusView().setText(">");
            horizontalNumberPicker.getButtonMinusView().setTypeface(null, Typeface.BOLD);
            horizontalNumberPicker.getButtonMinusView().setTypeface(null, Typeface.BOLD);
            horizontalNumberPicker.getTextValueView().setTypeface(null, Typeface.BOLD);
            horizontalNumberPicker.getTextValueView().setTextColor(Color.parseColor("#0E5AA4"));
        }
    }

    private void setSwitchButtonListeners() {
        ArrayList<SwitchButton> listButtons= new ArrayList<SwitchButton>();
        listButtons.add(isMorningAfter);
        listButtons.add(isMorningBefore);
        listButtons.add(isAfternoonAfter);
        listButtons.add(isAfternoonBefore);
        listButtons.add(isEveningAfter);
        listButtons.add(isEveningBefore);
        listButtons.add(isNightAfter);
        listButtons.add(isNightBefore);

        for(SwitchButton button : listButtons){

            int id = button.getId();
            Log.d("This is the id :", id + "");



            switch (id) {

                case R.id.isMorningAfter:
                    buttonClicked = isMorningAfter;
                    buttonToCheck = isMorningBefore;
                    textView = isMorning;
                    text = "Morning";
                    viewHideOrShow =morningLinearLayout;
                    break;
                case R.id.isMorningBefore:
                    buttonClicked = isMorningBefore;
                    buttonToCheck = isMorningAfter;
                    textView = isMorning;
                    text = "Morning";
                    viewHideOrShow =morningLinearLayout;
                    break;

                case R.id.isAfternoonAfter:
                    buttonClicked =isAfternoonAfter;
                    buttonToCheck = isAfternoonBefore;
                    textView = isAfternoon;
                    text = "Afternoon";
                    viewHideOrShow =afternoonLinearLayout;
                    break;
                case R.id.isAfternoonBefore:
                    buttonClicked = isAfternoonBefore;
                    buttonToCheck = isAfternoonAfter;
                    textView = isAfternoon;
                    text = "Afternoon";
                    viewHideOrShow =afternoonLinearLayout;
                    break;

                case R.id.isEveningAfter:
                    buttonClicked = isEveningAfter;
                    buttonToCheck = isEveningBefore;
                    textView = isEvening;
                    text = "Evening";
                    viewHideOrShow = eveningLinearLayout;
                    break;
                case R.id.isEveningBefore:
                    buttonClicked = isEveningBefore;
                    buttonToCheck = isEveningAfter;
                    textView = isEvening;
                    text = "Evening";
                    viewHideOrShow = eveningLinearLayout;
                    break;

                case R.id.isNightAfter:
                    buttonClicked = isNightAfter;
                    buttonToCheck = isNightBefore;
                    textView = isNight;
                    text = "Night";
                    viewHideOrShow =nightLinearLayout;
                    break;
                case R.id.isNightBefore:
                    buttonClicked = isNightBefore;
                    buttonToCheck = isNightAfter;
                    textView = isNight;
                    text = "Night";
                    viewHideOrShow =nightLinearLayout;
                    break;

            }

            final  SwitchButton buttonClickedf= buttonClicked;
            final SwitchButton buttonToCheckf = buttonToCheck;
            final TextView textViewf = textView;
            final String textf = text;
            final View viewHideOrShowf =viewHideOrShow;


            buttonClickedf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if ( !buttonToCheckf.isChecked()) {
                        buttonClickedf.setChecked(buttonClickedf.isChecked());
                        if(viewHideOrShowf.getVisibility()==View.GONE) {
                            viewHideOrShowf.setVisibility(View.VISIBLE);
                        }else{
                            viewHideOrShowf.setVisibility(View.GONE);
                        }
                        Log.d("here", "one");
                    }else if (buttonToCheckf.isChecked()) {
                        Toast.makeText(MedicineAddActivity.this, "You can't select both before and after", Toast.LENGTH_SHORT).show();
                        buttonClickedf.setChecked(false);
                        Log.d("here", "two");
                    }else{
                        Log.d("here","four");
                    }

                    if(morningLinearLayout.getVisibility() == View.VISIBLE ||
                            afternoonLinearLayout.getVisibility() == View.VISIBLE ||
                            eveningLinearLayout.getVisibility() == View.VISIBLE ||
                            nightLinearLayout.getVisibility() == View.VISIBLE){
                        daysLinearLayout.setVisibility(View.VISIBLE);
                    }else{
                        daysLinearLayout.setVisibility(View.GONE);
                    }
                }
            });



        }
    }


    private void setDB() {
        Prescription prescription = new Prescription();

        if(medicineName!= null && medicineName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fill medicine name", Toast.LENGTH_SHORT).show();
        }else{
            prescription.setMedicineName(medicineName.getText().toString());
        }

        prescription.setNumOfDays(daysNumberPicker.getValue());

        if (isMorning.getCurrentTextColor() == Color.parseColor("#0E5AA4")) {
            prescription.setIsMorning(1);
            prescription.setQuantity(morningNumberPicker.getValue(), Prescription.MORNING_QUANTITY);
            Log.d("mquanity",morningNumberPicker.getValue()+"");
            if(isMorningAfter.isChecked()){
                prescription.setIsMorningAfter(1);
            }else if(isMorningBefore.isChecked()) {
                prescription.setIsMorningBefore(1);
            }
        }

        if (isAfternoon.getCurrentTextColor() == Color.parseColor("#0E5AA4")) {
            prescription.setIsAfternoon(1);
            prescription.setQuantity(afternoonNumberPicker.getValue(), Prescription.AFTERNOON_QUANTITY);
            Log.d("afternoonNumberPicker", afternoonNumberPicker.getValue() + "");
            if(isAfternoonAfter.isChecked()){
                prescription.setIsAfternoonAfter(1);
            }else if(isAfternoonBefore.isChecked()) {
                prescription.setIsAfternoonBefore(1);
            }
        }

        if (isEvening.getCurrentTextColor() == Color.parseColor("#0E5AA4")) {
            prescription.setIsEvening(1);
            prescription.setQuantity(eveningNumberPicker.getValue(), Prescription.EVENING_QUANTITY);
            Log.d("eveningNumberPicker", eveningNumberPicker.getValue() + "");
            if(isEveningAfter.isChecked()){
                prescription.setIsEveningAfter(1);
            }else if(isEveningBefore.isChecked()) {
                prescription.setIsEvening(1);
                prescription.setIsEveningBefore(1);
            }
        }

        if (isNight.getCurrentTextColor() == Color.parseColor("#0E5AA4")) {
            prescription.setIsNight(1);
            prescription.setQuantity(nightNumberPicker.getValue(), Prescription.NIGHT_QUANTITY);
            Log.d("nightNumberPicker", nightNumberPicker.getValue() + "");
            if(isNightAfter.isChecked()){
                prescription.setIsNightAfter(1);
            }else if(isNightBefore.isChecked()) {
                prescription.setIsNightBefore(1);
            }
        }

        db.add(prescription);
    }

    private void initialization() {
        db = new DatabaseHandler(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout =(CoordinatorLayout)findViewById(R.id.main_content);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nested_scroll_view);
        medicationScheduleCard = (CardView)findViewById(R.id.medication_schedule);
        medicationQuantityCard = (CardView)findViewById(R.id.medication_quantity);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        medicineName = (AutoCompleteTextView)findViewById(R.id.medicine_name_edit_text);

        isMorning = (TextView) findViewById(R.id.isMorning);
        isAfternoon = (TextView) findViewById(R.id.isAfternoon);
        isEvening = (TextView) findViewById(R.id.isEvening);
        isNight = (TextView) findViewById(R.id.isNight);

        isMorningAfter = (SwitchButton)findViewById(R.id.isMorningAfter);
        isMorningBefore = (SwitchButton)findViewById(R.id.isMorningBefore);

        isAfternoonAfter = (SwitchButton)findViewById(R.id.isAfternoonAfter);
        isAfternoonBefore = (SwitchButton)findViewById(R.id.isAfternoonBefore);

        isEveningAfter = (SwitchButton)findViewById(R.id.isEveningAfter);
        isEveningBefore = (SwitchButton)findViewById(R.id.isEveningBefore);

        isNightAfter = (SwitchButton)findViewById(R.id.isNightAfter);
        isNightBefore = (SwitchButton)findViewById(R.id.isNightBefore);

        morningLinearLayout = (TableRow)findViewById(R.id.morning_table_row);
        afternoonLinearLayout = (TableRow)findViewById(R.id.afternoon_table_row);
        eveningLinearLayout = (TableRow)findViewById(R.id.evening_table_row);
        nightLinearLayout = (TableRow)findViewById(R.id.night_table_row);
        daysLinearLayout = (TableRow)findViewById(R.id.days_table_row);

        morningNumberPicker = (HorizontalNumberPicker )findViewById(R.id.morning_np);
        afternoonNumberPicker = (HorizontalNumberPicker )findViewById(R.id.afternoon_np);
        eveningNumberPicker = (HorizontalNumberPicker )findViewById(R.id.evening_np);
        nightNumberPicker = (HorizontalNumberPicker )findViewById(R.id.night_np);
        daysNumberPicker = (HorizontalNumberPicker )findViewById(R.id.days_np);

        numOfDaysTv= (TextView)findViewById(R.id.num_of_days_tv);
    }


    public void onMedicationNameSkip_or_Done(View view){
        programmaticScroll(medicationScheduleCard, 0);
    }
    public void onMedicationScheduleSkip_or_Done(View view){
        programmaticScroll(medicationQuantityCard, 1);
    }
    public void programmaticScroll(View view, int from) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, 10000, true);
        }
        if(from == 0) {
//            nestedScrollView.smoothScrollTo(0, view.getBottom() / 3);
            ObjectAnimator.ofInt(nestedScrollView, "scrollY", view.getBottom()/3).setDuration(1000).start();
        }else if(from ==1){
//            nestedScrollView.smoothScrollTo(0, view.getBottom());
            ObjectAnimator.ofInt(nestedScrollView, "scrollY", view.getBottom()).setDuration(1000).start();
        }
    }


    @Override
    public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
        Log.d("here","ahahahah"+"  "+number.intValue());
        daysNumberPicker.setValue(number.intValue());
    }

}