package src.thread;

import src.Logic;
import src.Player;
import src.UI.Game;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class OutRunnable implements Runnable{
    private Socket socket;
    private Game game;
    public OutRunnable(Socket socket, Game game){
        this.socket = socket;
        this.game = game;
    }
    @Override
    public void run() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true) {
            Point lastClickPoint = game.getChessBoard().getLastClickPoint();
//            System.out.println("上一次点击坐标: " + lastClickPoint);
            try {
                oos.writeObject(lastClickPoint);
                oos.flush();
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                Logic.leave(game.getChessBoard(), Player.isRoomOwner());
                break;
            }
        }
    }
}
