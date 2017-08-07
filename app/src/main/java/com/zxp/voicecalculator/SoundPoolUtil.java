package com.zxp.voicecalculator;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import static com.zxp.voicecalculator.R.raw.zero;

/**
 * Created by xiaoxin on 2017/8/2.
 */

public class SoundPoolUtil {
    private SoundPool soundPool;
    private final static int SoundmaxStreams=5;
    private final static int SoundsrcQuality=5;
    private int soundPosition=0;
    private String[] Name2Sound;
    private final static String TAG="SoundPoolUtil";
    private final static int NumberLength=12;
    private final static int DelayToSound=400;
    private boolean isPlay=true;
    private final static String[] position_voice=new String[]{"null","ac","del","/",
                                                               ".","8","=","5",
                                                               "4","-","x","9",
                                                                "1","+","7","6",
                                                                "3","2","0"};

    public SoundPoolUtil(Context context) {
        this.soundPool = new SoundPool(SoundmaxStreams, AudioManager.STREAM_MUSIC,SoundsrcQuality);
        //注意这里，设置了数组长度，如果数组过大应该作一下修改！
        Name2Sound=new String[25];
        Loadinit(context);
    }


    public void setPlay(boolean play) {
        isPlay = play;
    }

    /**
     * 加载默认的语音
     * @param context
     */
    public void Loadinit(Context context){

        addSound(context,R.raw.ac,"ac");
        addSound(context,R.raw.del,"del");
        addSound(context,R.raw.div,"div");
        addSound(context,R.raw.dot,"dot");
        addSound(context,R.raw.eight,"eight");
        addSound(context,R.raw.equal,"equal");
        addSound(context,R.raw.five,"five");
        addSound(context,R.raw.four,"four");
        addSound(context,R.raw.minus,"minus");
        addSound(context,R.raw.mul,"mul");
        addSound(context,R.raw.nine,"nine");
        addSound(context,R.raw.one,"one");
        addSound(context,R.raw.plus,"plus");
        addSound(context,R.raw.seven,"seven");
        addSound(context,R.raw.six,"six");
        addSound(context,R.raw.three,"three");
        addSound(context,R.raw.two,"two");
        addSound(context, zero,"zero");
        addSound(context,R.raw.bai,"bai");
        addSound(context,R.raw.fu,"fu");
        addSound(context,R.raw.qian,"qian");
        addSound(context,R.raw.shi,"shi");
        addSound(context,R.raw.wan,"wan");
        addSound(context,R.raw.yi,"yi");

//        soundPool.load(context,R.raw.ac,1);
//        //清除
//        soundPool.load(context,R.raw.del,1);
//        soundPool.load(context,R.raw.div,1);
//        soundPool.load(context,R.raw.dot,1);
//        soundPool.load(context,R.raw.eight,1);
//        soundPool.load(context,R.raw.equal,1);
//        soundPool.load(context,R.raw.five,1);
//        soundPool.load(context,R.raw.four,1);
//        soundPool.load(context,R.raw.minus,1);
//        soundPool.load(context,R.raw.mul,1);
//        soundPool.load(context,R.raw.nine,1);
//        soundPool.load(context,R.raw.one,1);
//        soundPool.load(context,R.raw.plus,1);
//        soundPool.load(context,R.raw.seven,1);
//        soundPool.load(context,R.raw.six,1);
//        soundPool.load(context,R.raw.three,1);
//        soundPool.load(context,R.raw.two,1);
//        soundPool.load(context,R.raw.zero,1);

    }

    /**
     * 注意这里的注解，如果是图片的话可能用到Reference属性
     * @param context
     * @param SoundId
     */
    public void addSound(Context context,int SoundId,String name){
        soundPool.load(context,SoundId,1);
        soundPosition=soundPosition+1;
        Name2Sound[soundPosition]=name;
    }

    /**
     *
     * @param postion  这里的postion是指语音添加到SoundPool里的位置
     */
    public void playSound(int postion){
        soundPool.play(postion, 1, 1, 0, 0, 1);
    }

    /**
     * 输入数字播放相应的文字
     * @param number
     */
    public void playSoundByNumber(Integer number) {
        if (isPlay) {
            //   Log.i(TAG,"playSoundByNumber"+number);
            for (int j = 0; j < position_voice.length; j++) {
                if (number.toString().equals(position_voice[j])) {
                    soundPool.play(j, 1, 1, 0, 0, 1);
                    //         Log.i(TAG,"playSoundByNumber"+j);
                }
            }
        }
    }


    public void playSoundByName(String SoundName) {
        if (isPlay) {
            int temp_position = 0;
            //获取语音位置，可以考虑将其封装成方法。

            for (int i = 0; i < Name2Sound.length; i++) {
                if (Name2Sound[i] == SoundName) {
                    temp_position = i;
                }
            }

            soundPool.play(temp_position, 1, 1, 0, 0, 1);

        }
    }
    public void playSoundByChar(String SoundName) {
        if (isPlay) {
            int temp_position = 0;
            //获取语音位置，可以考虑将其封装成方法。
            for (int i = 0; i < position_voice.length; i++) {
                if (position_voice[i].equals(SoundName)) {
                    temp_position = i;
                }
            }
            if (temp_position != 0) {
                soundPool.play(temp_position, 1, 1, 0, 0, 1);
            }
        }

    }



    public void PlayByNumber(Integer num){

        //9898 9123 4567
        //1234 0001 8989
        //1234 0000 0012
        //这里出来的String是num真正的属性
        if(isPlay) {
            Log.i(TAG, "number's length" + num.toString().length() + "hahahah");
            int numberlength = num.toString().length();
            boolean wanPalyed = false;
            int count = 0, count1 = 0;


            //1 2345 6789               1234 5678 7890
            for (int i = 0; i < numberlength; i++) {
                Log.i(TAG, "numberpositon:" + num.toString().charAt(i) + "^^^^" + "i==" + i);
                int numInPostion = Integer.parseInt(num.toString().charAt(i) + "");

                //1、出现0，则消化0直到出现非0
                if (numInPostion == 0) {
                    if (i < numberlength - 1) {
                        while (Integer.parseInt(num.toString().charAt(i + 1) + "") == 0) {
                            i++;
                            if (i == numberlength - 1) return;
                            if ((numberlength - i) == 9) {
                                playSoundByName("yi");
                                count++;
                                try {
                                    Thread.sleep(DelayToSound);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            count1++;
                            count++;
                            if ((numberlength - i) == 5) {
                                playSoundByName("wan");
                                count++;
                                try {
                                    Thread.sleep(DelayToSound);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                wanPalyed = true;
                            }


                        }
                    }
                    Log.i(TAG, "我是0，不是0走开");
                }


                //解决把10读成一十
                //if((numberlength-i)%4==2&numInPostion==1){
                //}else{}

                //读取原数字,这里能够准确地保证只在一次特殊的情况下执行该项代码
                if (count == count1) {
                    playSoundByNumber(Integer.parseInt(num.toString().charAt(i) + ""));
                } else {
                    //测试数据:1231004304
                    Log.i(TAG, "读取到万一次");
                    count = 0;
                    count1 = 0;
                }

                try {
                    Thread.sleep(DelayToSound);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if ((numberlength - i) == 9) {
                    playSoundByName("yi");
                    try {
                        Thread.sleep(DelayToSound);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!wanPalyed) {
                    if ((numberlength - i) == 5) {
                        playSoundByName("wan");
                        try {
                            Thread.sleep(DelayToSound);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                if (numInPostion != 0) {

                    Log.i(TAG, "我不是0,0进来了吗？");
                    //TODO:这里先写死，有时间可能算一下
                    if ((numberlength - i) % 4 == 0) {
                        playSoundByName("qian");
                        try {
                            Thread.sleep(DelayToSound);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((numberlength - i) % 4 == 3) {
                        playSoundByName("bai");
                        try {
                            Thread.sleep(DelayToSound);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((numberlength - i) % 4 == 2) {
                        playSoundByName("shi");
                        try {
                            Thread.sleep(DelayToSound);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
//
//            int[] number=new int[NumberLength];
//            for(int i=0;i<num.toString().length()+2;i++){
//                Log.i(TAG,num.toString()+"&&&&&&&&&&");
//                //反向存储
//                number[NumberLength-i-1]=num%10;
//                num=num/10;
//                Log.i(TAG,num.toString()+"^^^^^^^^^^");
//            }



//        Boolean isZero=true;
//        for (int i=0;i<temp.length;i++){
//            Log.i(TAG,temp[i]+"%%%%%");
//            String re=String.valueOf(temp[i]);
//            if(temp[i]!=0){
//                isZero=false;
//            }
//            if(isZero==false) {
//                try {
//                    Thread.sleep(600);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                for (int j = 0; j < position_voice.length; j++) {
//                    if (re.equals(position_voice[j])) {
//                        soundPool.play(j, 1, 1, 0, 0, 1);
//                    }
//                }
//            }
//
//        }
//        if(isZero){
//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            soundPool.play(18, 1, 1, 0, 0, 1);
//        }
//        isZero=true;


    }

    //这里不注释会影响上边的函数
//    public void PlayByNumber(float num){
//
//    }


    public void PlayByNumber(String floatnum) {
        if (isPlay) {
            String[] numbers = floatnum.split("\\.");
            //记得这里得进行转义

            Log.i(TAG, "数字" + "*****" + numbers[0] + "小数点后" + "数字" + numbers[1]);

            PlayByNumber(Integer.parseInt(numbers[0]));
            //Log.i(TAG,Integer.parseInt(numbers[0])+"测试小数前的数字");

            playSoundByName("dot");
            try {
                Thread.sleep(DelayToSound);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < numbers[1].length(); i++) {
                //Log.i(TAG, "numberpositon:" + num.toString().charAt(i) + "^^^^" + "i==" + i);
                int number = Integer.parseInt(numbers[1].toString().charAt(i) + "");
                playSoundByNumber(number);
                try {
                    Thread.sleep(DelayToSound);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
