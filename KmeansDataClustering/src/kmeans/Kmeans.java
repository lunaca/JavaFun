package kmeans;

import java.io.*;
import java.util.Arrays;
//This class will overall controll the Kmeans algorithm
public class Kmeans {
	public DataMaster dataSet;
	public double SSE = 0.0;//initialize the SSE value for the algorithm
	public double prevSSE = 500000000;//set the previous value to a nun-null value
	public double size = 0;
	
	Kmeans() throws IOException{
		super();
	
	}
	
	public static void main(String args[]) throws IOException{
		//First take in the meta data for the algorithm passed through the command line
		
		//StringBuilder totalSSE = new StringBuilder();//This a string used to accumulate the SSE's for box plot entry
		String F = args[0]; //filename
		int K = Integer.parseInt(args[1]);//max number of klusters :)
		int I = Integer.parseInt(args[2]);//max number of iterations
		double T = Double.parseDouble(args[3]);//Convergence value
		int R = Integer.parseInt(args[4]);//Number of runs
		
		String fullFileName = "src/kmeans/" + F ;
		BufferedReader brTest = new BufferedReader(new FileReader(fullFileName));
	    String text = brTest.readLine();   
	   String[] strArray = text.split(" ");
	   double size = Double.parseDouble(strArray[0]);
	   
	   
	    double maxClusters = Math.sqrt(size/2);
		double randMean = 0;
		double smartMean = 0;
		double initRandMean = 0;
		double initSmartMean = 0; 
		double SCmean = 0;
		double CHmean = 0;
		int clusterCount = 2;
		
		

		
		//This will test each number of clusters and print the average Silhoutte Coeffeciant for each
		
		
				for (int y = 2; y <= maxClusters; y++){
					//this loop will test each number of clusters
					CHmean = 0;
					SCmean = 0;
					//System.out.println("Runs with cluster count of " + clusterCount);
					for(int k = 0; k < 5; k++){
						//This loop will control the number of runs	for averaging
					
					//System.out.println("Run # " + k);
					//System.out.println("-----");
					//Create new instance of Kmeans
					Kmeans kMeans = new Kmeans();
					//Initialize kMeans with meta data and new centroids
					kMeans.dataSet = new DataMaster(F, clusterCount);
					kMeans.dataSet.smartCentroids();
					//This loop will control the number of iterations
					for(int i = 0; i<= I;i++){
						if( i > 0){
							kMeans.prevSSE = kMeans.SSE; 
						}
						if(i == 1) {
							double isse2 = kMeans.dataSet.findSSE();
							//System.out.println("initial SSE = " + isse2 );
							initSmartMean += isse2 / R;
						}
						kMeans.dataSet.setCentroidDif();
						kMeans.dataSet.setClusters();
						kMeans.SSE = kMeans.dataSet.findSSE();
						

						double convergence = (kMeans.prevSSE - kMeans.SSE) / kMeans.prevSSE;
						if(convergence < T && i != 0 ){
							//System.out.println(  i + " Iterations finalizing with  SSE = " + kMeans.SSE);
							smartMean += kMeans.SSE / R ;
							break;
						}
						kMeans.dataSet.findNewCentroids();
					}
					double currentCH = kMeans.dataSet.calculateCH();
					double currentSC = kMeans.dataSet.calculateSC();
					CHmean += currentCH;
				
					SCmean += currentSC;
					}
					CHmean = CHmean/5;
					SCmean = SCmean/5;
					System.out.println("CH Index of " + CHmean + " for " + clusterCount + " clusters");
					//System.out.println("Mean Silhoutte of " + SCmean + "for " + clusterCount + " clusters");
					clusterCount++;
				}
				
				
//				System.out.println("Random Centroids: __________________________________");
//				System.out.println("Average Inital SSE: " + initRandMean);
//				System.out.println("Average Final SSE: " + randMean);
//				
//				System.out.println("Random Partition Centroids: __________________________________");
//				System.out.println("Average Inital SSE: " + initSmartMean);
//				System.out.println("Average Final SSE: " + smartMean);
				
				

		
	}


}