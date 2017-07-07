package manosComponents;

public class ALU extends Register {
	
	protected int E = 0;

public ALU(){
	super();
		
}
public void clearE(){
	this.E = 0;
}
public void complementE(){
	if (this.E == 0){
		this.E = 1;
	}
	if (this.E == 1){
		this.E = 0;
	}
}
public String circulateRight(Register AC){
	//AC (0-14) <-- AC (1-15)// AC that has one to end, gets begininning to end-1
	//AC (15) <-- E
	//E <-- AC (0)
	

	String oneTo15 = AC.binaryS.substring(0, AC.binaryS.length() - 2 );
	
	String cirBin = this.E + oneTo15;
	String cirHex = AC.binary2hex(cirBin);
	
	
	this.E = Integer.parseInt(AC.binaryS.substring(AC.binaryS.length() - 1));
	return cirHex;
	
	
	
}
public String circulateLeft(Register AC) {
	//AC (1-15) <-- AC (0-14)
	//AC (0) <-- E
	//E <-- AC (15)
	
	String zeroTo14 = AC.binaryS.substring(1);
	String cilBin = zeroTo14 + this.E;
	String cilHex = AC.binary2hex(cilBin);
	
	
	this.E = Integer.parseInt(AC.decodeI());
	return cilHex;
	
	
	
}
public int ADD(Register DR,Register AC){
	int carry = 0;
	int length = DR.binaryS.length();
	String binAdd = Integer.toBinaryString(Integer.parseInt(DR.binaryS,2) + Integer.parseInt(AC.binaryS,2));
	
	if(binAdd.length() > length){
	carry = 1;
	binAdd = binAdd.substring(1,length);
	}
	
	String addHex = AC.binary2hex(binAdd);
	AC.setValue(addHex);
	return carry;
	
	
	
}
public String AND(Register DR, Register AC){
	int acValue = Integer.parseInt(AC.binaryS, 2);
	int drValue = Integer.parseInt(DR.binaryS, 2);
	int andValue = acValue & drValue;
	
	String andHex = Integer.toHexString(andValue);
	
	return andHex;
	
	
}	

}
