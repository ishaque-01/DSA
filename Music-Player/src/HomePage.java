import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HomePage extends JFrame implements ActionListener {

    private JLabel label;
    private ImageIcon bgIcon;
    private JButton audio, video, exit;
    private JPanel btnPanel;
    private File selectedFile;

    public HomePage() {
        this.setSize(1000,580);
        this.setTitle("DIATON PLAYER");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        bgIcon = new ImageIcon("res/bgImage.jpg");
        label = new JLabel("",bgIcon,JLabel.CENTER);
        label.setBounds(0,0,1000, 550);

        audio = new JButton("AUDIO");
        audio.setBounds(65,70,170,40);
        audio.setFocusable(false);
        audio.setFont(new Font("Courier New",Font.BOLD,25));
        audio.setForeground(Color.WHITE);
        audio.setBorder(null);
        audio.setBackground(Color.BLACK);
        audio.addActionListener(this);

        video = new JButton("VIDEO");
        video.setBounds(65,20,170,40);
        video.setFocusable(false);
        video.setFont(new Font("Courier New",Font.BOLD,25));
        video.setForeground(Color.WHITE);
        video.setBorder(null);
        video.setBackground(Color.BLACK);
        video.addActionListener(this);

        exit = new JButton("EXIT");
        exit.setBounds(65,120,170,40);
        exit.setFocusable(false);
        exit.setFont(new Font("Courier New",Font.BOLD,25));
        exit.setForeground(Color.WHITE);
        exit.setBorder(null);
        exit.setBackground(Color.BLACK);
        exit.addActionListener(this);

        
        btnPanel = new JPanel();
        btnPanel.setLayout(null);
        btnPanel.setBackground(new Color(0, 0, 0, 80));
        btnPanel.setOpaque(true);
        btnPanel.setBounds(155, 300, 300, 200);
        btnPanel.add(video);
        btnPanel.add(audio);
        btnPanel.add(exit);
        
        this.add(btnPanel);
        this.add(label);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == audio){
            new AudioFiles();
            this.dispose();
        }else if(e.getSource() == video){
            new VideoFiles();
            this.dispose();
        } else if(e.getSource() == exit) {
            this.dispose();
        }
    }
}