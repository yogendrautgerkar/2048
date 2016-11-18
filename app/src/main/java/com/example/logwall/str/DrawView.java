package com.example.logwall.str;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import java.util.Random;

class DrawView extends View {
    private static Rect rectangle;
    private static RectF rf;
    public int rig=0,lef=0,u=0,dwn=0;
    private  Paint paint;
    private int currentS=0,high=0;
  static   int b[][]=new int[4][4];
   static int c[][]=new int[4][4];
   static int a[][]=new int[4][4];
   static int d[][]=new int[4][4];
    int xyz=0;
    int x=0;
    int gameover=0;
    static int kl=1;
   public static int xinc,yinc,width,height;
    static int in_x=5,in_y=5;
    public int undo=0,reset=0;
    SharedPreferences pref = getContext().getSharedPreferences("game", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor;


    public DrawView(Context context) {
        super(context);


        // create a rectangle that we'll draw late
    }


    public  int right_shift(Canvas canvas)
    {
        int i,j,k,l;int shft=0;
//shifting
        for(i=0;i<4;i++)
        {
            for(j=3;j>0;j--)
            {
                if(a[i][j]==0)
                {
                    k=l=j;
                    while(k>=0&&a[i][k]==0)k--;
                    while(k>=0)
                    {
                        if(a[i][k]!=0)
                        {
                            a[i][l] = a[i][k];
                            shft=1;
                            l--;
                        }
                        a[i][k--]=0;

                    }

                }

            }
        }
        return shft;
    }
    public  void right(Canvas ap)
    {int i=0,j,k;int mer=0;

        k=right_shift(ap);


        for( i=0;i<4;i++){
            for( j=3;j>0;j--){

                if(a[i][j]==a[i][j-1]&& a[i][j]!=0){
                    a[i][j]=2*a[i][j];
                    mer=1;
                    currentS+=a[i][j];
                    if(high<currentS)
                        high=currentS;

                    a[i][j-1]=0;

                    right_shift(ap);

                }
            }
        }
        if(k==0&&mer==0)
            return;
        dr(ap);
        mn();
    }
    public  int left_shift(Canvas canvas)
    {
        int i, j, k, l;
        int shft=0;
//shifting
        for(i=0;i<4;i++)
        {
            for(j=0;j<3;j++)
            {
                if(a[i][j]==0)
                {
                    k=l=j;
                    while(k<4&&a[i][k]==0)k++;
                    while(k<4)
                    {
                        if(a[i][k]!=0) {
                            a[i][l] = a[i][k];
                          //  dr(canvas);
                           // delay();
                            shft=1;

                            l++;
                        }
                        a[i][k++]=0;
                    }

                }

            }
        }
        return shft;
    }
    public   void left(Canvas ap)
    {int i,j,k,mer=0;

        k=left_shift(ap);
                for( i=0;i<4;i++){
            for( j=0;j<3;j++){
                if(a[i][j]==a[i][j+1] && a[i][j]!=0){
                    a[i][j]=2*a[i][j];
                    mer=1;
                    currentS+=a[i][j];
                    if(high<currentS)
                        high=currentS;
                   a[i][j+1]=0;


                    left_shift(ap);

                }
            }
        }
        if(mer==0&&k==0)
            return;
        dr(ap);
        mn();
    }

//up and down

    private int down_shift()
    {
        int i,j,k,l;int shft=0;
//shifting
        for(i=0;i<4;i++)
        {
            for(j=3;j>0;j--)
            {
                if(a[j][i]==0)
                {
                    k=l=j;
                    while(k>0&&a[k][i]==0)k--;
                    while(k>=0) {
                        if (a[k][i] != 0) {
                            a[l--][i] = a[k][i];
                            a[k][i] = 0;shft=1;
                        }
                        k--;
                    }

                }

            }
        }
        return shft;
    }
    void down(Canvas ap)
    {int i,j,k;int mer=0;
        k=down_shift();

        for( i=0;i<4;i++){
            for( j=3;j>0;j--){
                if(a[j][i]==a[j-1][i] && a[j][i]!=0){
                    a[j][i]=2*a[j][i];
                    mer=1;
                    currentS+=a[j][i];
                    if(high<currentS)
                        high=currentS;
                    a[j-1][i]=0;

                    down_shift();

                }
            }

        }
        if(k==0&&mer==0)
            return;
        dr(ap);
        mn();
    }
    private int up_shift()
    {
        int i,j,k,l;int shft=0;
//shifting
        for(i=0;i<4;i++)
        {
            for(j=0;j<3;j++)
            {
                if(a[j][i]==0)
                {
                    k=l=j;
                    while(k<4&&a[k][i]==0)k++;
                    while(k<4) {
                        if (a[k][i] != 0) {
                            a[l++][i] = a[k][i];
                            shft=1;
                            a[k][i] = 0;
                        }
                        k++;
                    }

                }

            }
        }
        return shft;
    }
    void up(Canvas ap)
    {int i,j,k;int mer=0;
        k=up_shift();

        for( i=0;i<4;i++){
            for( j=0;j<3;j++){
                if(a[j][i]==a[j+1][i] && a[j][i]!=0){
                    a[j][i]=2*a[j][i];
                    mer=1;
                    currentS+=a[j][i];
                    if(high<currentS)
                        high=currentS;
                    a[j+1][i]=0;
                    up_shift();

                }
            }
        }
        if(k==0&&mer==0)
            return;
        dr(ap);
        mn();
    }
    Random r=new Random();
    int ind(int x)
    {

        return r.nextInt(x);
    }
    int check()
    {
        int mer=0;
        int i,j;int count=0;
        for(i=0;i<4;i++) {
            for (j = 0; j < 4; j++) {
                if (a[i][j] == 0)
                    count++;
                }
        }
        for( i=0;i<4;i++){
            for( j=0;j<3;j++){
                if(a[j][i]==a[j+1][i] && a[j][i]!=0){
                    mer=1;
                    }
            }
            if(mer==1)break;
        }
        for( i=0;i<4;i++){
            for( j=3;j>0;j--){
                if(a[j][i]==a[j-1][i] && a[j][i]!=0){
                   mer=1;
                   }

            }
            if(mer==1)break;
        }
        for( i=0;i<4;i++){
            for( j=0;j<3;j++){
                if(a[i][j]==a[i][j+1] && a[i][j]!=0){
                    mer=1;
                    }
            }
            if(mer==1)break;
        }

        for( i=0;i<4;i++){
            for( j=3;j>0;j--){
                if(a[i][j]==a[i][j-1] && a[i][j]!=0){

                    mer=1;
                    }
                           }
            if(mer==1)break;
        }

        if(mer==0&&count==0)
            return 1;
        else
            return 0;
    }

    void it()
    { x=0;
        int i,j;
        for(i=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {
                a[i][j]=0;
            }
        }
        int r=ind(16);
        i=r/4;
        j=r%4;
        int m=ind(7);
        if(m==0)
            a[i][j]=4;
        else
            a[i][j]=2;
              r=ind(16);
        i=r/4;
        j=r%4;
        m=ind(7);
        while(a[i][j]!=0)
        {
            r=ind(16);
            i=r/4;
            j=r%4;
        }

        if(m==0)
            a[i][j]=4;
        else
            a[i][j]=2;
        copy();

    }
    void delay(int abcd)
    {
        long  l=System.currentTimeMillis()+abcd;
        while(true)
        {
            if(l<System.currentTimeMillis())
                break;
        }
    }

    public  void mn(){

        int r=ind(16);
        int i=r/4;
        int j=r%4;
        int m=ind(7);
        while (a[i][j] != 0) {
            r = ind(16);
            i = r / 4;
            j = r % 4;
            m = ind(7);
        }
        if (m == 0)
            a[i][j] = 4;
        else
            a[i][j] = 2;
        in_x=i;
        in_y=j;
         kl=1;
        if(check()==1)
        {

            gameover=1;

        }
    }


    public  void struc(Canvas ap) {
        height = ap.getHeight();
        width = ap.getWidth();

        ap.drawColor(Color.BLACK);
        int i;
        int p, q;
         xinc=(width-2*(width*5/108)-3*(width/54))/4;
         yinc=xinc;

        p = 0;
        q = 0;
        int x = (width*125/2700);
        int y =2*(height/8)+((5*height/8)-3*(height/85)-4*xinc)/2;
        for (i = 0; i < 16; i++) {

            if (i % 4 == 0 && i > 0) {
                y += yinc+(height/85);
                p++;
                q = 0;
                x = (width*125/2700);
            }
            b[p][q] = x;
            c[p][q++] = y;
            rectangle = new Rect(x, y, x + xinc, y + yinc);
            rf = new RectF(rectangle);
            paint = new Paint();
            paint.setColor(Color.LTGRAY);
            ap.drawRoundRect(rf, yinc*4/25, yinc*4/25, paint);
            x += xinc+(width/54);
        }
        rectangle=new Rect(width/2,height*3/170,width/2+xinc,height*3/170+yinc*3/5);
        rf=new RectF(rectangle);
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        ap.drawRoundRect(rf, yinc*3/25,yinc*3/25, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(xinc*19/115);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("SCORE", width/2+xinc/2,(yinc*3/5)*8/15, paint);




        // high score


        rectangle=new Rect(width/2+xinc+xinc*2/23,height*3/170,width/2+2*xinc+10,height*3/170+yinc*3/5);
        rf=new RectF(rectangle);
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        ap.drawRoundRect(rf,yinc*3/25,yinc*3/25, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(xinc*19/115);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("HIGH SCORE", ((width+3*xinc)/2)+xinc*2/23, (yinc*3/5)*8/15, paint);





        // ONE STEP BACK
        rectangle=new Rect(width/2+xinc/2,yinc,width/2+xinc,yinc+yinc/2);
        rf=new RectF(rectangle);
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        ap.drawRoundRect(rf, yinc*3/25,yinc*3/25, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(xinc*19/115);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("UNDO", width/2+3*xinc/4,yinc+yinc/4 , paint);

        //RESET

        rectangle=new Rect(width/2+xinc*3/2,yinc,width/2+2*xinc,yinc+yinc/2);
        rf=new RectF(rectangle);
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        ap.drawRoundRect(rf, yinc*3/25,yinc*3/25, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(xinc*19/115);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("RESET",width/2+(xinc*3/2+2*xinc)/2 ,yinc+yinc/4 , paint);


        paint.setColor(Color.LTGRAY);
        paint.setTextSize(width*5/27);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("2048", width/4, height/5, paint);

        paint.setColor(Color.LTGRAY);
        paint.setTextSize(width*4/27);
        paint.setTextAlign(Paint.Align.CENTER);
        ap.drawText("JAAT EDITION", width/2, height*20/21, paint);
    }

    private String getNumberColor(int number) {
        switch (number) {


            case 2:

             return "#61B1FF";

            case 4:

             return "#1B81E5";

            case 8:
                return "#46D246";

            case 16:
                return "#2D992D";

            case 32:
                return "#F9571A";

            case 64:
                return "#E63000";

            case 128:
                return "#77b300";

            case 256:
                return "#B3FF66";

            case 512:
                return "#FFAE19";

            case 1024:
                return "#CC8400";

        }

        return "#6b8e23";
    }

    public  void dr(Canvas canvas)
    {
        int i,j;
        for(i=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {
                if(a[i][j]==0)
                {
                    rectangle=new Rect(b[i][j],c[i][j],b[i][j]+xinc,c[i][j]+yinc);
                    rf=new RectF(rectangle);
                    paint = new Paint();
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRoundRect(rf, yinc * 4 / 25, yinc * 4 / 25, paint);
                }else //if(i!=in_x&&j!=in_y)
                {
                    rectangle=new Rect(b[i][j],c[i][j],b[i][j]+xinc,c[i][j]+yinc);
                    paint = new Paint();
                    rf=new RectF(rectangle);
                    paint.setColor(Color.parseColor(getNumberColor(a[i][j])));
                    canvas.drawRoundRect(rf, yinc * 4 / 25, yinc * 4 / 25, paint);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(xinc * 8 / 23);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(Integer.toString(a[i][j]), b[i][j] + xinc / 2, c[i][j] + yinc / 2 + yinc * 2 / 25, paint);
                    if(a[i][j]==2048)
                    {mi=i;
                        mj=j;}

                }
            }
        }

    }
int mi,mj;
    public void copy()
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                d[i][j]=a[i][j];
    }
    public void und()
    {
        int i,j;
        for(i=0;i<4;i++)
            for(j=0;j<4;j++)
                a[i][j]=d[i][j];


    }




    public static Rect getRec(int jk)
    {
        int dec=(xinc*4)/23;
        int midx=b[in_x][in_y]+58,midy=c[in_x][in_y]+58;
        return new Rect(midx-dec*jk,midy-dec*jk,midx+dec*jk,midy+dec*jk);
    }
    public  void input(Canvas canvas)
    {
        Paint p=new Paint();


                       if(kl<4){
                rectangle = getRec(++kl);
                           p.setColor(Color.parseColor(getNumberColor(a[in_x][in_y])));

            }else{
                           p.setColor(Color.parseColor(getNumberColor(a[in_x][in_y])));
                rectangle = new Rect(b[in_x][in_y], c[in_x][in_y], b[in_x][in_y] + xinc, c[in_x][in_y] + yinc);
            }

            RectF rf = new RectF(rectangle);
        canvas.drawRoundRect(rf, yinc * 4 / 25, yinc * 4 / 25, p);
        p.setColor(Color.BLACK);
        p.setTextSize(xinc * 8 / 23);
        p.setTextAlign(Paint.Align.CENTER);
       // dr(canvas);
        canvas.drawText(Integer.toString(a[in_x][in_y]), b[in_x][in_y] + xinc / 2, c[in_x][in_y] +yinc/2 +yinc * 2 / 25, p);
        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.sound);
        if(a[mi][mj]==2048&&x<3)
        {
            if(x==0)
            {
               mp.start();
            }
            x++;

            {
                paint.setColor(Color.YELLOW);
                paint.setTextSize(width * 5 / 27);
                paint.setTextAlign(Paint.Align.CENTER);

                canvas.drawText("YOU WIN!", width / 2, height / 2, paint);
                delay(150);

            }



        }



    }






    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        struc(canvas);



      if(undo==1&&gameover==0)
         {
             undo=0;
             und();
             dr(canvas);
         }



        if(xyz==0||reset==1) {
            currentS=0;
            if(reset==1)
                reset=0;
            gameover=0;
            it();
            pref = getContext().getSharedPreferences("game", Context.MODE_PRIVATE);
            high = pref.getInt("high", 0);
            xyz++;
        }


        if(gameover==0) {
            if (rig == 1) {
                copy();
                right(canvas);
                rig = 0;

            }else
            if (lef == 1) {
                lef = 0;
                copy();
                left(canvas);
            }else
            if (u == 1) {
                u = 0;
                copy();
                up(canvas);
            }else
            if (dwn == 1) {
                dwn = 0;
                copy();
                down(canvas);
            }
        }
       // dr(canvas);
        editor = pref.edit();
        editor.putInt("high", high);
        editor.commit();
        paint.setColor(Color.BLACK);
        paint.setTextSize(yinc * 4 / 23);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Integer.toString(currentS), width / 2 + xinc / 2, ((yinc * 6 / 23) + yinc * 3 / 5) / 2 + yinc * 4 / 23, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(yinc * 4 / 23);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Integer.toString(high), width / 2 + xinc + 10 + xinc / 2, ((yinc * 6 / 23) + yinc * 3 / 5) / 2 + yinc * 4 / 23, paint);
        if(kl<4)
        {

            if(in_x < 4 && in_y<4) {
               dr(canvas);
                input(canvas);
                delay(50);
                invalidate();
              // dr(canvas);
                return;
            }


        }
        in_x=in_y=5;
        delay(100);
        dr(canvas);
        if(gameover==1) {
            paint.setColor(Color.YELLOW);
            paint.setTextSize(width * 5 / 27);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("YOU LOSE!", width / 2, height * 54 / 85, paint);
        }


    }


}