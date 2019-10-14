package com.hwhhhh.wordbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        getSupportActionBar().hide();
        //底部导航栏
        BottomNavigationView navView = findViewById(R.id.nav_bottom_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_notebook, R.id.navigation_user
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


//        final EditText editText = findViewById(R.id.edit_text);
//        Button button_search = findViewById(R.id.button_search);
////        final TextView textView = findViewById(R.id.text_view);
//        final WordDao wordDao = WordDao.getInstance();
//        button_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = wordDao.getUrl(editText.getText().toString());
//                HttpUtil.setHttpRequest(url, new HttpCallBackListener() {
//                    @Override
//                    public void onFinish(InputStream inputStream) {
//                        WordHandler wordHandler = new WordHandler();
//                        ParseXML.parse(wordHandler, inputStream);
//                        WordDto wordDto = wordHandler.getWordDto();
//                        wordDao.saveWord(wordDto);
////                        textView.setText(wordDto.toString());
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//
//                    }
//                });
//            }
//        });
//        Button button_delete = findViewById(R.id.delete_table);
//        button_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LitePal.deleteAll(WordInfo.class);
//                LitePal.deleteAll(WordORIG.class);
//                LitePal.deleteAll(WordPartOfSpeech.class);
//                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_LONG).show();
//            }
//        });
    }


}
