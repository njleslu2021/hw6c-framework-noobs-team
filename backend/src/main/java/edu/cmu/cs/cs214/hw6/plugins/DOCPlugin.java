package edu.cmu.cs.cs214.hw6.plugins;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cmu.cs.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.Experience;
import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException; 
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DOCPlugin implements DataPlugin {
    private final int num3 = 3;
    List<String> splitDocumentText = new ArrayList<>();
    @Override
    public void onRegister(ResumeFramework framework) {        
    }

    @Override
    public void parseDataResource(String path) throws IOException {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                splitDocumentText.add(para.getText());
                System.out.println(para.getText());
            }
            fis.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPluginName() {
        return "DOCX";
    }

    @Override
    public String getFirstName() {
        String name = splitDocumentText.get(0);
        String[] splitName = name.split(" ");
        return splitName[0];
    }

    @Override
    public String getLastName() {
        String name = splitDocumentText.get(0);
        String[] splitName = name.split(" ");
        return splitName[1];
    }

    @Override
    public String getEmail() {
        String info = splitDocumentText.get(1);
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[1];
    }

    @Override
    public String getPhoneNumber() {
        String info = splitDocumentText.get(1);
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[0];
    }

    @Override
    public String getAddress() {
        String info = splitDocumentText.get(1);
        String[] splitInfo = info.split(" +\\| +");
        if (splitInfo.length > num3) {
            return splitInfo[num3];
        }
        return null;
    }

    @Override
    public String getURL() {
        String info = splitDocumentText.get(1);
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[2];
    }

    @Override
    public List<String> getSkills() {
        int startIdx = -1; 
        List<String> skills = new ArrayList<>();
        for (int i = 0; i < splitDocumentText.size(); i++) {
            if (splitDocumentText.get(i).trim().equals("SKILLS")) {
                startIdx = i; 
                break; 
            }
        }
        if (startIdx == -1) {
            return null;
        }
        startIdx += 1;

        while (startIdx < splitDocumentText.size() && splitDocumentText.get(startIdx).trim() != "") {
            String skillLine = splitDocumentText.get(startIdx);
            String[] splitSkillLine = skillLine.split("[:,]");
            for (int j = 1; j < splitSkillLine.length; j++) {
                skills.add(splitSkillLine[j].trim());
            }
            startIdx++;
        }

        return skills;
    }

    @Override
    public List<Experience> getExperience() {
        List<Integer> startIdxs = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();

        for (int i = 0; i < splitDocumentText.size(); i++) {
            if (splitDocumentText.get(i).trim().equals("EDUCATION") ||
                splitDocumentText.get(i).trim().equals("EXPERIENCE") ||
                splitDocumentText.get(i).trim().equals("")) {
                    if (splitDocumentText.get(i+1).split(" ").length > 1) {
                        startIdxs.add(i + 1);
                    }
            }
        }

        for (int idx : startIdxs) {
            experiences.add(createExperience(idx));
        }

        return experiences;
    }

    private Experience createExperience(int startIdx) {
        String[] splitTitle = splitDocumentText.get(startIdx).split("\t"); 
        String[] splitDate = splitDocumentText.get(startIdx + 2).split(" - ");
        String title = String.join(" ", Arrays.copyOfRange(splitTitle, 0, splitTitle.length - 1)).trim();
        String location = String.join(" ", Arrays.copyOfRange(splitTitle, splitTitle.length - 1, splitTitle.length)).trim();
        String position = splitDocumentText.get(startIdx+1).trim(); 
        String startDate = splitDate[0].trim();
        String endDate = splitDate[splitDate.length - 1].trim();

        List<String> descriptions = new ArrayList<>(); 
        startIdx = startIdx + num3;
        while (startIdx < splitDocumentText.size() && splitDocumentText.get(startIdx).trim() != "") {
            String cleanText = splitDocumentText.get(startIdx).replace("\u2022 ", "");
            descriptions.add(cleanText.trim());
            startIdx++;
        }

        return new Experience(title, position, location, startDate, endDate, descriptions);
    }
    
    public static void main(String[] args) {
        List<String> splitDocumentText = new ArrayList<>();
        try {
            File file = new File("backend/src/test/resources/test.docx");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                splitDocumentText.add(para.getText());
            }
            fis.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> startIdxs = new ArrayList<>();
        for (int i = 0; i < splitDocumentText.size(); i++) {
            if (splitDocumentText.get(i).trim().equals("EDUCATION") ||
                splitDocumentText.get(i).trim().equals("EXPERIENCE") ||
                splitDocumentText.get(i).trim().equals("")) {
                    if (splitDocumentText.get(i+1).split(" ").length > 1) {
                        startIdxs.add(i + 1);
                    }
            }
        }

        for (int idx : startIdxs) {
            // System.out.println(Arrays.toString(splitDocumentText.get(idx).split("\t")));
            
            String[] splitTitle = splitDocumentText.get(idx).split("\t"); 
            //System.out.println(Arrays.toString(splitTitle));
            String[] splitDate = splitDocumentText.get(idx + 2).split(" - ");
            String title = String.join(" ", Arrays.copyOfRange(splitTitle, 0, splitTitle.length - 1)).trim();
            String location = String.join(" ", Arrays.copyOfRange(splitTitle, splitTitle.length - 1, splitTitle.length)).trim();
            String position = splitDocumentText.get(idx+1).trim(); 
            String startDate = splitDate[0].trim();
            String endDate = splitDate[splitDate.length - 1].trim();
    
            List<String> descriptions = new ArrayList<>(); 
            idx = idx + 3;
            while (idx < splitDocumentText.size() && splitDocumentText.get(idx).trim() != "") {
                String cleanText = splitDocumentText.get(idx).replace("\u2022 ", "");
                descriptions.add(cleanText.trim());
                idx++;
            }
            System.out.println(title);
            System.out.println(location);
            System.out.println(position);
            System.out.println(startDate);
            System.out.println(endDate);
        }

        int startIdx = -1; 
        List<String> skills = new ArrayList<>();
        for (int i = 0; i < splitDocumentText.size(); i++) {
            if (splitDocumentText.get(i).trim().equals("SKILLS")) {
                startIdx = i; 
                break; 
            }
        }
        if (startIdx == -1) {
            System.out.println("!!!");
        }
        startIdx += 1;

        while (startIdx < splitDocumentText.size() && splitDocumentText.get(startIdx).trim() != "") {
            String skillLine = splitDocumentText.get(startIdx);
            String[] splitSkillLine = skillLine.split("[:,]");
            for (int j = 1; j < splitSkillLine.length; j++) {
                skills.add(splitSkillLine[j].trim());
            }
            startIdx++;
        }
        System.out.println(skills);

    }
}
