package com.example.souvik.prescriptionformnew;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.souvik.prescriptionformnew.DB.DatabaseHandler;
import com.example.souvik.prescriptionformnew.DB.Prescription;
import com.example.souvik.prescriptionformnew.Http.HttpPost;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainActivity extends AppCompatActivity {

    MedicineIndividualCard medicineIndividualCard;
    CardViewNative medicineIndividualCardView;
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    NestedScrollView nestedScrollView;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressDialog pDialog;

    MaterialEditText patientDiagnosisEditText;
    DatabaseHandler db;
    String patientName=null;
    public static String patientDiagnosis =null;
    public static int scrollX = 0;
    public static int scrollY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setSupportActionBar(toolbar);
        initMedicineCard();
        collapsingToolbarLayout.setTitle(getString(R.string.patient_name));
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutExpandedTextStyle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

        if(floatingActionButton!=null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MedicineAddActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(MainActivity.this, floatingActionButton, "shared_fab");
                        startActivity(intent, options.toBundle());
                        patientDiagnosis = patientDiagnosisEditText.getText().toString();
                    }
                    else {
                        startActivity(intent);
                        patientDiagnosis = patientDiagnosisEditText.getText().toString();
                    }
                }
            });
        }

        floatingActionButton.setImageResource(R.drawable.add_med_white);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.LightBlue)));
        floatingActionButton.setRippleColor(getResources().getColor(R.color.pressed_lightblue));

    }

    private void initialization() {
        db = new DatabaseHandler(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nested_scroll_view);
        coordinatorLayout =(CoordinatorLayout)findViewById(R.id.main_content);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        patientDiagnosisEditText = (MaterialEditText)findViewById(R.id.patient_diagnosis_edit_text);
        if(patientDiagnosis !=null){patientDiagnosisEditText.setText(patientDiagnosis);}
    }


    public void onDiagnosisSkip_or_Done(View view){
        programmaticScroll(floatingActionButton);
    }
    public void programmaticScroll(View view) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, 10000, true);
        }
//        nestedScrollView.smoothScrollTo(0,view.getBottom());
        ObjectAnimator.ofInt(nestedScrollView, "scrollY", view.getBottom()).setDuration(1000).start();
    }
    private void initMedicineCard() {
        medicineIndividualCard = new MedicineIndividualCard(this);
        medicineIndividualCard.init();
        medicineIndividualCardView = (CardViewNative)findViewById(R.id.medicine_all_card);
        medicineIndividualCardView.setCard(medicineIndividualCard);
    }

    @Override protected void onResume() {
        super.onResume();
        Log.d("I am here", "hehe");
        medicineIndividualCard.updateItems();
     }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.action_done) {

            JSONObject medicineObjectArray = new JSONObject();

            List<Prescription> medicineList = db.get();

            for (Prescription p : medicineList) {

                JSONObject medicineObject = new JSONObject();
                try {
                    medicineObject.put("Morning", p.getMorning());
                    medicineObject.put("Afternoon", p.getAfternoon());
                    medicineObject.put("Evening", p.getEvening());
                    medicineObject.put("Night", p.getNight());
                    medicineObject.put("Total_Quantity", p.getTotalQuantity());
                    medicineObjectArray.put(p.getMedicineName(), medicineObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            JSONObject prescription = new JSONObject();
            try {
                prescription.put("Patient_Name","patientName");
                prescription.put("Doctor_Name","Random Doctor");
                prescription.put("Diagnosis",patientDiagnosis);
                prescription.put("Medicines", medicineObjectArray);
                prescription.put("Signature", "Default");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new CreatePrescription().execute(prescription);
        }
        if (menuItem.getItemId() == R.id.action_new){
            db.delete();
            recreate();
            patientDiagnosisEditText.setText("");
//            patientNameEditText.setText("");

        }


        return super.onOptionsItemSelected(menuItem);
    }



    class CreatePrescription extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Creating Prescription...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(JSONObject... params) {

            JSONObject jsonObjRecv;
            JSONObject jsonObjectSend = params[0];
            Log.d("jsonObjSend : ", jsonObjectSend.toString());
            jsonObjRecv = HttpPost.SendHttpPost("http://104.131.46.2:5000/Prescription/api/v1.0/prescription/1", jsonObjectSend);

            if(jsonObjRecv != null) {
                Log.d("Received Json : ", jsonObjRecv.toString());
            }else{
                Log.d("Received Json : ", "failed");
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            pDialog.dismiss();
        }
    }




}