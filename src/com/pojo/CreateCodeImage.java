package com.pojo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;





public class CreateCodeImage {
	//创建Random对象
	 static Random random=new Random();
	 //随机生成包含验证码字符串
	 public static String random(int num){
	  //初始化种子
	  String[] str={"0","1","2","3","4","5","6","7","8","9",
	       "a","b","c","d","e","f","g","h","i","j",
	       "k","l","m","n","p","q","r","s","t"};
	  int number=str.length;
	  //接收随机字符
	  String text = "";
	  //随机产生4个字符的字符串
	  //System.out.print(num);
	  for(int i=0;i<num;i++){
	   text+=str[random.nextInt(number)];
	  }
	  return text;
	 }
	 /**
	  * 随机产生定义的颜色
	  * 
	  * @return
	  */
//	 private static Color getRandColor() {
//	  Random random = new Random();
//	  Color color[] = new Color[10];
//	  color[0] = new Color(32, 158, 25);
//	  color[1] = new Color(218, 42, 19);
//	  color[2] = new Color(31, 75, 208);
//	  color[3] = new Color(0, 102, 182);
//	  color[4] = new Color(171, 0, 85);
//	  color[5] = new Color(63,35,104);
//	  color[6] = new Color(172,217,248);
//	  color[7] = new Color(94,170,50);
//	  color[8] = new Color(233,109,91);
//	  color[9] = new Color(85,174,252);
//	  return color[random.nextInt(10)];
//	 }
	 private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		         if (fc > 255)
		              fc = 255;
		          if (bc > 255)
		              bc = 255;
		          int r = fc + random.nextInt(bc - fc);
		          int g = fc + random.nextInt(bc - fc);
		          int b = fc + random.nextInt(bc - fc);
		          return new Color(r, g, b);
		      }
	 /**
	  * 产生随机字体
	  * 
	  * @return
	  */
	 private static Font getFont() {
	  Random random = new Random();
	  Font font[] = new Font[4];
	  font[0] = new Font("Ravie", Font.PLAIN, 30);
	  font[1] = new Font("Antique Olive Compact", Font.PLAIN, 30);
	  font[2] = new Font("Forte", Font.PLAIN, 30);
	  //font[3] = new Font("Wide Latin", Font.PLAIN, 30);
	  font[3] = new Font("Times New Roman", Font.PLAIN, 30);
	  
	  //font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, 30);
	  return font[random.nextInt(4)];
	 }
	 /**
	  * 生成图片
	  * @throws IOException 
	  */
	 public static void render(String randomStr,OutputStream out,int width,int height) throws IOException{
	  //在内存中创建图像
	  BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
	  //获取图形上下文
	  Graphics2D g=(Graphics2D) bi.getGraphics();
	  //话边框
	  g.setColor(Color.white);
	  g.fillRect(0, 0, width, height);
	  g.setFont(getFont());
	  g.setColor(Color.BLACK);
	  //画认证码，每个认证码在不同的水平位置
	  String str1[]=new String[randomStr.length()];
	  for(int i=0;i<str1.length;i++){
	   str1[i]=randomStr.substring(i,i+1);
	   int h=0;
	   int x=(i+1)%3;
	   //随机生成验证码字符水平偏移量
	   if(x==random.nextInt(3)){
	    h=25-random.nextInt(3);
	   }else{
	    h=25+random.nextInt(3);
	   }
	   //随机生成颜色
	  // g.setColor(getRandColor());
	   g.setColor(getRandColor(1, 255));
	   g.drawString(str1[i], 25*i+5, h);
	  }
	  //随机产生干扰点，并用不同的颜色表示，事图像的认证码不易被其他程序探测到
	  for(int i=0;i<100;i++){
	   int x=random.nextInt(width);
	   int y=random.nextInt(height);
	   Color color=new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	   //随机画各种颜色的线
	   g.setColor(color);
	   g.drawOval(x, y, 0, 0);
	  }
	  //画干扰线
	  for(int i=0;i<5;i++){
	   int x=random.nextInt(width);
	   int y=random.nextInt(height);
	   int x1=random.nextInt(width);
	   int y1=random.nextInt(height);
	   Color color=new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	   //随机画各种颜色线
	   g.setColor(color);
	   g.drawLine(x, y, x1, y1);
	  }
	  //图像生效
	  g.dispose();
	  //输出页面
	  ImageIO.write(bi, "jpg", out);
	 }
//	 public static void main(String[] args) throws FileNotFoundException, IOException {
//	  //获取随机字符串
//	  String randomStr=random(4);
//	  System.out.println(randomStr);
//	  //生成图片
//	  render(randomStr, new FileOutputStream("D:\\test.jpg"),120,32);
//	 }

	
}
