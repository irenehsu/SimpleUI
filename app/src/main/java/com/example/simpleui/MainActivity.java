package com.example.simpleui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private EditText editText;//class名稱都是大寫開頭，變數名會以小寫開頭之後大寫
    private CheckBox hideCheckBox;
    private ListView historyListView;
    private Spinner storeInfoSpinner;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//super是父類別
        setContentView(R.layout.activity_main);
        storeInfoSpinner = (Spinner)findViewById(R.id.storeInfoSpinner);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);//private的
        editor = sharedPreferences.edit();//edit方法：可以增、修內容

        editText = (EditText)findViewById(R.id.editText_1);
        //editText.setText("YA!YA!");
        editText.setText(sharedPreferences.getString("editText", " "));
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            //按entr就直接submit
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        submit(v);
                        return true;//結束，把鍵盤退掉
                    }
                }
                return false;
            }
        });

        hideCheckBox = (CheckBox)findViewById((R.id.hideCheckBox));//findViewById->依ID尋找
                //hideCheckBox.setChecked(true);//true:預設為打勾; false:預設不打勾

        hideCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override //子類別複寫父類別的資料
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("hideCheckBox", isChecked);
                editor.commit();
            }
        });
        hideCheckBox.setChecked(sharedPreferences.getBoolean("hideCheckBox", false));

        historyListView = (ListView) findViewById(R.id.historyListView);
        setHistory();
        setStoreInfo();

    }

    private  void setStoreInfo() {
        String[] stores = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stores);
        storeInfoSpinner.setAdapter(storeAdapter);
    }
    //把array "data" 的內容顯示出來
    private void setHistory(){
        String[] data = Utils.readFile(this,"history.txt").split("\n");//spilt->把換行的每行資料依序讀到txt檔內無資料為止
        //String[] data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        //simple_list_item_1是靠左顯示的一行list

        historyListView.setAdapter(adapter);
    }

    //submit是在on click屬性設定的名字
    //view:判斷是哪一個ui元件會來執行這個foundtion，此例是submit
    public void submit(View view){

        String text = editText.getText().toString();
        editor.putString("inputText", text);//存內容到sharedPreference
        editor.commit();
        Utils.writeFile(this, "history.txt", text + "\n");//把submit的內容寫入history.txt檔
                //如果hide被打勾，就會顯示"********"
        if(hideCheckBox.isChecked()){
            text="*******";
            editText.setText("********");
        }
        //Toast.makeText(this,text, Toast.LENGTH_SHORT).show();//下方跳出的訊息會顯示較短時間, Toast：把text資料顯示出來
        //Toast.makeText(this,"HAHA!!",Toast.LENGTH_LONG).show();//下方跳出的訊息會顯示較長時間
        //String fileContent = Utils.readFile(this, "history.txt");//this=本class
        //Toast.makeText(this, fileContent, Toast.LENGTH_LONG).show();//用tost顯示讀出來的檔案, long的顯示

        setHistory();//直接由listview顯示，不用Toast顯示
    }
}
