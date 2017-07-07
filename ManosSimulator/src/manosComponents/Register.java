package manosComponents;

import java.math.BigInteger;
//this class will make our storing of values easy with the registers!
public class Register extends java.lang.Object {
protected String name;	
protected String hexS ;// hex value stored
protected String binaryS;// binary representation
protected int decimalInt;// decimal representation
protected int bitSize;// size of bits of register, 16 for data, 12 for address

//initializer, initializes values to 0 and size to 16
public Register(){
	
this.hexS = "0000";
this.binaryS = "0000000000000000";
this.decimalInt = 0;
this.bitSize = 16;

}
//useful for setting values, and keeping all of the representations the same,
//with correct size as well
public void setValue(String hexValue){
	 String preFix = hexValue;
	 int regHexSize = this.bitSize/4;
	 Integer length = hexValue.length();
	    if (length < (regHexSize)) {
	        for (int t = 0; t < regHexSize - length; t++) {
	            preFix = "0" + preFix;
	        }
	    }
	    if (length > (regHexSize)){
	    		 preFix = preFix.substring(1);        
	    }
	    this.hexS = preFix;
	    this.binaryS = hexToBin(this.hexS);
	    this.decimalInt = bin2Dec(this.binaryS);
	
}
//converts hex string to binary string
public String hexToBin(String s) 
{
    String preBin = new BigInteger(s, 16).toString(2);
    Integer length = preBin.length();
    if (length < this.bitSize) {
        for (int t = 0; t < this.bitSize - length; t++) {
            preBin = "0" + preBin;
        }
    }
    return preBin;
}
//initializer where the size and initial value can be specified
public Register(String name, int size, String initialHex){
this.name = name;
this.hexS = initialHex;
this.bitSize = size;
this.binaryS = hexToBin(hexS);

int decimalValue = Integer.parseInt(this.binaryS, 2);
this.decimalInt = decimalValue;
}

//converts binary string to int decimal representation
public int bin2Dec (String binaryValue){
	int decimalValue = Integer.parseInt(binaryValue, 2);
	return decimalValue;
}
//converts binary string to hex
public String binary2hex(String binaryValue){
	int decimal = Integer.parseInt(binaryValue,2);
	String hexStr = Integer.toString(decimal,16);
	return hexStr;
}
// loads value in passed register to THIS register
public void LD1 (Register sender){
	System.out.println("Register  " + this.name + " has a LD value of 1");
	this.setValue(sender.hexS);
	
}
//increments the value in this register
public void inc(){
	int value = Integer.parseInt(this.hexS, 16);
	value++;
	setValue(Integer.toHexString(value));
	System.out.println(this.name + " was incremented");
}
//clears the register
public void clear()
{
	this.hexS = "0000";
	this.binaryS = "0000000000000000";
	this.decimalInt = 0;
}
public void printValue(){
	System.out.print(this.hexS + "  ");
}
public String complement(){
	int flip = ~this.decimalInt;
	String hexComp = Integer.toHexString(flip);
	return hexComp;
}
public String decodeOpCode(){

	String opCode = this.hexS.substring(0,1);
	
	return opCode;
	
}
public String decodeI(){
	String I = this.binaryS.substring(0,1);
	
	return I;
	
}
public String cutOp()
{
	String op = this.hexS.substring(1,4);
	
	return op;
}
public String oneToSix(){
	String code = this.binaryS.substring(1,4);
	String hexRep = binary2hex(code);
	System.out.println(hexRep);
	return hexRep;
}


}

