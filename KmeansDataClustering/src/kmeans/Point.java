package kmeans;

//This class will store the data for each point
public class Point {
// holds the axis data for each vertex
public double[] data;

//ID for the cluster the point belongs to
public int clusterid = 0;

//Dimension of the meta data
public int dimension;
//Array of distances to each centroid
public double[] clusterDist;

//initializes size
Point(int dimensionPass){
	this.dimension = dimensionPass;
	this.data = new double[dimension];
	
}
//initializes size and number of clusters for clusterDist
Point(int dimensionPass, int clusters){
	this.dimension = dimensionPass;
	this.data = new double[dimension];
	this.clusterDist = new double[clusters];
	
}
//decides the closest centroid and assigns a cluster
public void decideCluster(){
	double shortest = 1000000;
	for(int i = 0; i < clusterDist.length; i++)
	{
		if(clusterDist[i] < shortest)
		{	shortest = clusterDist[i];
			this.clusterid = i;
		}
		
	}
}
public int decideNeighborCluster(){
	double shortest = 1000000;
	int neighborId = 0;
	for(int i = 0; i < clusterDist.length; i++)
	{	if( i != this.clusterid){
		
	
		if(clusterDist[i] < shortest)
		{	shortest = clusterDist[i];
			neighborId = i;
		}
			
		}
		
	}
	return neighborId;
}


}
