package manosComponents;
import java.util.Random;
import javax.swing.*;
public class CPU {
	//The Mano’s computer (16 bit system) contains Registers: AR, PC, DR, AC, IR, TR, E and
	//Memory (4096*16). You need to do the following things:
	protected Register AR;
	protected Register PC;
	protected Register DR;
	protected Register AC;
	protected Register IR;
	
	protected Memory memunit; 
	protected int SC;
	protected ALU alu;
	public BusControl bus;
	public class BusControl {
		
		protected int S0;
		protected int S1;
		protected int S2;

		
		BusControl(){
			this.S0 = 0;
			this.S1 = 0;
			this.S2 = 0;
		}
		
		public void printSelectors(){
			System.out.println("S0 is set to " + this.S0);
			System.out.println("S1 is set to " + this.S1);
			System.out.println("S2 is set to " + this.S2);
		}
		public void onDaBus(int S0, int S1, int S2,  Register LD){
			this.S0 = S0;
			this.S1 = S1;
			this.S2 = S2;
			String selectString = Integer.toString(S0) + Integer.toString(S1) + Integer.toString(S2);
			
			if(selectString.equals("001")){
			System.out.println("AR is sending it's value to  " + LD.name);
			printSelectors();
			LD.LD1(AR);
			
			}
			
			if(selectString.equals("010")){
				System.out.println("PC is sending it's value to  " + LD.name);
				printSelectors();
				LD.LD1(PC);
				
				}
			if(selectString.equals("011")){
				System.out.println("DR is sending it's value to  " + LD.name);
				printSelectors();
				LD.LD1(DR);
				
				}
			if(selectString.equals("100")){
				System.out.println("AC is sending it's value to  " + LD.name);
				printSelectors();
				LD.LD1(AC);
				
				}
			if(selectString.equals("101")){
				System.out.println("IR is sending it's value to  " + LD.name);
				printSelectors();
				LD.LD1(IR);
				
				}
			if(selectString.equals("110")){
				System.out.println("TR is sending it's value to  " + LD.name);
				printSelectors();
				
				}
			if(selectString.equals("111")){
				System.out.println("Memory is sending it's value to  " + LD.name);
				String readValue = memunit.readFrom(AR.hexS);
				System.out.println("Memory RD = 1, Reading value: "+ readValue + ",  at location  " + AR.hexS);
				printSelectors();
				LD.setValue(readValue);
				
				}
		
		}
			
			public void onDaBusmem(int S0, int S1, int S2, Memory mem){
				this.S0 = S0;
				this.S1 = S1;
				this.S2 = S2;
				String selectString = Integer.toString(S0) + Integer.toString(S1) + Integer.toString(S2);
				
				if(selectString.equals("001")){
					System.out.println("AR is sending it's value to Memory Address " + AR.hexS);
				printSelectors();
				mem.writeTo(AR.hexS, AR.hexS);
				
				}
				
				if(selectString.equals("010")){
					System.out.println("PC is sending it's value to Memory Address " + AR.hexS);
					printSelectors();
					mem.writeTo(AR.hexS, PC.hexS);
					
					}
				if(selectString.equals("011")){
					System.out.println("DR is sending it's value to Memory Address " + AR.hexS);
					printSelectors();
					mem.writeTo(AR.hexS, DR.hexS);
					
					}
				if(selectString.equals("100")){
					System.out.println("AC is sending it's value to Memory Address " + AR.hexS);
					printSelectors();
					mem.writeTo(AR.hexS, AC.hexS);
					
					}
				if(selectString.equals("101")){
					System.out.println("IR is sending it's value to Memory Address " + AR.hexS);
					printSelectors();
					mem.writeTo(AR.hexS, IR.hexS);
					
					}
				if(selectString.equals("110")){
					System.out.println("TR is sending it's value to Memory Address " + AR.hexS);
					printSelectors();
					}
				
	
			}
			
	}
	
	
//	                 IR  AC    DR   PC  AR M[AR] E
//	Initial Values 1200 0001 00C7 035 200 0400   0
	public CPU(){
		Random rand = new Random();
		this.IR = new Register("IR", 16, "1200");
	
		this.AC = new Register("AC", 16, "0001");
		
		this.DR = new Register("DR", 16, "00C7");
		String randPC = "";
		for (int j = 0; j < 3; j++)
		{
			randPC += (String.format("%x", rand.nextInt(16)));
		}
		
		this.PC = new Register("PC", 12, randPC);
		
		this.AR = new Register("AR", 12,"200");
		
		this.memunit = new Memory();
		this.alu = new ALU();
	
		this.SC = 0;
		this.bus = new BusControl();
		
		printState();
		
	}
	public void printState(){
		System.out.println(" IR     AC   DR    PC   AR   M[AR]  E");
		IR.printValue();
		AC.printValue();
		DR.printValue();
		PC.printValue();
		AR.printValue();
		System.out.print(memunit.readFrom(AR.hexS) + "    ");
		System.out.println(alu.E);
	}
	public int directOp(String opCode, int clock){
		int plusclock = 0; //this puts the clock to T5 if there is another step 
		switch(opCode){
		
		case "0" : 
			//AND
			if(clock == 4){
				bus.onDaBus(1,1,1,DR);
				plusclock = 1;
			}
			if(clock == 5){
			String andHex = alu.AND(DR, AC);
			AC.setValue(andHex);
			System.out.println("AC LD = 1 loading AND value from the ALU");
			}
			break;
		case "1" :
			//ADD
			if(clock == 4){
				bus.onDaBus(1,1,1,DR);
				plusclock = 1;
			}
			if(clock == 5){
			int carry = alu.ADD(DR, AC);
			alu.E = carry;
			
			
			}
			break;
		case "2" :
			//LDA
			if(clock == 4){
				bus.onDaBus(1,1,1,DR);
				plusclock = 1;
			}
			if(clock == 5){
				AC.setValue(DR.hexS);
				
			}
			break;
		case "3" : 	
			if(clock == 4){
				bus.onDaBusmem(1,0,0, memunit);	
			}
			break;	
		case "4" :			//BUN

			if(clock == 4){
				bus.onDaBus(1, 1, 1, PC);
				
			}
			break;
		case "5" ://BSA 
			if(clock == 4){
				bus.onDaBusmem(0,1,0,memunit);
				AR.inc();
				plusclock = 1;
			}
			if(clock == 5){
				bus.onDaBus(0, 0, 1, PC);
			}
			
			break;
		case "6" : 
			if(clock == 4){
				bus.onDaBus(1,1,1,DR);
				plusclock = 1;
			}
			if(clock == 5){
				DR.inc();
				plusclock = 1;
				
				}
			if(clock == 6){
				bus.onDaBusmem(0, 1, 1, memunit);
			}
			break;
			//ISZ
		default:
			
		
		}
		return plusclock;
	}
	public void regIns(String opCode){
		
		switch (opCode){
		
		case "001" :
			System.out.println("computer Halted");
			
			
			break;
		case "002" :
			if(alu.E == 0){
				PC.inc();
				}
			break;
		case "004" : 
			if(AC.hexS.equals("0000")){
			PC.inc();
			}
			break;
		case "008" :
			String neg1 = AC.decodeI();
			if(neg1.equals("1")){
			PC.inc();
				
			}
			break;
		case "010" : 
			String neg0 = AC.decodeI();
			if(neg0.equals("0")){
			PC.inc();
			}
			break;
		case "020" : 
			AC.inc();
			break;
		case "040" :
			AC.setValue(alu.circulateLeft(AC));
			
		case "080" :
			AC.setValue(alu.circulateRight(AC));
			
		case "100" :
			alu.complementE();
			break;
		case "200" : 
			String comp = AC.complement();
			AC.setValue(comp);
			break;
		case "400" :
			alu.E= 0;
			break;
			
		case "800" : 
			AC.clear();
			break;
		
		
		default: 
		
		
		}
		
	}
	
}
