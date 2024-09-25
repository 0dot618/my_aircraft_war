package edu.hitsz.Dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ScoreDaoImpl implements ScoreDao {
    private List<Score> scores;
    private String pathName;

    public ScoreDaoImpl(String difficulty){
        scores =new ArrayList<>();

        if(difficulty.equals("EASY")){
            pathName = "EasyScores.txt";
        }
        else if(difficulty.equals("MEDIUM")){
            pathName = "MediumScores.txt";
        }
        else{
            pathName = "HardScores.txt";
        }

    }

    @Override
    public void doAdd(Score score) throws IOException {
        scores.add(score);
        File file=new File(pathName);
        FileOutputStream fop=new FileOutputStream(file,true);
        OutputStreamWriter writer=new OutputStreamWriter(fop,"utf-8");
        writer.append(score.getUserName()+"\n"+ score.getMark()+"\n"+ score.getTime()+"\n");
        writer.close();
        fop.close();

    }

    @Override
    public String[][] getAll() throws IOException {
        scores.clear();
        File file=new File(pathName);
        FileInputStream fip=new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(fip,"utf-8");
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        while(line!=null){
            Score score=new Score();
            score.setUserName(line);
            line=br.readLine();
            String markS = line;
            int markInt=Integer.parseInt(markS);
            score.setMark(markInt);
            line=br.readLine();
            score.setTime(line);
            line=br.readLine();
            scores.add(score);
        }
        br.close();
        reader.close();
        fip.close();

        scores.sort(Comparator.comparing(Score::getMark));
        Collections.reverse(scores);

        System.out.println("***********************************");
        System.out.println("             得分排行榜             ");
        System.out.println("***********************************");

        String[][] scoreTable=new String[scores.size()][4];

        for(int i = 0; i< scores.size(); i++){
            System.out.print("第"+(i+1)+"名：");
            scoreTable[i][0]= String.valueOf(i+1);
            System.out.print(scores.get(i).getUserName()+',');
            scoreTable[i][1]=scores.get(i).getUserName();
            System.out.print(scores.get(i).getMark());
            scoreTable[i][2]= String.valueOf(scores.get(i).getMark());
            System.out.print(','+scores.get(i).getTime()+"\n");
            scoreTable[i][3]=scores.get(i).getTime();
        }
        return scoreTable;
    }

    public void delete(String userName,String mark,String time) throws IOException {
        scores.clear();
        File file=new File(pathName);
        FileInputStream fip=new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(fip,"utf-8");
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        while(line!=null){
            Score score=new Score();
            score.setUserName(line);
            line=br.readLine();
            String markS = line;
            int markInt=Integer.parseInt(markS);
            score.setMark(markInt);
            line=br.readLine();
            score.setTime(line);
            if(!(score.getUserName().equals(userName) && score.getMark()==Integer.parseInt(mark) && score.getTime().equals(time))){
                scores.add(score);
            }
            line=br.readLine();
        }
        br.close();
        reader.close();
        fip.close();

        File temp=new File("scorescopy.txt");
        FileOutputStream fop=new FileOutputStream(temp,true);
        OutputStreamWriter writer=new OutputStreamWriter(fop,"utf-8");
        for(Score score:scores){
            writer.append(score.getUserName()+"\n"+ score.getMark()+"\n"+ score.getTime()+"\n");
        }
        writer.close();
        fop.close();


        file.delete();
        temp.renameTo(file);


    }
}
