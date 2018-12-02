package view.edit.input.englishphonetictobangla;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Mimo on 12/3/2018.
 */
public class Translator {
    Context mContext;
    EditText mEditText;
    TextView mLiveText;
    LinearLayout mTextLanguage;
    Button mDemo;
    Button mDemoOne;
    Button mDemoTwo;
    Button mDemoThree;
    Button mDemoFour;
    int mPosition = 0;
    int mQuicktypingflag = 0;
    Handler mHan;
    Runnable mRun ;
    int flag = 0;
    String text = "";

    String[] values = new String[6];
    ArrayList<String> val = new ArrayList<String>();

    public Translator(Context context, EditText editText, TextView liveText,
                      LinearLayout textLanguage, Button demo,
                      Button demoOne, Button demoTwo, Button demoThree, Button demoFour){
        this.mContext = context;
        this.mEditText = editText;
        this.mLiveText = liveText;
        this.mTextLanguage = textLanguage;
        this.mDemo = demo;
        this.mDemoOne = demoOne;
        this.mDemoTwo = demoTwo;
        this.mDemoThree = demoThree;
        this.mDemoFour = demoFour;

    }

    public void switchKeyboard( ){


        mDemo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String text = mEditText.getText().toString();
                mEditText.setText(/* values[0]+" " */text.replaceAll(mLiveText
                        .getText().toString(), mDemo.getText().toString() + " "));
                mEditText.setSelection(mEditText.getText().toString().length());
                mPosition = mEditText.getText().toString().length();
            }
        });

        mDemoOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String text = mEditText.getText().toString();
                mEditText.setText(/* values[0]+" " */text.replaceAll(mLiveText
                        .getText().toString(), mDemoOne.getText().toString() + " "));
                mEditText.setSelection(mEditText.getText().toString().length());
                mPosition = mEditText.getText().toString().length();
            }
        });

        mDemoTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String text = mEditText.getText().toString();
                mEditText.setText(/* values[0]+" " */text.replaceAll(mLiveText
                        .getText().toString(), mDemoTwo.getText().toString() + " "));
                mEditText.setSelection(mEditText.getText().toString().length());
                mPosition = mEditText.getText().toString().length();
            }
        });

        mDemoThree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String text = mEditText.getText().toString();
                mEditText.setText(/* values[0]+" " */text.replaceAll(mLiveText
                        .getText().toString(), mDemoThree.getText().toString() + " "));
                mEditText.setSelection(mEditText.getText().toString().length());
                mPosition = mEditText.getText().toString().length();
            }
        });

        mDemoFour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String text = mEditText.getText().toString();
                mEditText.setText(/* values[0]+" " */text.replaceAll(mLiveText
                        .getText().toString(), mDemoFour.getText().toString() + " "));
                mEditText.setSelection(mEditText.getText().toString().length());
                mPosition = mEditText.getText().toString().length();
            }
        });

        // Sudip added
        mHan = new Handler();
        mRun = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                if (mPosition > mEditText.getText().toString().length()) {
                    mPosition = mEditText.getText().toString().length();
                } else {
                    String s = mEditText.getText().toString().substring(mPosition);
                    if (!s.equals(" ") && !s.equals("?") && !s.equals("!")) {

                        final String txtquery = mLiveText.getText().toString();
                        /*
                         * ed.setText(ed.getText().toString() .substring(0,
                         * position));
                         */
                        if (!txtquery.equals("") && !txtquery.equals(" ")) {

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    values = callGoogleTranslate("en", "bn",
                                            txtquery);
                                    val.clear();
                                    if (values != null) {

                                        for (int j = 0; j < values.length; j++) {
                                            if (values[j] != null)
                                                val.add(values[j]);
                                        }

                                        ((Activity)mContext).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (val.size() > 0) {
                                                    mDemo.setText(val.get(0));
                                                }

                                                if (val.size() > 1) {
                                                    mDemoOne.setText(val.get(1));
                                                }

                                                if (val.size() > 2) {
                                                    mDemoTwo.setText(val.get(2));
                                                }

                                                if (val.size() > 3) {
                                                    mDemoThree.setText(val.get(3));
                                                }

                                                if (val.size() > 4) {
                                                    mDemoFour.setText(val.get(4));
                                                }
                                            }
                                        });

                                    }
                                }
                            });
                            thread.start();

                        }

                    } else {
                        mPosition = mEditText.getText().toString().length();
                    }
                }

            }
        };

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                if(s.toString().equals(" ")){
                    ProcessTextAndShowTranslatedResult();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

                ProcessTextAndShowTranslatedResult();
            }
        });
    }

    public String[] callGoogleTranslate(String fromLanguage, String toLanguage,
                                        String textToTranslate) {
        // String yourKey = "AIzaSyB4uyhW_DNIAVC-VhE1sghBnSZhzp7iSK8";
        String results[] = null;
        /*
         * String URL = "https://www.googleapis.com/language/translate/v2";
         * String key = "?key=" + yourKey; String sourceParam = "&source=" +
         * fromLanguage; String toParam = "&target=" + toLanguage; String
         * textParam = "&q=" + textToTranslate.replaceAll(" ", "%20");
         */
        String fullURL = "";
        try {
            fullURL = "https://inputtools.google.com/request?text="
                    + URLEncoder.encode(textToTranslate, "UTF-8")
                    + "&itc=bn-t-i0-und&num=13&cp=0&cs=0&ie=utf-8&oe=utf-8&app=demopage";
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        HttpResponse resp;
        try {
            HttpGet del = new HttpGet(fullURL);
            HttpClient httpClient = new DefaultHttpClient();
            resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            // Parse Result http
            results = proccesResult(respStr, textToTranslate);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    private void ProcessTextAndShowTranslatedResult(){
        boolean isBangla = true;
        String banglaflag;
        if (isBangla)
            banglaflag = "on";
        else
            banglaflag = "off";

        if (String.valueOf(banglaflag).equals("on")) {

            if(String.valueOf(banglaflag).equals("on")) {
                mTextLanguage.setVisibility(View.VISIBLE);
            }else {
                mTextLanguage.setVisibility(View.GONE);
            }

            if (mQuicktypingflag == 1) {
                mQuicktypingflag = 0;
            } else {
                text = mEditText.getText().toString();
                mLiveText.setText("");
                if (mPosition <= text.length()) {
                    mLiveText.setText(text.substring(mPosition,
                            text.length()).trim());
                } else {
                    mLiveText.setText("");
                    mPosition = text.length();
                }

                if (text.length() > 0) {
                    String vall = text.substring(text.length() - 1);

                    if (vall.equals(" ")) {

                        mDemo.setText("");
                        mDemoOne.setText("");
                        mDemoTwo.setText("");
                        mDemoThree.setText("");
                        mDemoFour.setText("");

                        mHan.removeCallbacks(mRun);

                        if (mPosition > text.length())
                            mPosition = text.length();

                        //
                        if (flag != 1) {

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final String txtquery = text.substring(mPosition,
                                            text.length()).trim();
                                    values = callGoogleTranslate(
                                            "en", "bn", txtquery);
                                    if (values != null) {
                                        if (values[0] != null) {

                                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mQuicktypingflag = 1;
                                                    mEditText.setText(text.replaceAll(txtquery,
                                                            values[0] + " "));
                                                    mEditText.setSelection(mEditText.getText()
                                                            .toString().length());
                                                    mPosition = mEditText.getText().toString()
                                                            .length();
//                                                    Toast.makeText(PrivateMessageActivity.this,
//                                                            String.valueOf(txtquery),
//                                                            Toast.LENGTH_LONG).show();

                                                }
                                            });

                                        }
                                    }
                                }
                            });

                            thread.start();


                        }


                    } else if (flag == 0) {
                        // TODO Auto-generated method stub
                        mHan.removeCallbacks(mRun);
                        mHan.postDelayed(mRun, 700);

                    }

                } else {
                    mTextLanguage.setVisibility(View.GONE);
                }

            }

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    String[] proccesResult(String jsonStringData, String englishtext) {
        String result[] = new String[6];
        try {
            JSONArray suc = new JSONArray(jsonStringData);
            JSONArray sss = suc.getJSONArray(1);
            JSONArray yyy = sss.getJSONArray(0);
            JSONArray zzz = yyy.getJSONArray(1);

            for (int i = 0; i < 5; i++) {
                if (zzz.length() > i){
                    result[i] = zzz.getString(i);

                    //    UserDictionary.Words.addWord(PrivateMessageActivity.this, String.valueOf(result[i]),1,"",Locale.getDefault());
                }

            }
            result[result.length - 1] = englishtext;

            // TRANSLATE TEXT
            // Log.i("Prueba", "Resultado-> " + txtTraducido);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void ShoworHideKeyboardAutoSuggestion(){
        boolean isBangla = true;
        String banglaFlag;
        if (isBangla)
            banglaFlag = "on";
        else
            banglaFlag = "off";

        if(String.valueOf(banglaFlag).equals("on")){
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else{
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    callGoogleTranslate("en", "bn", "Hello there"); // dummy request
                }
            });
            t.start();
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }
}