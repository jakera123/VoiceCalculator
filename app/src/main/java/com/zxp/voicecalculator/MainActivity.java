package com.zxp.voicecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class MainActivity extends Activity {

//    private SoundPool soundPool;
    private Button btn_clear,btn_back,btn_isplay;
    private HashMap soundPoolMap;
    private GridView gv_key;
    private String TAG="MainActivity";
    private String[] position_voice,strings;
    private TextView tv_show;
    //点的标志位、退位键的标志
    private Boolean isSltDot=false,hasdot=false,isEquel=false,isNO1F=false;
    private final static int NumberLength=15;
    private int dot_legth;
    //加减乘除分别对应  1 2 3 4
    private int math_flag;
    private float numb1,numb2;
    private boolean isplay=true;


    private SoundPoolUtil soundPoolUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //一定要在加载布局之前,使用AppcomActivtiy实现不了

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        tv_show=(TextView)findViewById(R.id.tv_show);
        soundPoolUtil=new SoundPoolUtil(MainActivity.this);


        gv_key=(GridView)findViewById(R.id.gv_key);
        ArrayList<HashMap<String,String>> map=new ArrayList<HashMap<String, String>>();
        strings=new String[]         {"7","8", "9",  "+",
                                      "4","5","6",   "-",
                                      "1", "2","3",  "x",
                                      "0",".","=",   "/"};
        Log.i(TAG,strings[1]);
        for(String i:strings){
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put("Key",i);
            map.add(temp);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,map,R.layout.gv_modle,
                new String[]{"Key"},new int[]{R.id.tv_gv_item} );
        gv_key.setAdapter(adapter);
        gv_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解决按=号之后按数字产生的错误
                if(isEquel){
                    tv_show.setText("0");
                    isEquel=false;
                }
                String oldnum = tv_show.getText().toString();
                String sltnum = strings[position];

            if(oldnum.length()<NumberLength) {
               Log.i(TAG,"stlnum:"+sltnum+"+++++++++++++");
                soundPoolUtil.playSoundByChar(sltnum);
                 if (position == 13) {
                     if (!isSltDot) {
                         isSltDot = true;
                         dot_legth=oldnum.length();
                     }
                 }

                 if (position == 0 | position == 1 | position == 2 | position == 4 |
                         position == 5 | position == 6 | position == 8 | position == 9 |
                         position == 10 | position == 12) {
                     if (isSltDot) {
                         if (!hasdot) {
                                 tv_show.setText(oldnum + "." + sltnum);
                                 hasdot = true;
                         } else {
                             tv_show.setText(oldnum + sltnum);
                         }
                     } else {
                         Integer result = Integer.parseInt(oldnum) * 10 + parseInt(sltnum);
                         tv_show.setText(result + "");
                     }
                 }


                 if(position==3|position==7|position==11|position==15){
                     numb1=Float.parseFloat(tv_show.getText().toString());
                     if(hasdot){
                         isNO1F=true;
                     }
                     tv_show.setText("0");
                     if(hasdot){
                         isNO1F=true;
                     }
                     hasdot=false;
                     isSltDot=false;
                     switch (position){
                         case 3:  math_flag=1; break;
                         case 7:  math_flag=2; break;
                         case 11: math_flag=3; break;
                         case 15: math_flag=4; break;
                     }
                 }

                 if(position==14){
                     isEquel=true;
                         numb2 = Float.parseFloat(tv_show.getText().toString());
                         float result = 0.0f;
                         if (math_flag != 0) {
                             switch (math_flag) {
                                 case 1:
                                     result = numb1 + numb2;
                                     break;
                                 case 2:
                                     result = numb1 - numb2;
                                     break;
                                 case 3:
                                     result = numb1 * numb2;
                                     break;
                                 case 4:
                                     result = numb1 / numb2;
                                     break;
                             }

                             math_flag = 0;
                            //判断显示类型

                             Log.i(TAG,"isno1f"+isNO1F+"&&&&&&&"+"hasdot"+hasdot);
                             if(isNO1F|hasdot) {
                                 tv_show.setText(result + "");
                                 try {
                                     Thread.sleep(500);
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                                 soundPoolUtil.PlayByNumber(result+"");
                                 isNO1F=false;
                             }
                             else {
                                 //类型转换
                                 Float f=new Float(result);
                                 Integer a=f.intValue();
                                 int[] temp = new int[NumberLength];
                                 tv_show.setText(a+"");
                                 try {
                                     Thread.sleep(500);
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                                 soundPoolUtil.PlayByNumber(a);
                             }
                         }
                 }
             }

            }
        });


        btn_clear=(Button)findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPoolUtil.playSoundByName("ac");
                tv_show.setText("0");
                hasdot=false;
                isSltDot=false;
                isNO1F=false;
            }
        });

        btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPoolUtil.playSoundByName("del");
                String num=tv_show.getText().toString();

              //  Toast.makeText(MainActivity.this,num.charAt(num.length()-1)+"",Toast.LENGTH_SHORT).show();

                Log.i(TAG,num.length()+"%%%%%%"+dot_legth+"numstring"+num);

                if(num.length()==dot_legth+1){
                    hasdot=false;
                    isSltDot=false;
                }
             // 这里退去了最前的一位，不可用！
             //   num=num.substring(1);
                num=num.substring(0,num.length()-1);
                tv_show.setText(num+"");
                //Integer inum=Integer.parseInt(num);
                //inum=inum/10;
                //tv_show.setText(inum+"");

                if(num.length()==0){
                    tv_show.setText("0");
                }
            }
        });




        btn_isplay=(Button)findViewById(R.id.btn_isplay);
        btn_isplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isplay=!isplay;
               soundPoolUtil.setPlay(isplay);
            }
        });


    }
}
