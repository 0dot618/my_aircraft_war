package edu.hitsz.Dao;

import java.io.IOException;

public interface ScoreDao {

    void doAdd(Score score) throws IOException;

    String[][] getAll() throws IOException;

    void delete(String userName,String mark,String time) throws IOException;
}
