package nearsoft.academy.bigdata.recommendation;

import java.io.*;

/**
 * Created by AMDA on 30/03/2017.
 */
class TransformData
{
    public String filenameOutput;

    //List Manage
    public ManageList manageList = new ManageList();

    public void setOutputFile(String file){
        filenameOutput = file;
    }

    public void transformToPreferenceFile(String file)
    {
        try{
            //Reader
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Writer
            FileWriter fw = new FileWriter(filenameOutput);
            BufferedWriter bw = new BufferedWriter(fw);

            //Line control
            String line;

            StringBuilder sb = new StringBuilder();

            int index = 0, totalIndexes = 0;

            while( (line = br.readLine()) != null )
            {
                if (line.split("/")[0].equals("product")) {
                    index++;
                    sb.append( manageList.addProduct(line.split(" ")[1]) + ",");

                }
                else if (line.split(":")[0].equals("review/userId")) {
                    index++;
                    sb.insert(0, manageList.addUser(line.split(" ")[1]) + ",");

                }
                else if (line.split(":")[0].equals("review/score")) {
                    index++;
                    sb.append(line.split(" ")[1]);
                }

                if (index == 3) {
                    bw.write(sb.toString() + "\n");
                    index = 0;
                    totalIndexes++;
                    sb.setLength(0);
                }
            }

            manageList.totalIndexes = totalIndexes;

            br.close();
            bw.close();
            fr.close();
            fw.close();
        }
        catch (IOException e){
            System.out.print(e);
        }
    }
}
