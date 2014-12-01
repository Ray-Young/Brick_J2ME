package main;

import java.io.IOException;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class Demo extends MIDlet implements CommandListener {
	private List list1;
	private List list2;
	private List list3;
	private Gauge gauge1;
	private Gauge gauge2;
	private Display display;
	public static int frame_rate;
	public static int block_number;
	public static boolean hardflag;
	public static Font font= Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
//	private int i=0;                         //记录条数
	private String Mode;
	private Command cmd1 = new Command("确定", Command.ITEM, 1); // 初始界面的确定键
	private Command cmd2 = new Command("退出", Command.EXIT, 1);
	
	private Command cmd3 = new Command("保存分数记录", Command.EXIT, 1); // BALL保存分数记录
	private Command cmd4 = new Command("确定", Command.ITEM, 1); // 选择难度界面的确定键
	
	private Command cmd5 = new Command("返回", Command.ITEM, 1); // BALL返回游戏初始界面
	
	//private Command cmd7 = new Command("删除", Command.EXIT, 1);   //LIST3 排行榜 删除记录
	private Command cmd8 = new Command("显示记录", Command.EXIT, 1);  //LIST3 排行榜显示排行榜记录
	
	private RecordStore rs;
	



	

	public Demo() throws IOException {
		Image image1 = Image.createImage("/1.png");
		Image image2 = Image.createImage("/2.png");
		Image image3 = Image.createImage("/3.png");
		Image image4 = Image.createImage("/6.png");
		Image image5 = Image.createImage("/7.png");
		Image image6 = Image.createImage("/8.png");
		// 初始界面list1初始化
		list1 = new List("Welcome", Choice.IMPLICIT);
		list1.append(" 新游戏", image1);
		list1.append(" 继续", image2);
		list1.append(" 排行榜", image3);
		// 难度选择界面初始化
		list2 = new List("难度选择", Choice.IMPLICIT);
		list2.append("简单", image4);
		list2.append("中等", image5);
		list2.append("困难", image6);
		list2.addCommand(cmd2);
		list2.addCommand(cmd4);
		list1.addCommand(cmd1);
		list1.addCommand(cmd2);
		list2.setCommandListener(this);
		list1.setCommandListener(this);
		// 历史记录界面
		list3 = new List("排行榜",Choice.IMPLICIT);
		try {
			rs=RecordStore.openRecordStore("data", true);        //简历排行榜的数据库
		} catch (RecordStoreFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list3.addCommand(cmd8);
		list3.addCommand(cmd5);
		list3.setCommandListener(this);

	}

	protected void startApp() {
		display = display.getDisplay(this);
		display.setCurrent(list1);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == cmd1) {
			if (list1.getSelectedIndex() == 0) {
				display.setCurrent(list2);
			}
			if (list1.getSelectedIndex() == 1) {

			}
			if (list1.getSelectedIndex() == 2) {
				display.setCurrent(list3);
			}
		}
		if (arg0 == cmd4) {
			if (list2.getSelectedIndex() == 0) {
				Ball ball;
				ball = new Ball();
				display.setCurrent(ball);
				ball.addCommand(cmd5);
				ball.addCommand(cmd3);
				ball.flag1=true;
				ball.count=0;
				ball.winningcount=0;
				Mode="简单模式";
				frame_rate = 1;
				hardflag = false;
				ball.setCommandListener(this);
				Thread th = new Thread(ball);
				th.start();
			}
			if (list2.getSelectedIndex() == 1) {
				Ball ball;
				ball = new Ball();
				display.setCurrent(ball);
				ball.addCommand(cmd5);
				ball.addCommand(cmd3);
				ball.flag1=true;
				ball.count=0;
				ball.winningcount=0;
				Mode="中等模式";
				frame_rate = 2;
				hardflag = false;
				ball.setCommandListener(this);
				Thread th = new Thread(ball);
				th.start();
			}
			if (list2.getSelectedIndex() == 2) {
				Ball ball;
				ball = new Ball();
				display.setCurrent(ball);
				ball.addCommand(cmd5);
				ball.addCommand(cmd3);
				ball.flag1=true;
				ball.count=0;
				ball.winningcount=0;
				Mode="困难模式";
				frame_rate = 1;
				hardflag = true;
				ball.setCommandListener(this);
				Thread th = new Thread(ball);
				th.start();
			}
		}
		if (arg0 == cmd2) {
			destroyApp(false);
			notifyDestroyed();
		}
		if (arg0 == cmd3) {
			if(Ball.flag1==true)
			{
				System.out.println("游戏尚未结束，不能保存");
			}
			else if(Ball.flag1==false)
			{
				String temp=Mode+"           "+String.valueOf(Ball.count);
				
				
				byte[] b1 = temp.getBytes();     //将分数转为string并且写入RS
				try {
					rs.addRecord(b1, 0, b1.length);
				} catch (RecordStoreNotOpenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RecordStoreFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RecordStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (arg0 == cmd5) {                        //返回初始界面
			display.setCurrent(list1);
		}
		
		if(arg0==cmd8)                           //遍历数据库
		{
			int i=0;
			RecordEnumeration re;
			try {
				
				// 如果recordnum>0,则说明有
				if (rs.getNumRecords() > 0) {
					try {
						list3.deleteAll();
						//由于这里只是用到遍历功能，不需要排序也不需要实时更新，所以这样设置就可以。
						re = rs.enumerateRecords(null, null, false);
						while (re.hasNextElement()) {
							//读取下一条记录，然后强制转型为String,然后输出
							byte[] data1 = re.nextRecord();
							String temp = new String(data1);
							list3.append("记录   "+ String.valueOf(i)+"        "+temp, null);
							i++;
						}
					} catch (RecordStoreNotOpenException e) {
						e.printStackTrace();
						//无效的输入
					} catch (InvalidRecordIDException e) {
						e.printStackTrace();
						//异常操作
					} catch (RecordStoreException e) {
						e.printStackTrace();
					}
				} else if (rs.getNumRecords() == 0) {
					list3.deleteAll();
					list3.append("数据库中没有数据", null);
				}
			} catch (RecordStoreNotOpenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void destroyApp(boolean arg0) {

	}

	protected void pauseApp() {

	}

}
