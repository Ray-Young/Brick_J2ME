package main;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.m3g.Image2D;

import com.sun.j2me.global.Constants;
import com.sun.perseus.model.Font;

public class Ball extends Canvas implements Runnable, CommandListener {
	int Height = this.getHeight();
	int x = this.getWidth() /2; // 第一个小球的横坐标
	int y = this.getHeight() /2; // 第一个小球的纵坐标

	int c = this.getWidth() / 2; // hard模式下，第二个小球的横坐标
	int d = this.getHeight() / 2; // hard模式下，第二个小球的纵坐标

	int a = this.getWidth() / 2 - 30;
	int b = this.getHeight() - this.getHeight() / 4;

	public static boolean flag1=true;
	public static int count = 0;
	public static int winningcount = 0;
	
	String result;
	int dir = 0;

	// int width=20;
	int height = 8;
	Brick[][] brick = new Brick[15][7];
	Image image1;
	Image image2;
	Image image3;
	Image image4;
	Image image5;

	Ball() {
		// System.out.println(this.getWidth());
		// System.out.println(this.getHeight());
		// Brick [][]brick=new Brick[15][7];
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 15; i++) {
				brick[i][j] = new Brick();
			}
		}
		try {
			// //图片的读取必须放在paint中，如果放在paint中会大大影响执行效率
			image1 = Image.createImage("/background.png");
			image2 = Image.createImage("/b.png");
			image3 = Image.createImage("/c.png");
			image4 = Image.createImage("/d.png");
			image5 = Image.createImage("/e.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Image image = Image.createImage("\1.png");
	}

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub

	}

	protected void paint(Graphics g) {
		if (flag1 == true) {
			// 这里要尤其注意，这里设置的每次像素移动2，而小球的初始位置是（偶数，奇数）所以在判断触碰时必须留范围而不能绝对相等，因此方块之间空两格以保证判断不干扰。
			g.setColor(0x000000);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(image1, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.setColor(255, 255, 0);
			// Brick [][]brick=new Brick[15][7];
			int height = 8;
			for (int j = 0; j < 7; j++) {
				int width = 20;
				for (int i = 0; i < 15; i++) {
					if (brick[i][j].flag) {
						brick[i][j].x = width - 20;
						brick[i][j].y = height - 8;
						g.setColor(255, 255, 0);
						g.fillRect(brick[i][j].x, brick[i][j].y, 20, 8);
						if (j == 0) {
							g.drawImage(image2, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 1) {
							g.drawImage(image3, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 2) {
							g.drawImage(image4, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 3) {
							g.drawImage(image5, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 4) {
							g.drawImage(image2, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 5) {
							g.drawImage(image3, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						} else if (j == 6) {
							g.drawImage(image4, brick[i][j].x, brick[i][j].y,
									Graphics.LEFT | Graphics.TOP);
						}
					}
					width = width + 22;

				}
				height = height + 10;
			}
			g.setColor(255, 0, 0);
			g.fillRoundRect(a, b, 60, 10, 20, 20);
			g.setColor(255, 255, 0);
			g.fillRect(x, y, 4, 4);
			g.setColor(255, 255, 0);
			g.drawString("当前得分: " + count, 10, this.getHeight() - 30,
					Graphics.TOP | Graphics.LEFT);
			if (Demo.hardflag) {
				g.setColor(255, 0, 0);
				g.fillRect(c, d, 4, 4);
			}
		} 
		else if (flag1 == false) {
			g.setColor(0x000000);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(image1, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.setFont(Demo.font);
			g.setColor(255, 0, 0);
			g.drawString("Game Over   最高分  " + count, this.getWidth() / 2-65,
					this.getHeight() / 2, Graphics.TOP | Graphics.LEFT);
		} else if (winningcount == 105) {
			g.setColor(0x000000);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(image1, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.setFont(Demo.font);
			g.setColor(0xffffff);
			g.drawString("Win!    最高分 " + count, this.getWidth() / 2-65,
					this.getHeight() / 2, Graphics.TOP | Graphics.LEFT);
			
		}

		// TODO Auto-generated method stub

	}

	public void keyPressed(int keyCode) {
		System.out.println(keyCode);
		if (keyCode == -3) {
			a = a - 30;
		}
		if (keyCode == -4) {
			a = a + 30;
		}
	}

	public void run() {

		int speedx = Demo.frame_rate; // 每次移动的像素格数是2
		int speedy = Demo.frame_rate;
		int speedc = Demo.frame_rate; // 每次移动的像素格数是2
		int speedd = Demo.frame_rate;
		System.out.println(y);
		while (true) // 循环执行，动画效果
		{
			if (flag1 == true) {
					x = x + speedx;
					y = y + speedy;
					c = c - speedc;
					d = d - speedd;
					// 第一个小球和第二个小球除了初始方向不同，运行逻辑是相同的，这样的话只要使其初始方向不同就可以了，
					// 用speedx和speedy来统筹他们的运行速度和方向变化
					for (int j = 0; j < 7; j++) {
						for (int i = 0; i < 15; i++) {
							// 下底面的弹射
							if ((x > brick[i][j].x) && (x < brick[i][j].x + 20)
									&& (y <= brick[i][j].y + 7)
									&& (y >= brick[i][j].y + 6)) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("下底边碰撞");
								System.out.println(x);
								System.out.println(y);
								speedy = -speedy;
								count = count + 50;
							}
							// 两边的弹射
							if ((y > brick[i][j].y)
									&& (y < brick[i][j].y + 7)
									&& ((x == brick[i][j].x) || (x == brick[i][j].x + 20))) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("两面碰撞");
								System.out.println(x);
								System.out.println(y);
								System.out.println(brick[0][6].x);
								System.out.println(brick[0][6].y);
								speedx = -speedx;
								count = count + 50;
							}
							// 上底面的弹射
							if ((x > brick[i][j].x) && (x < brick[i][j].x + 20)
									&& (y >= brick[i][j].y - 1)
									&& (y <= brick[i][j].y + 0.5)) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("上底边碰撞");
								System.out.println(x);
								System.out.println(y);
								speedy = -speedy;
								count = count + 50;
							}
							if (Demo.hardflag) {
								if ((c > brick[i][j].x)
										&& (c < brick[i][j].x + 20)
										&& (d <= brick[i][j].y + 7)
										&& (d >= brick[i][j].y + 6)) {
									brick[i][j].flag = false;
									winningcount++;
									brick[i][j].x = this.getWidth();
									brick[i][j].y = this.getHeight();
									speedd = -speedd;
									count = count + 50;
								}
								// 两边的弹射
								if ((d > brick[i][j].y)
										&& (d < brick[i][j].y + 7)
										&& ((c == brick[i][j].x) || (c == brick[i][j].x + 20))) {
									brick[i][j].flag = false;
									winningcount++;
									brick[i][j].x = this.getWidth();
									brick[i][j].y = this.getHeight();
									System.out.println(brick[0][6].x);
									System.out.println(brick[0][6].y);
									speedc = -speedc;
									count = count + 50;
								}
								// 上底面的弹射
								if ((c > brick[i][j].x)
										&& (c < brick[i][j].x + 20)
										&& (d >= brick[i][j].y - 1)
										&& (d <= brick[i][j].y + 0.5)) {
									brick[i][j].flag = false;
									winningcount++;
									brick[i][j].x = this.getWidth();
									brick[i][j].y = this.getHeight();
									speedd = -speedd;
									count = count + 50;
								}
								if (d <= 0 || d >= this.getHeight()) // 如果小球碰到上下边界，再纵向速度取反
								{
									speedd = -speedd;
								}
								if (c <= 0 || c >= this.getWidth()) // 如果小球碰到左右边界，则横向速度取反
								{
									speedc = -speedc;
								}
								if ((d <= 0 || d >= this.getHeight())
										&& (c <= 0 || c >= this.getWidth())) // 这个情况是针对小球的运行恰好碰到直角点，则x,y方向的速度同时取反
								{
									speedc = -speedc;
									speedd = -speedd;
								}
								if (((d >= b - 1) && (d < b + 1))
										&& (c > a - 1) && (c < a + 60)) {
									speedd = -speedd;
								}
								if (d >Height) {
									flag1 = false;
								}
							}
						}
					}

					if (y <= 0 || y >= this.getHeight()) // 如果小球碰到上下边界，再纵向速度取反
					{
						speedy = -speedy;
					}
					if (x <= 0 || x >= this.getWidth()) // 如果小球碰到左右边界，则横向速度取反
					{
						speedx = -speedx;
					}

					if ((y <= 0 || y >= this.getHeight())
							&& (x <= 0 || x >= this.getWidth())) // 这个情况是针对小球的运行恰好碰到直角点，则x,y方向的速度同时取反
					{
						speedx = -speedx;
						speedy = -speedy;
					}

					if (((y >= b - 1) && (y < b + 1)) && (x > a - 1)
							&& (x < a + 60)) {
						speedy = -speedy;
					}

					if (a < 0) {
						a = this.getWidth() - 60;
					}
					if (a + 60 > this.getWidth()) {
						a = 0;
					}
					if (y >= Height) {
						flag1 = false;
					}
				repaint();
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
		}
			
			else if (flag1 == false) {
				repaint();
				break;
			}
		}

	}
}
