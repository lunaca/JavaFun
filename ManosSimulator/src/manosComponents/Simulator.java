package manosComponents;

import java.awt.Dimension;

import javax.swing.*;

public class Simulator extends JFrame {
	private JLabel [] jl = new JLabel[7];
	public CPU master;
	public int clock = 0;
	public String I = "0";
	
	JTextField bus = new JTextField();
	JTextField function = new JTextField();
	JTextField history = new JTextField();
	
	private JLabel [] vl = new JLabel[7];
public Simulator(){
	this.master = new CPU();
	

	 
}
public void addToTF(String value, JTextField tf){
tf.setText(tf.getText() + value + "/n" );
}

public void RunIt () throws Exception{
	
	if (clock == 0){
		System.out.println("T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T0T:");
		master.bus.onDaBus(0, 1, 0, master.AR);
	
		master.printState();
		 
		Thread.sleep(1000);
		
		this.clock = 1;
	}
	if (clock == 1){
		System.out.println("T1T1T1T1T1T1T1T1T1T1T1T1T1T1:");
		master.bus.onDaBus(1, 1, 1, master.IR);
		master.PC.inc();
		master.printState();
		 
		this.clock = 2;
		Thread.sleep(1000);
		
	}
	if(clock == 2){
		System.out.println("T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T2T: ");
		String op = master.IR.decodeOpCode();
		I = master.IR.decodeI();
		master.bus.onDaBus(1, 0, 1, master.AR);
		if(I.equals("0")&&(!op.equals("7"))){
			clock = 4;
			System.out.println("Direct Memory Reference");
		}
		if(I.equals("1")){
			clock = 3;
			System.out.println("Indirect Memory Reference");
		}
		if(op.equals("7")){
			clock = 3;
			System.out.println("Register Instruction");
		}
		master.printState();
		 
		Thread.sleep(1000);
	}
	if(clock == 3){
		System.out.println("T3T3T3T3T3T3T3T3T3T3T3T3T3T3T3:");
		String op = master.IR.decodeOpCode();
		I = master.IR.decodeI();
		if(op.equals("7")){
		String last3 = master.IR.cutOp();
		master.regIns(last3);
		clock = 0 ;
		}
		if(I.equals("1"))
		{
		master.bus.onDaBus(1, 1, 1, master.AR);
		clock = 4;
		}
		
		master.printState();
		 
		Thread.sleep(1000);
		
	}
	if(clock == 4){
		System.out.println("T4T4T4T4T4TT4T4T4T4T4T4T4T4T4T4T4T4T4");
	String directOpCode = master.IR.oneToSix();
	int clockadd = master.directOp(directOpCode, clock);
	clock += clockadd;
	master.printState();
	Thread.sleep(1000);
	}
	if(clock == 5){
		System.out.println("T5T5T5T5T5T5T5T5T5T5T5T5T5T5T5T5T");
		String directOpCode = master.IR.oneToSix();
		int clockadd = master.directOp(directOpCode, clock);
		master.printState();
		clock += clockadd;
		 
		Thread.sleep(1000);
	}
	if(clock == 6){
		System.out.println("T6T6T6T6T6T6T6T6T6T6T6T6T6T6T6T6T6T");
		String directOpCode = master.IR.oneToSix();
		int clockadd = master.directOp(directOpCode, clock);
		master.printState();
		 
		Thread.sleep(1000);
	}
	clock = 0;
	
}



public static void main(String[] args) throws Exception {
	Simulator begin = new Simulator();
	begin.RunIt();
	Thread.sleep(1000);
	begin.RunIt();
	Thread.sleep(1000);
	begin.RunIt();
	Thread.sleep(1000);
	begin.RunIt();
	Thread.sleep(1000);
	begin.RunIt();
	Thread.sleep(1000);
	begin = new Simulator();
	begin.RunIt();
	begin.RunIt();
	begin.RunIt();
	begin.RunIt();
	begin.RunIt();
	
	
}
}