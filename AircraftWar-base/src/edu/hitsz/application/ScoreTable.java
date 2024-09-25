package edu.hitsz.application;

import edu.hitsz.Dao.Score;
import edu.hitsz.Dao.ScoreDao;
import edu.hitsz.Dao.ScoreDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ScoreTable {
    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JLabel difficultyLable;
    private JTable scoreTable;
    private JButton deleteButton;
    private JLabel scoreTableLable;
    private JScrollPane scoreTableScrollPane;
    public int score;
    private String userName = "testUserName";
    public String time;
    private ScoreDao scoreDao = new ScoreDaoImpl(Main.difficulty);

    public ScoreTable(int score,String time) {
        this.score = score;
        this.time = time;
        difficultyLable.setText("难度："+Main.difficulty);

        String[] columnName = {"名次","玩家名","得分","记录时间"};


        userName = JOptionPane.showInputDialog(null,"游戏结束，你的得分为"+score+'\n'+"请输入名字记录得分","输入",JOptionPane.PLAIN_MESSAGE);

        Score newScore = new Score(userName, score, time);
        try {
            scoreDao.doAdd(newScore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String[][][] tableData = {null};
        try {
            tableData[0] = scoreDao.getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData[0], columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        scoreTableScrollPane.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                //System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？","选择",JOptionPane.YES_NO_OPTION);
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    String delUserName = scoreTable.getValueAt(row,1).toString();
                    String delScore = scoreTable.getValueAt(row,2).toString();
                    String delTime = scoreTable.getValueAt(row,3).toString();
                    try {
                        scoreDao.delete(delUserName,delScore,delTime);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        tableData[0] = scoreDao.getAll();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    DefaultTableModel model = new DefaultTableModel(tableData[0], columnName);
                    scoreTable.setModel(model);
                    //model.removeRow(row);
                    //scoreTable.updateUI();
                    //scoreTable.repaint();
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
