package nearsoft.academy.bigdata.recommendation;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.*;
import java.util.*;

/**
 * Created by AMDA on 29/03/2017.
 */
public class MovieRecommender {

    //Recommender
    DataModel model;
    UserSimilarity similarity;
    UserNeighborhood neighborhood;
    UserBasedRecommender recommender;

    //Data Management
    TransformData transformData;
    ManageList manageList;

    String filePath;

    public MovieRecommender(String filePath){
        //Transform Data With the path of output file
        transformData = new TransformData();

        transformData.setOutputFile("C:/datos/moviesOutput.txt");

        //Function who calls the convertion of an input file
        transformData.transformToPreferenceFile(filePath);

        //Assign result Maps to class variable
        manageList = transformData.manageList;

        //Initializing model with the output file
        try {
            model = new FileDataModel(new File(transformData.filenameOutput));
        }
        catch (IOException e){
            System.out.println(e);
        }

        this.filePath = filePath;
    }

    public long getTotalReviews() {
        return manageList.totalIndexes;
    }

    public long getTotalProducts(){
        try{
            return (long)model.getNumItems();
        }
        catch (TasteException e){
            System.out.println(e);
        }
        return 0;
    }

    public long getTotalUsers(){
        try{
            return (long)model.getNumUsers();
        }
        catch (TasteException e){
            System.out.println(e);
        }
        return 0;
    }

    public List<String> getRecommendationsForUser(String user){

        List<String> recommendationsList = new ArrayList<String>();

        try{
            //Data from recommendation
            similarity = new PearsonCorrelationSimilarity(model);
            neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            long idUser = (long)manageList.userList.get( user );

            List recommends = recommender.recommend( idUser, 3);

            for(Object re : recommends)
            {
                recommendationsList.add( manageList.invertProductList.get( ((RecommendedItem) re).getItemID() ) );
            }
        }
        catch (TasteException e){
            System.out.print(e);
        }

        return recommendationsList;
    }
}