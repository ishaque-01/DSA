import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AudioFiles extends JFrame implements ActionListener {

    private JPanel addList, listPanel, listCtrl;
    private JLabel bgLabel;
    private JButton addBtn, back, play;
    private JComboBox order;
    private ImageIcon addBtnIcon;
    private File selectedFile;
    private LinkedList<File> list;
    private JTextArea fileListTextArea, conditionText;
    private int count;
    private String orderValue;

    public AudioFiles() {
        this.setSize(1000,580);
        this.setTitle("Choose Audio Files");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(116,34,117,255));

        addBtnIcon = new ImageIcon("res/addIcon.jpeg");

        addBtn = new JButton("Add Files");
        addBtn.setIcon(addBtnIcon);
        addBtn.setBounds(165,10,78,78);
        addBtn.setFocusable(false);
        addBtn.setFont(new Font("Courier New",Font.BOLD,25));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(null);
        addBtn.setBackground(Color.CYAN);
        addBtn.addActionListener(this);

        list = new LinkedList<>();

        conditionText = new JTextArea("Waiting For File!");
        conditionText.setEditable(false);
        conditionText.setBounds(35, 200, 400, 40);
        conditionText.setFont(new Font("Courier New", Font.BOLD, 22));
        conditionText.setForeground(Color.WHITE);
        conditionText.setBackground(Color.BLUE);

        addList = new JPanel();
        addList.setBounds(10, 20, 500, 300);
        addList.setLayout(null);
        addList.setBackground(Color.BLACK);
        addList.add(addBtn);
        addList.add(conditionText);

        fileListTextArea = new JTextArea();
        count = 1;
        fileListTextArea.setBounds(20,0,450, 510);
        fileListTextArea.setEditable(false);
        fileListTextArea.setAutoscrolls(true);
        fileListTextArea.setFont(new Font("Courier New Font", Font.BOLD, 16));
        fileListTextArea.setBackground(new Color(228,205,227,255));
        fileListTextArea.setForeground(Color.BLACK);
        fileListTextArea.setLineWrap(true);
        fileListTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(fileListTextArea);
        scrollPane.setBounds(20, 0, 450, 510);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        listPanel = new JPanel();
        listPanel.add(scrollPane);
        listPanel.setBackground(Color.BLUE);
        listPanel.setBackground(new Color(116,34,117,255));
        listPanel.setBounds(510, 20, 500, 510);
        listPanel.setLayout(null);

        bgLabel = new JLabel();
        bgLabel.setBounds(0,0, listPanel.getWidth(), listPanel.getHeight());
        listPanel.add(bgLabel);

        play = new JButton("Play");
        play.setBounds(20, 20, 460, 40);
        play.setFocusable(false);
        play.setBorder(null);
        play.setBackground(new Color(237, 46, 238,255));
        play.setForeground(Color.WHITE);
        play.setFont(new Font("Courier New Font", Font.BOLD, 30));
        play.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(20, 140, 460, 40);
        back.setFocusable(false);
        back.setBorder(null);
        back.setBackground(new Color(237, 46, 238,255));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Courier New Font", Font.BOLD, 30));
        back.addActionListener(this);

        String[] ordersList = {"List-Loop", "Single-Loop", "Shuffle"};
        orderValue = "";
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        order = new JComboBox(ordersList);
        order.setRenderer(listRenderer);
        order.setBounds(20, 80, 460, 40);
        order.setFocusable(false);
        order.setBorder(null);
        order.setBackground(new Color(237, 46, 238,255));
        order.setForeground(Color.WHITE);
        order.setFont(new Font("Courier New Font", Font.BOLD, 25));
        order.addActionListener(this);
        
        listCtrl = new JPanel();
        listCtrl.setLayout(null);
        listCtrl.setBounds(10, 330,500,200);
        listCtrl.setBackground(Color.YELLOW);
        listCtrl.add(play);
        listCtrl.add(order);
        listCtrl.add(back);

        this.add(addList);
        this.add(listPanel);
        this.add(listCtrl);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBtn) {
            getFile();
        } else if (e.getSource() == order){
            orderValue = String.valueOf(order.getSelectedItem());
            System.out.println(orderValue);
        } else if (e.getSource() == back) {
            new HomePage();
            this.dispose();
        }
    }

    public void getFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files", "mp3", "wav", "acc", "m4a", "flac", "mp3");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            if(!list.find(selectedFile.getAbsoluteFile())) {
                fileListTextArea.append(count + "-  " + selectedFile.getName() + "\n\n");
                count++;
                list.add(selectedFile.getAbsoluteFile());
                conditionText.setText("File Added Successfully!");
            } else {
                conditionText.setText("File Already Added!");
            }
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(2000);
                    return null;
                }

                @Override
                protected void done() {
                    conditionText.setText("Waiting For File!");
                }
            }.execute();
        }
    }
}
