package gui;

import brawllogic.Controller;
import brawllogic.MoveAnalysis;
import deepdish.Animator;
import deepdish.GameObject;
import deepdish.Painter;
import deepdish.Scene;
import deepdish.Viewport;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Patrick Herrmann
 */
public final class GamePanel extends JPanel {

    private WindowFrame frame;
    private Animator animator;
    private Painter painter;
    private Scene scene;
    private Controller controller;
    private GameView gameView;
    private JLabel canvas;
    
    public GamePanel(WindowFrame frame, Controller controller) {
        super();

        this.frame = frame;
        frame.setTitle("Brawl");
        this.controller = controller;
        
        initializeAnimation(frame.getWidth(), frame.getHeight());
    }
    
    private void initializeAnimation(int width, int height) {
        
        gameView = new GameView(controller.getGameState());
        scene = new Scene();

        scene.add(new Tabletop());
        
        for (GameObject obj : gameView.getCardViews())
            scene.add(obj);
        
        Viewport viewport = new Viewport(width, height);
        viewport.getGraphics().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        animator = new Animator(scene, 80);
        
        painter = new Painter(scene, viewport, 24) {

            @Override
            public void sendToScreen(Image image) {
                repaint();
            }

        };

        canvas = new JLabel(new ImageIcon(viewport.getImage()));
        add(canvas);

        frame.addKeyListener(new KeyHandler());
    }

    public void start() {
        animator.start();
        painter.start();
    }
    
    private class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            List<MoveAnalysis> analyses = controller.handleInput(ke.getKeyChar());

            for (MoveAnalysis analysis : analyses) {
                if (analysis != null && !analysis.isLegal()) {
                    System.out.println(analysis.getMessage());
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            
        }
        
    }
}
