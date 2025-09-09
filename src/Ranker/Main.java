package Ranker;

import javax.swing.*;

import Ranker.GUI.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Ranker ranker = new Ranker();
//                ranker.setVisible(true);

                ranker.RankingAlgorithm();
            }
        });


    }
}