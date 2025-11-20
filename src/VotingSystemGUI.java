import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class VotingSystemGUI extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);
    private final Blockchain blockchain;

    // Admin creds (demo)
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public VotingSystemGUI() {
        super("Blockchain Based Voting System");
        String[] candidates = {"Alice", "Bob", "Charlie", "Diana"};
        blockchain = new Blockchain(candidates);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 520);
        setLocationRelativeTo(null);

        root.add(homePanel(), "home");
        root.add(registerPanel(), "register");
        root.add(voterLoginPanel(), "voterLogin");
        root.add(adminLoginPanel(), "adminLogin");
        root.add(adminDashboardPanel(), "adminDashboard");

        setContentPane(root);
        layout.show(root, "home");
    }

    private JPanel homePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10,10,10,10);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Blockchain Based Voting System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

        JButton register = new JButton("Register (Voter)");
        register.addActionListener(e -> layout.show(root, "register"));

        JButton loginVoter = new JButton("Login (Voter)");
        loginVoter.addActionListener(e -> layout.show(root, "voterLogin"));

        JButton loginAdmin = new JButton("Login (Admin)");
        loginAdmin.addActionListener(e -> layout.show(root, "adminLogin"));

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.weightx = 1;
        p.add(title, gc);
        gc.gridy++;
        p.add(register, gc);
        gc.gridy++;
        p.add(loginVoter, gc);
        gc.gridy++;
        p.add(loginAdmin, gc);
        return p;
    }

    private JPanel registerPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0; gc.gridy = 0;

        JLabel head = new JLabel("Voter Registration", SwingConstants.CENTER);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        gc.gridwidth = 2; p.add(head, gc);

        gc.gridwidth = 1; gc.gridy++;
        p.add(new JLabel("Voter ID:"), gc);
        JTextField tfId = new JTextField(16);
        gc.gridx = 1; p.add(tfId, gc);

        gc.gridx = 0; gc.gridy++;
        p.add(new JLabel("Password:"), gc);
        JPasswordField tfPass = new JPasswordField(16);
        gc.gridx = 1; p.add(tfPass, gc);

        gc.gridx = 0; gc.gridy++;
        p.add(new JLabel("Email:"), gc);
        JTextField tfEmail = new JTextField(16);
        gc.gridx = 1; p.add(tfEmail, gc);

        gc.gridx = 0; gc.gridy++;
        JButton btnReg = new JButton("Register");
        btnReg.addActionListener((ActionEvent e) -> {
            String id = tfId.getText().trim();
            String pw = new String(tfPass.getPassword());
            String email = tfEmail.getText().trim();
            if (blockchain.registerVoter(id, pw, email)) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Registered: " + id);
                layout.show(root, "home");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Check inputs or duplicate ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(btnReg, gc);

        gc.gridx = 1;
        JButton back = new JButton("Back");
        back.addActionListener(e -> layout.show(root, "home"));
        p.add(back, gc);
        return p;
    }

    private JPanel voterLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel head = new JLabel("Voter Login", SwingConstants.CENTER);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        gc.gridx=0;gc.gridy=0;gc.gridwidth=2;
        p.add(head, gc);

        gc.gridwidth=1; gc.gridy++;
        p.add(new JLabel("Voter ID:"), gc);
        JTextField tfId = new JTextField(16);
        gc.gridx=1; p.add(tfId, gc);

        gc.gridx=0; gc.gridy++;
        p.add(new JLabel("Password:"), gc);
        JPasswordField tfPass = new JPasswordField(16);
        gc.gridx=1; p.add(tfPass, gc);

        gc.gridx=0; gc.gridy++;
        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            String id = tfId.getText().trim();
            String pw = new String(tfPass.getPassword());
            if (blockchain.authenticate(id, pw)) {
                Toolkit.getDefaultToolkit().beep();
                showVotePanel(id);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(login, gc);

        gc.gridx=1;
        JButton back = new JButton("Back");
        back.addActionListener(e -> layout.show(root, "home"));
        p.add(back, gc);
        return p;
    }

    private void showVotePanel(String voterId) {
        JPanel p = new JPanel(new BorderLayout(10,10));

        JLabel head = new JLabel("Cast Your Vote", SwingConstants.CENTER);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        p.add(head, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] options = new JRadioButton[blockchain.candidates.length];
        for (int i=0;i<options.length;i++) {
            options[i] = new JRadioButton((i+1) + ". " + blockchain.candidates[i]);
            group.add(options[i]);
            center.add(options[i]);
        }
        p.add(center, BorderLayout.CENTER);

        JButton voteBtn = new JButton("Vote");
        voteBtn.addActionListener(e -> {
            int idx = -1;
            for (int i=0;i<options.length;i++) if (options[i].isSelected()) { idx = i; break; }
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Please select a candidate.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean ok = blockchain.castVote(voterId, idx);
            if (ok) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Vote cast successfully!");
                layout.show(root, "home");
            } else {
                JOptionPane.showMessageDialog(this, "You may have already voted or inputs invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(voteBtn, BorderLayout.SOUTH);

        root.add(p, "votePanel");
        layout.show(root, "votePanel");
    }

    private JPanel adminLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel head = new JLabel("Admin Login", SwingConstants.CENTER);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        gc.gridx=0;gc.gridy=0;gc.gridwidth=2;
        p.add(head, gc);

        gc.gridwidth=1; gc.gridy++;
        p.add(new JLabel("Username:"), gc);
        JTextField tfUser = new JTextField(16);
        gc.gridx=1; p.add(tfUser, gc);

        gc.gridx=0; gc.gridy++;
        p.add(new JLabel("Password:"), gc);
        JPasswordField tfPass = new JPasswordField(16);
        gc.gridx=1; p.add(tfPass, gc);

        gc.gridx=0; gc.gridy++;
        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String pw = new String(tfPass.getPassword());
            if (ADMIN_USER.equals(u) && ADMIN_PASS.equals(pw)) {
                Toolkit.getDefaultToolkit().beep();
                layout.show(root, "adminDashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        p.add(login, gc);

        gc.gridx=1;
        JButton back = new JButton("Back");
        back.addActionListener(e -> layout.show(root, "home"));
        p.add(back, gc);
        return p;
    }

    private JPanel adminDashboardPanel() {
        JTabbedPane tabs = new JTabbedPane();

        // Pie chart panel
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int total = Arrays.stream(blockchain.voteCount).sum();
                if (total == 0) {
                    g.drawString("No votes yet.", 20, 20);
                    return;
                }
                int x = 40, y = 40, w = 300, h = 300;
                int start = 0;
                for (int i = 0; i < blockchain.candidates.length; i++) {
                    int arc = (int)Math.round(((double) blockchain.voteCount[i] / total) * 360.0);
                    g.fillArc(x, y, w, h, start, arc);
                    g.drawString(blockchain.candidates[i] + " : " + blockchain.voteCount[i], x + w + 20, y + 20 + i * 20);
                    start += arc;
                }
            }
        };
        tabs.addTab("Vote Chart", chartPanel);

        // Blockchain text panel
        JTextArea area = new JTextArea(20, 60);
        area.setEditable(false);
        JButton refresh = new JButton("Refresh & Mine Pending");
        refresh.addActionListener(e -> {
            blockchain.mineBlock();
            area.setText(blockchain.blockchainAsText());
        });
        JPanel txtPanel = new JPanel(new BorderLayout());
        txtPanel.add(new JScrollPane(area), BorderLayout.CENTER);
        txtPanel.add(refresh, BorderLayout.SOUTH);
        tabs.addTab("Blockchain Data", txtPanel);

        JPanel container = new JPanel(new BorderLayout());
        JLabel head = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        container.add(head, BorderLayout.NORTH);
        container.add(tabs, BorderLayout.CENTER);
        return container;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VotingSystemGUI().setVisible(true));
    }
}