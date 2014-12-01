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
//	private int i=0;                         //��¼����
	private String Mode;
	private Command cmd1 = new Command("ȷ��", Command.ITEM, 1); // ��ʼ�����ȷ����
	private Command cmd2 = new Command("�˳�", Command.EXIT, 1);
	
	private Command cmd3 = new Command("���������¼", Command.EXIT, 1); // BALL���������¼
	private Command cmd4 = new Command("ȷ��", Command.ITEM, 1); // ѡ���ѶȽ����ȷ����
	
	private Command cmd5 = new Command("����", Command.ITEM, 1); // BALL������Ϸ��ʼ����
	
	//private Command cmd7 = new Command("ɾ��", Command.EXIT, 1);   //LIST3 ���а� ɾ����¼
	private Command cmd8 = new Command("��ʾ��¼", Command.EXIT, 1);  //LIST3 ���а���ʾ���а��¼
	
	private RecordStore rs;
	



	

	public Demo() throws IOException {
		Image image1 = Image.createImage("/1.png");
		Image image2 = Image.createImage("/2.png");
		Image image3 = Image.createImage("/3.png");
		Image image4 = Image.createImage("/6.png");
		Image image5 = Image.createImage("/7.png");
		Image image6 = Image.createImage("/8.png");
		// ��ʼ����list1��ʼ��
		list1 = new List("Welcome", Choice.IMPLICIT);
		list1.append(" ����Ϸ", image1);
		list1.append(" ����", image2);
		list1.append(" ���а�", image3);
		// �Ѷ�ѡ������ʼ��
		list2 = new List("�Ѷ�ѡ��", Choice.IMPLICIT);
		list2.append("��", image4);
		list2.append("�е�", image5);
		list2.append("����", image6);
		list2.addCommand(cmd2);
		list2.addCommand(cmd4);
		list1.addCommand(cmd1);
		list1.addCommand(cmd2);
		list2.setCommandListener(this);
		list1.setCommandListener(this);
		// ��ʷ��¼����
		list3 = new List("���а�",Choice.IMPLICIT);
		try {
			rs=RecordStore.openRecordStore("data", true);        //�������а�����ݿ�
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
				Mode="��ģʽ";
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
				Mode="�е�ģʽ";
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
				Mode="����ģʽ";
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
				System.out.println("��Ϸ��δ���������ܱ���");
			}
			else if(Ball.flag1==false)
			{
				String temp=Mode+"           "+String.valueOf(Ball.count);
				
				
				byte[] b1 = temp.getBytes();     //������תΪstring����д��RS
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
		if (arg0 == cmd5) {                        //���س�ʼ����
			display.setCurrent(list1);
		}
		
		if(arg0==cmd8)                           //�������ݿ�
		{
			int i=0;
			RecordEnumeration re;
			try {
				
				// ���recordnum>0,��˵����
				if (rs.getNumRecords() > 0) {
					try {
						list3.deleteAll();
						//��������ֻ���õ��������ܣ�����Ҫ����Ҳ����Ҫʵʱ���£������������þͿ��ԡ�
						re = rs.enumerateRecords(null, null, false);
						while (re.hasNextElement()) {
							//��ȡ��һ����¼��Ȼ��ǿ��ת��ΪString,Ȼ�����
							byte[] data1 = re.nextRecord();
							String temp = new String(data1);
							list3.append("��¼   "+ String.valueOf(i)+"        "+temp, null);
							i++;
						}
					} catch (RecordStoreNotOpenException e) {
						e.printStackTrace();
						//��Ч������
					} catch (InvalidRecordIDException e) {
						e.printStackTrace();
						//�쳣����
					} catch (RecordStoreException e) {
						e.printStackTrace();
					}
				} else if (rs.getNumRecords() == 0) {
					list3.deleteAll();
					list3.append("���ݿ���û������", null);
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
