package edu.hitsz.application;

import edu.hitsz.template.EasyGame;
import edu.hitsz.template.HardGame;
import edu.hitsz.template.MediumGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {

    private JPanel mainPanel;
    private JPanel difficultyOptionPanel;
    private JPanel musicOptionPanel;
    private JButton simpleModeButton;
    private JButton mediumModeButton;
    private JButton hardModeButton;
    private JComboBox<String> musicOption;
    private JLabel musicOptionLable;//2.1创建JComboBox下拉框组件
    //2.2为下拉框添加选项

    public StartMenu() {
        musicOption.addItem("开");
        musicOption.addItem("关");
        simpleModeButton.addActionListener(e -> {
            Main.difficulty = "EASY";
            gameStartAction(new EasyGame());
        });
        mediumModeButton.addActionListener(e -> {
            Main.difficulty = "MEDIUM";
            gameStartAction(new MediumGame());
        });
        hardModeButton.addActionListener(e -> {
            Main.difficulty = "HARD";
            gameStartAction(new HardGame());
        });
        musicOption.addActionListener(e -> {
            String musicOptionSelectedItem = (String) musicOption.getSelectedItem();
            if (musicOptionSelectedItem.equals("关")) {
                MusicThread.setNoPlay();
            }
        });
    }//end constructor

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void gameStartAction(Game game){
        Main.cardPanel.add(game);
        Main.cardLayout.last(Main.cardPanel);
        game.action();

    }


}