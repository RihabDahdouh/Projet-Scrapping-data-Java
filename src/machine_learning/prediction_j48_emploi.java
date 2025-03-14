// Import necessary libraries for the machine_learning package and Weka toolkit
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

// Class for making predictions using J48 algorithm for employment-related data
class prediction_j48_emploi {
    // J48 tree variable to store the trained classifier
    private J48 tree;

    // Method to read ARFF file and return instances
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

        // Set Target Class
        // For training Data
        // Classify with work as the target
        dataTrain.setClassIndex(dataTrain.numAttributes() - 7);

        // For Test data
        dataTest.setClassIndex(dataTrain.numAttributes() - 7);

        // Setting options for J48
        String[] options = new String[1];
        options[0] = "-U";
        J48 tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(dataTrain);

        // Evaluate the J48 model
        Evalute(tree, dataTrain, dataTest);

        // Save the trained tree
        this.tree = tree;
    }

    // Method to evaluate the J48 classifier
    public void Evalute(J48 tree, Instances dataTrain, Instances dataTest) throws Exception {
        // Evaluate the model on training data
        Evaluation eval_rocTrain = new Evaluation(dataTrain);
        eval_rocTrain.crossValidateModel(tree, dataTrain, 10, new Random(1), new Object[] {});

        // Evaluate the model on testing data
        Evaluation eval_rocTest = new Evaluation(dataTest);
        eval_rocTest.crossValidateModel(tree, dataTest, 10, new Random(1), new Object[] {});
    }

    // Method to apply Random Forest algorithm on training data
    public void RandomForest(Instances dataTrain, Instances dataTest) throws Exception {
        dataTrain.setClassIndex(0);
        RandomForest forest = new RandomForest();
        forest.buildClassifier(dataTrain);
    }

    // Method to test the trained model on a specific test instance
    public void Test(Instances dataTest) {
        try {
            // Classify the instance using the trained tree
            double label = tree.classifyInstance(dataTest.get(1));
            String predLabel = dataTest.classAttribute().value((int) label);
            System.out.println(predLabel);
        } catch (Exception ex) {
            System.out.println("Error in GUI predireListener" + ex.getMessage());
        }
    }

    // Method to make predictions on a given instance using the trained J48 model
    public void predict(Instance predict) throws Exception {
        double predictions;
        System.out.println("______________La prédiction est : ______________");

        // Classify the given instance using the trained tree
        Instance instance = predict;
        predictions = tree.classifyInstance(instance);

        System.out.println("____________________________________________________________________________________________________________");
        System.out.println("La donnée appartient au metier : " + predict.attribute(7).value((int) predictions));
    }

    // Main method to demonstrate the usage of the J48 algorithm for prediction
    public static void main(String[] args) throws Exception {
        // Create an instance of the prediction_j48_emploi class
        prediction_j48_emploi classif = new prediction_j48_emploi();

        // Load training and testing data from ARFF files
        Instances dataTrain = classif.getData("ScraperOutputEmploi_Training.arff");
        Instances dataTest = classif.getData("ScraperOutputEmploi_Testing.arff");

        // Select a specific instance from the testing data for prediction
        Instance predict = dataTest.instance(5);

        System.out.println("************************** J48 *************************");

        // Apply J48 algorithm on training and testing data
        classif.J48Algo(dataTrain, dataTest);

        // Make predictions using the trained J48 model
        classif.predict(predict);
    }
}
