package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MakeAdvertisementActivity extends AppCompatActivity {



    private Button mAdButton;
    private Button mAddQuestionButton;

    private EditText mTitle;
    private RangeSlider mSalary ; // what kind of widget to use
    private EditText mDescription;
    private ArrayList<String> questions = new ArrayList<>();
    private ListView mQuestionsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_advertisement);
      //  makeList();

        // adding questions to list view
        mQuestionsView = findViewById(R.id.extraQuestionsView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MakeAdvertisementActivity.
                        this, android.R.layout.simple_list_item_1, questions);
        mQuestionsView.setAdapter(arrayAdapter);

        // adding item to questions
        mAddQuestionButton = (Button) findViewById(R.id.addQuestion_button);
        mAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText text = findViewById(R.id.addQuestion);
                String question = text.getText().toString();

                if(questions.contains(question)) {
                    Toast.makeText(getBaseContext(), "Item Already Added to the Array", Toast.LENGTH_LONG).show();
                }

                else if (question == null || question.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Input Fields is Empty", Toast.LENGTH_LONG).show();
                }
                else {
                    questions.add(question);
                    mQuestionsView.setAdapter(arrayAdapter);
                    ((EditText) findViewById(R.id.addQuestion)).setText("");
                }
            }
        });

        // changing items
        mQuestionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                questions.remove(i);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        // register ad
        mAdButton = findViewById(R.id.registerAd_button);
        mAdButton.setOnClickListener(view -> {
            mTitle = findViewById(R.id.title_entry);
            mSalary = findViewById(R.id.priceRange_entry);
            mDescription = findViewById(R.id.description_entry);

            getInfoFromForm();
            /// where do we want to go
        });



    }

    private void getInfoFromForm() {
        String title = mTitle.getText().toString();
        // mSalary
        String description = mDescription.getText().toString();

        // put into backend
    }


    private void makeList()  {
        questions.add("question 1");
        questions.add("question 2");

    }
    private void printlist() {

    }
}