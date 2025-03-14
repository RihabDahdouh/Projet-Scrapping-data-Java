package machine_learning;

import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class naiveBayes_emploi {
  public static void main(String[] args) throws Exception {
    // Load training data from ARFF file
    ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("ScraperOutputEmploi_Training.arff");
    Instances train = source1.getDataSet();
    
    // Set class attribute index if not provided in the data
    if (train.classIndex() == -1)
      train.setClassIndex(train.numAttributes() - 6);

    // Load test data from ARFF file
    ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("ScraperOutputEmploi_Testing.arff");
    Instances test = source2.getDataSet();
    
    // Set class attribute index if not provided in the data
    if (test.classIndex() == -1)
      test.setClassIndex(test.numAttributes() - 6);

    // Create NaiveBayes classifier
    NaiveBayes naiveBayes = new NaiveBayes();
    
    // Build the NaiveBayes classifier using the training data
    naiveBayes.buildClassifier(train);

    // Evaluate the classifier on the training data
    Evaluation eval_rocTrain = new Evaluation(train);
    eval_rocTrain.crossValidateModel(naiveBayes, train, 10, new Random(1), new Object[] {});
    System.out.println("Evaluation with Training Data");
    System.out.println(eval_rocTrain.toSummaryString());

    // Evaluate the classifier on the test data
    System.out.println("Evaluation with Test Data");
    Evaluation eval_rocTest = new Evaluation(test);
    eval_rocTest.crossValidateModel(naiveBayes, test, 10, new Random(1), new Object[] {});
    System.out.println(eval_rocTest.toSummaryString());
    System.out.println(eval_rocTest.toMatrixString());

    // Print the class value of a specific instance in the test data
    System.out.println("Class value of instance 8 in test data: " + test.instance(8).stringValue(4));
  }
}
