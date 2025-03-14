// Import necessary libraries
package machine_learning;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

// Define the class
public class prediction_naiveBayes_mjob {
  // Main method, entry point of the program
  public static void main(String[] args) throws Exception {

    // Load training data from ARFF file
    ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("ScraperOutputMjob_Training.arff");
    Instances train = source1.getDataSet();

    // Set the class attribute if not provided in the data format
    if (train.classIndex() == -1)
      train.setClassIndex(train.numAttributes()-5);

    // Load testing data from ARFF file
    ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("ScraperOutputMjob_Testing.arff");
    Instances test = source2.getDataSet();

    // Set the class attribute if not provided in the data format
    if (test.classIndex() == -1)
      test.setClassIndex(test.numAttributes()-5);

    // Create a Naive Bayes classifier model
    NaiveBayes naiveBayes = new NaiveBayes();
    naiveBayes.buildClassifier(train);

    // Perform cross-validation on the training data
    Evaluation eval_rocTrain = new Evaluation(train);
    eval_rocTrain.crossValidateModel(naiveBayes, train, 10, new Random(1), new Object[] {});

    // Perform cross-validation on the testing data
    Evaluation eval_rocTest = new Evaluation(test);
    eval_rocTest.crossValidateModel(naiveBayes, test, 10, new Random(1), new Object[] {});

    // Make a prediction for a specific instance in the testing data
    double predictions;
    System.out.println("______________La prédiction est : ______________");

    // Get the 6th instance from the testing data (index 5)
    Instance instance = test.instance(5);
    // Classify the instance using the trained Naive Bayes model
    predictions = naiveBayes.classifyInstance(instance);

    // Print the predicted class value
    System.out.println("____________________________________________________________________________________________________________");
    System.out.println("La donnée appartient à l'expérience : " + instance.attribute(7).value((int) predictions));
  }
}
