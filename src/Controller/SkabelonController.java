package Controller;
import Model.Skabelonudfyldning;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Daniel on 24-05-2017.
 */
public class SkabelonController implements ISkabelon
{
    private Skabelonudfyldning skabelon = new Skabelonudfyldning();
    private ArrayList<String> keyWords = skabelon.getKeyWords();
    public ArrayList<String> skabelonUdfyldning = skabelon.getUdfyldning();


    public void fillDocument() {
        try {
            // skal hardcodes for hvert document
            //skabelon.setInputFilePath("C:\\Users\\Ender\\Desktop\\Projekt\\input.docx");
            skabelon.setInputFilePath("C:\\Users\\Ender\\Dropbox\\2.Semester_Afleveringer\\Projekt2017\\Blanketer\\Modsætte sig fremleje.docx");
            //skabelon.setOutputFilePath("C:\\Users\\Ender\\Desktop\\Projekt\\output.docx");
            skabelon.setOutputFilePath("C:\\Users\\Ender\\Dropbox\\2.Semester_Afleveringer\\Projekt2017\\Blanketer\\Output.docx");
            //C:\Users\Ender\Dropbox\2.Semester_Afleveringer\Projekt2017\Blanketer

            fillKeywordsArray();

            //kopieret fra nettet, for fat i alt teksten i input filen
            XWPFDocument docx = new XWPFDocument(new FileInputStream(skabelon.getInputFilePath()));
            for (XWPFParagraph p : docx.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {

                        String text = r.getText(0);

                        String dagsDato = "[DAGS DATO]";
                        // erstater [DAGS DATO] med datoen for den dag programmet blev kørt
                        if (text != null) {
                            text = text.replace(dagsDato, skabelon.getDate());
                            r.setText(text, 0);
                        }
                        // finder keywords og erstater dem med indtastningerne fra GUI
                        for (int i = 0; i < skabelon.getKeyWords().size() ; i++)
                        {

                            if (text != null && text.contains(skabelon.getKeyWords().get(i)))
                            {
                                text = text.replace(skabelon.getKeyWords().get(i), skabelonUdfyldning.get(i));
                                r.setUnderline(UnderlinePatterns.SINGLE);
                                r.setText(text,0);

                            }

                        }
                    }
                }
            }
            // skriver til outputfilen, outputfilen er næsten identisk med input filen, udover de keywords
            // der bliver erstattet
            docx.write(new FileOutputStream(skabelon.getOutputFilePath()));
            File file = new File(skabelon.getOutputFilePath());
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //keywords skal hardcodes, for hvert document
    public void fillKeywordsArray()
    {
        keyWords.addAll(Arrays.asList("[Navn]","[værelsesnummer]"));
        skabelon.setKeyWord(keyWords);

    }

    public ArrayList<String> fillSkabelonArray(ArrayList<String> skabelon)
    {
        return skabelon;
    }

    public void openFile(String fileName)
    {
        File file = new File("C:\\Users\\Ender\\Desktop\\Projekt\\" + fileName);

        if (file.exists())
        {
            try
            {
                Desktop.getDesktop().open(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Error, filen "+ fileName + " eksistere ikke i mappen");
        }
    }
}
