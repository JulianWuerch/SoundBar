package classes;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;


public class Window {

    public static JFrame window;
    public static Draw draw;

    public static void openWindow() {
        window = new JFrame("Background Music");
        draw = new Draw();
        draw.setPreferredSize(new Dimension(400, 200));
        draw.addMouseListener(new Mouse());
        draw.addMouseWheelListener(new Mouse());
        draw.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                if (event.paramString().split(" ")[0].equals("COMPONENT_RESIZED")) {
                    String[] parameters = event.paramString().split(" ");
                    float factor = Float.parseFloat(parameters[2].substring(0, parameters[2].indexOf("x"))) / 400f;
                    Draw.h = (int) (Draw.H * factor);
                    Draw.w = (int) (Draw.W * factor);
                    Draw.x = (int) (Draw.X * factor);
                    Draw.y = (int) (Draw.Y * factor);
                    Draw.boarderDist = (int) (Draw.BOARDER_DIST * factor);
                }
            }
        });
        window.add(draw);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
}