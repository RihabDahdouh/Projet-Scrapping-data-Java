// Importing necessary Java and Weka libraries for machine learning
package machine_learning;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ConfusionMatrix;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.trees.RandomForest;

// Class representing a decision tree for employment classification
class Decision_Tree_rekrute {
    // J48 decision tree instance variable
    private J48 tree;

    // Method to read data from an ARFF file and return Instances
    public Instances getData(String chemin) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(chemin));
        ArffReader arff = new ArffReader(reader);
        Instances data = arff.getData();

        System.out.println(data.numInstances());
        return data;
    }

    // Method to apply J48 algorithm on training and testing data
    public void J48Algo(Instances dataTrain, Instances dataTest) throws Exception {
        // Creating J48 classifier
        J48 j48Classifier = new J48();

        // Set target class index for both train and test data
        dataTrain.setClassIndex(dataTrain.numAttributes() - 10);
        dataTest.setClassIndex(dataTrain.numAttributes() - 10);

        // Setting options for J48 classifier
        String[] options = new String[1];
        options[0] = "-U";
        J48 tree = new J48();
        tree.setOptions(options);

        // Building J48 decision tree on the training data
        tree.buildClassifier(dataTrain);

        // Evaluating the tree using the training and testing data
        Evalute(tree, dataTrain, dataTest);

        // Storing the trained tree in the instance variable
        this.tree = tree;
    }

    // Method to evaluate the J48 decision tree
    public void Evalute(J48 tree, Instances dataTrain, Instances dataTest) throws Exception {
        System.out.println("Evaluation with Train Data");
        Evaluation eval_rocTrain = new Evaluation(dataTrain);

        // Cross-validate the model on the training data
        eval_rocTrain.crossValidateModel(tree, dataTrain, 10, new Random(1), new Object[]{});
        System.out.println(eval_rocTrain.toSummaryString());

        System.out.println("Evaluation with Test Data");
        Evaluation eval_rocTest = new Evaluation(dataTest);

        // Cross-validate the model on the testing data
        eval_rocTest.crossValidateModel(tree, dataTest, 10, new Random(1), new Object[]{});

        System.out.println(eval_rocTest.toSummaryString());
        System.out.println(eval_rocTest.toMatrixString());
    }

    // Method to apply Random Forest algorithm on training data
    public void RandomForest(Instances dataTrain, Instances dataTest) throws Exception {
        // Set target class index for training data
        dataTrain.setClassIndex(0);

        // Creating Random Forest classifier
        RandomForest forest = new RandomForest();

        // Building Random Forest on the training data
        forest.buildClassifier(dataTrain);

        // Printing the Random Forest model
        System.out.println(forest);
    }

    // Method to test the trained decision tree on new instances
    public void Test(Instances dataTest) {
        try {
            // Classify an instance using the trained tree
            double label = tree.classifyInstance(dataTest.get(1));
            String predLabel = dataTest.classAttribute().value((int) label);

            // Display the predicted label
            System.out.println(predLabel);
        } catch (Exception ex) {
            System.out.println("Error in GUI predireListener" + ex.getMessage());
        }
    }

    // Main method to run the decision tree classification
    public static void main(String[] args) throws Exception {
        Decision_Tree_rekrute classif = new Decision_Tree_rekrute();

        // Loading training and testing data
        System.out.println("Number of instances in train data");
        Instances dataTrain = classif.getData("ScraperOutputRekrute_Training.arff");
        System.out.println("Number of instances in test data");
        Instances dataTest = classif.getData("ScraperOutputRekrute_Testing.arff");

        // Applying J48 algorithm and Random Forest algorithm
        System.out.println("************************** J48 *************************");
        classif.J48Algo(dataTrain, dataTest);
        classif.RandomForest(dataTrain, dataTest);

        // Testing the trained decision tree on new instances
        // classif.Test(dataTest);
    }
}
