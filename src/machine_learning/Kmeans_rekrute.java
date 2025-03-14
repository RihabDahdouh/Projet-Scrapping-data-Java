package machine_learning;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class Kmeans_rekrute {
  public Kmeans_rekrute() throws Exception {
    // Initialize BufferedReaders for reading training and test data ARFF files
    BufferedReader breader = null;
    BufferedReader breader_test = null;
    
    // Read the training and test data from ARFF files
    breader_test = new BufferedReader(new FileReader("ScraperOutputRekrute_Testing.arff"));
    breader = new BufferedReader(new FileReader("ScraperOutputRekrute_Training.arff"));
    
    // Create Instances for training and test data
    Instances Train = new Instances(breader);
    Instances Test = new Instances(breader_test);

    // Create and configure SimpleKMeans clustering algorithm
    SimpleKMeans kMeans = new SimpleKMeans();
    kMeans.setSeed(10);
    kMeans.setPreserveInstancesOrder(true);
    kMeans.setNumClusters(5);
    
    // Build clustering model using the training data
    kMeans.buildClusterer(Train);
    
    // Evaluate the clustering model on the training data
    ClusterEvaluation eval = new ClusterEvaluation();
    eval.setClusterer(kMeans);
    System.out.println("Clusters made out of Training set");
    eval.evaluateClusterer(Train);
    System.out.println(eval.clusterResultsToString());
    System.out.println("# of clusters: " + eval.getNumClusters());
    breader.close();
    
    // Evaluate the clustering model on the test data
    ClusterEvaluation eval_test = new ClusterEvaluation();
    eval_test.setClusterer(kMeans);
    System.out.println("Clusters made out of Test set");
    eval_test.evaluateClusterer(Test);
    System.out.println(eval_test.clusterResultsToString());
    System.out.println("# of clusters: " + eval_test.getNumClusters());
    breader_test.close();
  }

  public static void main(String[] args) throws Exception {
    // Create an instance of the Kmeans_emploi class to run the clustering process
    new Kmeans_emploi();
  }
}
