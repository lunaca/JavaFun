package manosComponents;

import java.util.Arrays;
import java.util.Random;

public class Memory {

	public int WR;
	public int RD;
	public String data[] = new String[4096];
	
	public Memory()
	{
		WR = 0;
		RD = 0;
		Random rand = new Random();
		for (int i = 0; i < 4096; i++) 
		{
			String temp = String.format("%x", rand.nextInt(15));
			if (temp.charAt(0) == '7')
			{
				int temp2 = rand.nextInt(12);
				if (temp2 == 0)
				{
					temp+= ("001");
				}
				else if (temp2 == 1)
				{
					temp+=("002");
				}
				else if (temp2 == 2)
				{
					temp+=("004");
				}
				else if (temp2 == 3)
				{
					temp+=("008");
				}
				else if (temp2 == 4)
				{
					temp+=("010");
				}
				else if (temp2 == 5)
				{
					temp+=("020");
				}
				else if (temp2 == 6)
				{
					temp+=("040");
				}
				else if (temp2 == 7)
				{
					temp+=("080");
				}
				else if (temp2 == 8)
				{
					temp+=("100");
				}
				else if (temp2 == 9)
				{
					temp+=("200");
				}
				else if (temp2 == 10)
				{
					temp+=("400");
				}
				else
				{
					temp+=("800");
				}
			}
			else
			{
				for (int j = 0; j < 3; j++)
				{
					temp += (String.format("%x", rand.nextInt(16)));
				}
			}
			data[i] = temp;
		} 
}
	public String readFrom(String hexAddress){
		String readValue = "";
		int decAddress = Integer.parseInt(hexAddress, 16);
		readValue = data[decAddress];
		
		return readValue;
		
	}
	public void writeTo(String hexAddress, String writeValue){
		
		int decAddress = Integer.parseInt(hexAddress, 16);
		data[decAddress] = writeValue;
		
		
	}
	public static void main(String[] args) {
		Memory mem = new Memory();
		System.out.println(Arrays.toString(mem.data));
		System.out.println(mem.readFrom("FF1"));
}
}