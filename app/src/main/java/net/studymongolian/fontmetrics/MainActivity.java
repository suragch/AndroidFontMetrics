package net.studymongolian.fontmetrics;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FontMetricsView myFontMetricsView; // custom view
    EditText mTextStringEditText;
    EditText mFontSizeEditText;
    CheckBox cbTop;
    CheckBox cbAscent;
    CheckBox cbBaseline;
    CheckBox cbDescent;
    CheckBox cbBottom;
    CheckBox cbBounds;
    CheckBox cbMeasuredWidth;

    TextView tvTop;
    TextView tvAscent;
    TextView tvBaseline;
    TextView tvDescent;
    TextView tvBottom;
    TextView tvBounds;
    TextView tvMeasuredWidth;
    TextView tvLeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFontMetricsView = (FontMetricsView) findViewById(R.id.viewWindow);
        mTextStringEditText = (EditText) findViewById(R.id.etTextString);
        mFontSizeEditText = (EditText) findViewById(R.id.etFontSize);

        mTextStringEditText.setText("My text line");
        mFontSizeEditText.setText("200");


        findViewById(R.id.updateButton).setOnClickListener(this);
        cbTop = (CheckBox) findViewById(R.id.cbTop);
        cbAscent = (CheckBox) findViewById(R.id.cbAscent);
        cbBaseline = (CheckBox) findViewById(R.id.cbBaseline);
        cbDescent = (CheckBox) findViewById(R.id.cbDescent);
        cbBottom = (CheckBox) findViewById(R.id.cbBottom);
        cbBounds = (CheckBox) findViewById(R.id.cbTextBounds);
        cbMeasuredWidth = (CheckBox) findViewById(R.id.cbWidth);

        cbTop.setOnClickListener(this);
        cbAscent.setOnClickListener(this);
        cbBaseline.setOnClickListener(this);
        cbDescent.setOnClickListener(this);
        cbBottom.setOnClickListener(this);
        cbBounds.setOnClickListener(this);
        cbMeasuredWidth.setOnClickListener(this);

        tvTop = (TextView) findViewById(R.id.tvTop);
        tvAscent = (TextView) findViewById(R.id.tvAscent);
        tvBaseline = (TextView) findViewById(R.id.tvBaseline);
        tvDescent = (TextView) findViewById(R.id.tvDescent);
        tvBottom = (TextView) findViewById(R.id.tvBottom);
        tvBounds = (TextView) findViewById(R.id.tvTextBounds);
        tvMeasuredWidth = (TextView) findViewById(R.id.tvWidth);
        tvLeading = (TextView) findViewById(R.id.tvLeadingValue);
        updateTextViews();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.updateButton:
                myFontMetricsView.setText(mTextStringEditText.getText().toString());
                int fontSize;
                try {
                    fontSize = Integer.valueOf(mFontSizeEditText.getText().toString());
                }catch (NumberFormatException e) {
                    fontSize = FontMetricsView.DEFAULT_FONT_SIZE_PX;
                }
                myFontMetricsView.setTextSizeInPixels(fontSize);
                updateTextViews();
                hideKeyboard(getCurrentFocus());
                break;
            case R.id.cbTop:
                myFontMetricsView.setTopVisible(cbTop.isChecked());
                break;
            case R.id.cbAscent:
                myFontMetricsView.setAscentVisible(cbAscent.isChecked());
                break;
            case R.id.cbBaseline:
                myFontMetricsView.setBaselineVisible(cbBaseline.isChecked());
                break;
            case R.id.cbDescent:
                myFontMetricsView.setDescentVisible(cbDescent.isChecked());
                break;
            case R.id.cbBottom:
                myFontMetricsView.setBottomVisible(cbBottom.isChecked());
                break;
            case R.id.cbTextBounds:
                myFontMetricsView.setBoundsVisible(cbBounds.isChecked());
                break;
            case R.id.cbWidth:
                myFontMetricsView.setWidthVisible(cbMeasuredWidth.isChecked());
                break;
        }


    }

    public void updateTextViews() {
        tvTop.setText(String.valueOf(myFontMetricsView.getFontMetrics().top));
        tvAscent.setText(String.valueOf(myFontMetricsView.getFontMetrics().ascent));
        tvBaseline.setText(String.valueOf(0f));
        tvDescent.setText(String.valueOf(myFontMetricsView.getFontMetrics().descent));
        tvBottom.setText(String.valueOf(myFontMetricsView.getFontMetrics().bottom));
        tvBounds.setText("w = " + String.valueOf(myFontMetricsView.getTextBounds().width() +
                "     h = " + String.valueOf(myFontMetricsView.getTextBounds().height())));
        tvMeasuredWidth.setText(String.valueOf(myFontMetricsView.getMeasuredTextWidth()));
        tvLeading.setText(String.valueOf(myFontMetricsView.getFontMetrics().leading));
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
