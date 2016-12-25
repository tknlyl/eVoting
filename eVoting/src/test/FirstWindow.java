package test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class FirstWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstWindow window = new FirstWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FirstWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton enterButton = new JButton("Enter");
		enterButton.setBounds(122, 79, 172, 102);
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pelin");
				SwingWorker<Integer, String> swingWorker = new SwingWorker<Integer, String>(){

					@Override
					protected Integer doInBackground() throws Exception {
						// TODO Auto-generated method stub
						//calculate();
						System.out.println("Start!!!");
						try {
							Thread.sleep(1000);
							publish("20");	//publish metodu kullanýcýyla durum paylaþýmý için kullanýlýr.
							Thread.sleep(1000);
							publish("40");
							Thread.sleep(1000);
							publish("60");
							Thread.sleep(1000);
							publish("80");
							Thread.sleep(1000);
							publish("100");	//Bir listede tutuluyor. Sistem müsait olunca 3 publish listenin içinde geliyor.
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//System.out.println("End!!!!");
						return null;
					}
					
					@Override
					protected void process(List<String> chunks) {
						// TODO Auto-generated method stub
						System.out.println(chunks.toString());
						super.process(chunks);
					}
					
					@Override
					protected void done() {
						// TODO Auto-generated method stub
						System.out.println("End!!!!"); //Done methoduna ekledik sonradan. doInBackground'ta 100'den önce End yazýyordu.
						super.done();
					}
				};
				swingWorker.execute();
			}
		});
		frame.getContentPane().add(enterButton);
	}
	
	public void calculate(){
		System.out.println("Start!!!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End!!!!");
	}

}
