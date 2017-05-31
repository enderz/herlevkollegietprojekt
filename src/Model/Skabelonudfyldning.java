package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Herlev Kollegiet Udvikler on 23-05-2017..
 *
 * Daniel H.M.
 */
public class Skabelonudfyldning
{
    private ArrayList<String> keyWords = new ArrayList();
    private ArrayList<String> skabelonUdfyldninger = new ArrayList();
    private LocalDate localDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
    private String formatString = localDate.format(formatter);
    private String outputFilePath = "";
    private String inputFilePath = "";

    public String getDate()
    {
        return formatString;
    }

    public String getOutputFilePath()
    {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }

    public String getInputFilePath()
    {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath)
    {
        this.inputFilePath = inputFilePath;
    }

    public void setKeyWord(ArrayList<String> keyWords)
    {
        this.keyWords = keyWords;
    }


    public ArrayList<String> getUdfyldning()
    {
        return skabelonUdfyldninger;
    }

    public ArrayList<String> getKeyWords()
    {
        return keyWords;
    }

}