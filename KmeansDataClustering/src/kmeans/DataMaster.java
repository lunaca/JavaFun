package kmeans;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

//This class is the Master of all vertice data and manipulations of such 
public class DataMaster {

	public int size;
	public int dimension;
	public Point[] oldPoints;
	public Point[] points;		//All of the points in the data set
	public double[] newmaxes;
	public double[] maximums;	//Maximums of each axis
	public double[] minimums;	//Minimums of each axis
	public Point[] zScorePoints;
	public double[] clusterPoints;
	
	public Point[] centroids;		//Array of centroid points
	public int numClusters;			//K

	DataMaster(String filename, int clusters){
		// first set up the data set from filename passed through command line

		String fullFileName = "src/kmeans/" + filename ;


		try{	
			FileInputStream fstream = new FileInputStream(fullFileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int pointCount = 0;
			while ((strLine = br.readLine()) != null)   {

				int i = 0;
				String[] tokens = strLine.split(" ");
				//Read in the first line and initialize anything with size and dimension 
				if (pointCount == 0)
				{	this.numClusters = clusters;
				this.size = Integer.parseInt(tokens[0]);
				this.dimension = Integer.parseInt(tokens[1]);
				this.oldPoints = new Point[this.size + 1];
				for(int p = 0; p < oldPoints.length ; p++){
					oldPoints[p] = new Point(this.dimension, clusters);
				}
				}
				//Assign point data to point array
				for (i=0; i < tokens.length; i++ ){
					if(pointCount != 0){
						this.oldPoints[pointCount].data[i] = Double.parseDouble(tokens[i]);
					

					}	
				}
				
				pointCount++;
			}



			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}



		//here we will find the maximums and minimums for normalization

		this.maximums = new double[this.dimension];
		for (int m = 0; m < maximums.length; m++){
			maximums[m] = 0.0;
		}
		for(int i = 0; i < oldPoints.length; i++){
			for(int j = 0; j < maximums.length; j++){
				if(maximums[j] < oldPoints[i].data[j]){
					maximums[j] = oldPoints[i].data[j];
				}
			}
		}
	
		
		this.minimums = new double[this.dimension];
		for (int m = 0; m < minimums.length; m++){
			minimums[m] = 1000; // set so the minimum values won't be greater than!
		}
		for(int i = 1; i < oldPoints.length; i++){
			for(int j = 0; j < minimums.length; j++){
				if(minimums[j] > oldPoints[i].data[j]){
					minimums[j] = oldPoints[i].data[j];
					
				}
			}
		}
		

		//now we will normalize the data using the min max method!

		this.points = new Point[oldPoints.length];
		for(int p = 0; p < oldPoints.length ; p++){
			points[p] = new Point(this.dimension, clusters);
		}
		double normMax = 1;
		double normMin = 0;
		for(int i = 1; i < oldPoints.length; i++){
			for(int j = 0; j < minimums.length; j++)
			{
				points[i].data[j] = (oldPoints[i].data[j] - minimums[j])/(maximums[j]-minimums[j]);
			}
		}

		
	
		
		
		
		

		//now we will also normalize the data for with the zscore method as well!
		//first we will find the mean of the data, and then find the standard deviation!
		double[] means = new double[this.dimension];
		for(int i = 0; i < this.dimension; i++){
			means[i]= 0.0;
		}
		for(int i = 0; i < points.length; i++){
			for(int j = 0; i < this.dimension; i++){
				means[i] += points[i].data[j]/points.length; // divide everytime so we dont overload the double
			}
		}
	

		//now we will find the sd

		double[] SDs = new double[this.dimension];
		for(int i = 0; i < this.dimension; i++){
			SDs[i]= 0.0;
		}
		for (int i = 0; i < points.length; i++)
		{
			for(int j = 0; j < this.dimension; j++){
				SDs[j] += Math.pow((points[i].data[j] - means[j]), 2) / points.length;// divide everytime to not overload
			}

		}
		double[] standardDeviations = new double[this.dimension];
		for( int i = 0; i < this.dimension; i++){
			standardDeviations[i] = Math.sqrt(SDs[i]);
		}



		this.zScorePoints = new Point[oldPoints.length];
		for(int p = 0; p < oldPoints.length ; p++){
			zScorePoints[p] = new Point(this.dimension, clusters);
		}

		for(int i = 1; i < oldPoints.length; i++){
			for(int j = 0; j < minimums.length; j++)
			{
				zScorePoints[i].data[j] = (oldPoints[i].data[j] - means[j])/standardDeviations[j];
			}
		}

		
	}



	public void randCentroids(){
		
		//first calculate the random maximums for each value
		
		this.newmaxes = new double[this.dimension];
		for (int m = 0; m < newmaxes.length; m++){
			newmaxes[m] = 0.0;
		}
		for(int i = 0; i < points.length; i++){
			for(int j = 0; j < newmaxes.length; j++){
				if(newmaxes[j] < oldPoints[i].data[j]){
					newmaxes[j] = oldPoints[i].data[j];
				}
			}
		}

		//now that we have the maximum values of each attribute, we will create n cluster centroids 
		this.centroids = new Point[numClusters];
		for(int p = 0; p < centroids.length ; p++){
			centroids[p] = new Point(this.dimension);
		}
		Random r = new Random();
		for(int i = 0; i < centroids.length; i++){
			for(int j = 0; j < this.dimension; j++){

				double randomValue = newmaxes[j] *  r.nextDouble();
				
				centroids[i].data[j] = randomValue;

			}

		}
		
	}
	public void smartCentroids(){
		//this function will use random partitioning to calculate the centroids

		// first we will assign all points to random clusters
		randClusters();
		
		//Then we will simply calculate the first centroids from those clusters
		//first we will initialize the centroids as blank points
		this.centroids = new Point[this.numClusters];
		for(int p = 0; p < centroids.length ; p++){
			centroids[p] = new Point(this.dimension);
		}
		//Then just calculate them! 
		findNewCentroids();
		
		
		
		
	}

	//This function will calculate the distance between two points regardless of dimension
	//Based on Euclidian distance
	
	public double calcDistance(double[] origin, double[] center) {
		double diff_square_sum = 0.0;
		for (int i = 0; i < origin.length; i++) {
			diff_square_sum += (origin[i] - center[i]) * (origin[i] - center[i]);
			
		}
		return Math.sqrt(diff_square_sum);
	}

	public double specialCalcDistance(double[] origin, double[] center) {
		double diff_square_sum = 0.0;
		for (int i = 0; i < origin.length; i++) {
			diff_square_sum += (origin[i] - center[i]) * (origin[i] - center[i]);
			System.out.println("Distance from data i to center i is " + origin[i] + " -  " + center[i]);
		}
		return Math.sqrt(diff_square_sum);
	}
	//This function calculates the clusterDist for each point
	public void setCentroidDif(){

		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < centroids.length; j++){
				points[i].clusterDist[j] = calcDistance(points[i].data, centroids[j].data);


			}	
		}

	}
	public void randClusters(){
		//This function assists in making all the point's clusters random for initialization
		Random t = new Random();
		for(int i = 0; i < this.size; i++){
			int randomValue =  t.nextInt(this.numClusters);
			points[i].clusterid = randomValue;
			
		}
		
	}
	//This will set the clusterID for each point
	public void setClusters(){
		for(int i = 0; i < this.size; i++){
			points[i].decideCluster();

		}
	}
	//This function will calculate the SSE for a data set
	public double findSSE(){
		double SSE = 0.0;

		for(int i = 0; i < this.numClusters; i++){
			for(int j = 0; j < this.size; j++){
				if (points[j].clusterid == i){
					SSE += Math.pow(points[j].clusterDist[points[j].clusterid],2);
				}
			}
		}
		return SSE;

	}
	//This will take the mean of the vertices in each cluster and create new centroids
	public void findNewCentroids(){
		double[][] sumPoints = new double[numClusters][this.dimension];
		for (int m = 0; m < sumPoints.length; m++){
			for(int j = 0; j < this.dimension; j++){
				sumPoints[m][j]= 0.0;
			}
		}

		this.clusterPoints = new double[numClusters];
		for(int i = 0; i < this.numClusters; i++){
			for(int j = 0; j < this.size; j++)
			{
				if(points[j].clusterid == i){
					for(int k = 0; k < this.dimension; k++){
						sumPoints[i][k] += points[j].data[k];
					}
					clusterPoints[i]++;
				}
			}	
		}

		for (int m = 0; m < sumPoints.length; m++){
			
			for(int j = 0; j < this.dimension; j++){
				if(sumPoints[m][j] != 0 && clusterPoints[m] != 0){
				this.centroids[m].data[j] = sumPoints[m][j]/clusterPoints[m];
				}
				
				
			}
		}

	}
	
	public double calculateCH(){
		double CH = 0.0;
		double SSb = 0.0;
		double SSw = 0.0;
		Point grandCentroid = new Point(this.dimension);
		for (int h = 0; h < this.dimension; h++)
		{
			grandCentroid.data[h] = 0.0;
		}
		//first calculate within cluster variance
		for(int i = 0; i < this.numClusters; i++){
			for(int j = 0; j < this.size; j++){
				if (points[j].clusterid == i){
					SSw += Math.pow(points[j].clusterDist[points[j].clusterid],2);
				}
			}
		}
		
		
		//then calculate between cluster variance
		
		//first calculate the clusters grand Centroid
		for(int i = 1; i < this.size; i++){
			for(int j = 0; j < this.dimension;j++){
				grandCentroid.data[j] += (points[i].data[j] / (this.size - 1));
			}
		}
		//then calculate the distance of each centroid to the grand centroid
		for(int i = 0; i < centroids.length; i++){
			SSb += calcDistance(centroids[i].data, grandCentroid.data);
			
			
			
		}
		
		double temp1 = SSb/SSw;
		
		double temp2 = (this.size - this.numClusters)/(this.numClusters - 1);
		CH = temp1 * temp2;
		
		
		return CH;
		
	}
	
	
public double calculateSC(){// this function will calculate the Silhouettes coefficient for the number of clusters
	double SC = 0.0; //this will be the sum of all of the point's values when plugged into the formula
	
	//Steps:
	//Do this for all points 
	for(int i = 1; i < points.length; i++)
	{
		double meanCloseDist = 0;
		double meanFarDist = 0;
		
		int neighborid = points[i].decideNeighborCluster();
		double clusterPoints1 = this.clusterPoints[points[i].clusterid];
		double clusterPoints2 = this.clusterPoints[neighborid];
		//for each point, calculate the mean distance to all the other points in its cluster
		for(int j = 1; j < points.length; j++)
			
		{
			if ( points[j].clusterid == points[i].clusterid  )
			{
				meanCloseDist += calcDistance(points[i].data, points[j].data);
				
			}
		
		}
		meanCloseDist = meanCloseDist/(clusterPoints1 - 1);
		
		
		for(int j = 1; j < points.length; j++)
			//Also calcualate the mean distance from it to all the other points in the neighboring cluster
		{
			if ( points[j].clusterid == neighborid  )
			{
				meanFarDist += calcDistance(points[i].data, points[j].data);
				
			}
		}
		meanFarDist = meanFarDist/(clusterPoints2 - 1);
		
		
		
		SC += (meanFarDist - meanCloseDist) / Math.max(meanCloseDist, meanFarDist);
		
		
	}
	SC = SC /this.size;
	
	
	
	return SC;
	
}
}
