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
	int x = this.getWidth() /2; // ��һ��С��ĺ�����
	int y = this.getHeight() /2; // ��һ��С���������

	int c = this.getWidth() / 2; // hardģʽ�£��ڶ���С��ĺ�����
	int d = this.getHeight() / 2; // hardģʽ�£��ڶ���С���������

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
			// //ͼƬ�Ķ�ȡ�������paint�У��������paint�л���Ӱ��ִ��Ч��
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
			// ����Ҫ����ע�⣬�������õ�ÿ�������ƶ�2����С��ĳ�ʼλ���ǣ�ż�����������������жϴ���ʱ��������Χ�����ܾ�����ȣ���˷���֮��������Ա�֤�жϲ����š�
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
			g.drawString("��ǰ�÷�: " + count, 10, this.getHeight() - 30,
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
			g.drawString("Game Over   ��߷�  " + count, this.getWidth() / 2-65,
					this.getHeight() / 2, Graphics.TOP | Graphics.LEFT);
		} else if (winningcount == 105) {
			g.setColor(0x000000);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(image1, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.setFont(Demo.font);
			g.setColor(0xffffff);
			g.drawString("Win!    ��߷� " + count, this.getWidth() / 2-65,
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

		int speedx = Demo.frame_rate; // ÿ���ƶ������ظ�����2
		int speedy = Demo.frame_rate;
		int speedc = Demo.frame_rate; // ÿ���ƶ������ظ�����2
		int speedd = Demo.frame_rate;
		System.out.println(y);
		while (true) // ѭ��ִ�У�����Ч��
		{
			if (flag1 == true) {
					x = x + speedx;
					y = y + speedy;
					c = c - speedc;
					d = d - speedd;
					// ��һ��С��͵ڶ���С����˳�ʼ����ͬ�������߼�����ͬ�ģ������Ļ�ֻҪʹ���ʼ����ͬ�Ϳ����ˣ�
					// ��speedx��speedy��ͳ�����ǵ������ٶȺͷ���仯
					for (int j = 0; j < 7; j++) {
						for (int i = 0; i < 15; i++) {
							// �µ���ĵ���
							if ((x > brick[i][j].x) && (x < brick[i][j].x + 20)
									&& (y <= brick[i][j].y + 7)
									&& (y >= brick[i][j].y + 6)) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("�µױ���ײ");
								System.out.println(x);
								System.out.println(y);
								speedy = -speedy;
								count = count + 50;
							}
							// ���ߵĵ���
							if ((y > brick[i][j].y)
									&& (y < brick[i][j].y + 7)
									&& ((x == brick[i][j].x) || (x == brick[i][j].x + 20))) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("������ײ");
								System.out.println(x);
								System.out.println(y);
								System.out.println(brick[0][6].x);
								System.out.println(brick[0][6].y);
								speedx = -speedx;
								count = count + 50;
							}
							// �ϵ���ĵ���
							if ((x > brick[i][j].x) && (x < brick[i][j].x + 20)
									&& (y >= brick[i][j].y - 1)
									&& (y <= brick[i][j].y + 0.5)) {
								brick[i][j].flag = false;
								winningcount++;
								brick[i][j].x = this.getWidth();
								brick[i][j].y = this.getHeight();
								System.out.println("�ϵױ���ײ");
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
								// ���ߵĵ���
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
								// �ϵ���ĵ���
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
								if (d <= 0 || d >= this.getHeight()) // ���С���������±߽磬�������ٶ�ȡ��
								{
									speedd = -speedd;
								}
								if (c <= 0 || c >= this.getWidth()) // ���С���������ұ߽磬������ٶ�ȡ��
								{
									speedc = -speedc;
								}
								if ((d <= 0 || d >= this.getHeight())
										&& (c <= 0 || c >= this.getWidth())) // �����������С�������ǡ������ֱ�ǵ㣬��x,y������ٶ�ͬʱȡ��
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

					if (y <= 0 || y >= this.getHeight()) // ���С���������±߽磬�������ٶ�ȡ��
					{
						speedy = -speedy;
					}
					if (x <= 0 || x >= this.getWidth()) // ���С���������ұ߽磬������ٶ�ȡ��
					{
						speedx = -speedx;
					}

					if ((y <= 0 || y >= this.getHeight())
							&& (x <= 0 || x >= this.getWidth())) // �����������С�������ǡ������ֱ�ǵ㣬��x,y������ٶ�ͬʱȡ��
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
