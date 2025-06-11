package client;

import interfaces.LostAndFoundInterface;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.sql.Date;

public class LostAndFoundGUI extends JFrame {
    LostAndFoundInterface stub;

    public LostAndFoundGUI() {
        try {
            stub = (LostAndFoundInterface) Naming.lookup("rmi://localhost/LostAndFoundService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
            System.exit(1);
        }

        setTitle("Lost & Found System");
        setSize(520, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Main panel with gradient background and shadow
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 34, 45), getWidth(), getHeight(),
                        new Color(44, 47, 59));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Drop shadow for card
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRoundRect(25, 35, getWidth() - 50, getHeight() - 70, 40, 40);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Card-style inner panel with shadow effect
        JPanel cardPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header label
        JLabel header = new JLabel("Lost & Found System", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(new Color(33, 102, 172));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        cardPanel.add(header, BorderLayout.NORTH);

        // Subtitle
        JLabel subtitle = new JLabel("Campus Lost & Found Portal", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(80, 80, 80));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        cardPanel.add(subtitle, BorderLayout.BEFORE_FIRST_LINE);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);

        Color accent = new Color(0, 255, 200); // Vibrant cyan
        Color accentHover = new Color(0, 200, 150);
        JButton reportLostBtn = new JButton("📌 Report Lost");
        JButton reportFoundBtn = new JButton("🧾 Report Found");
        JButton searchItemBtn = new JButton("🔍 Search Item");

        JButton[] buttons = { reportLostBtn, reportFoundBtn, searchItemBtn };
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btn.setBackground(accent);
            btn.setForeground(Color.BLACK); // Black text for contrast
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(33, 102, 172), 2, true),
                    BorderFactory.createEmptyBorder(12, 30, 12, 30)));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(accentHover);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(accent);
                }
            });
        }

        buttonPanel.add(reportLostBtn);
        buttonPanel.add(reportFoundBtn);
        buttonPanel.add(searchItemBtn);

        cardPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);

        reportLostBtn.addActionListener(e -> reportItem("lost"));
        reportFoundBtn.addActionListener(e -> reportItem("found"));
        searchItemBtn.addActionListener(e -> searchItem());

        setVisible(true);
    }

    private void reportItem(String type) {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField itemName = new JTextField();
        JTextField place = new JTextField();
        JTextField date = new JTextField(); // format: yyyy-mm-dd
        JTextField desc = new JTextField();

        Object[] fields = {
                "Your Name:", name,
                "Phone Number:", phone,
                "Item Name:", itemName,
                "Place in Campus:", place,
                "Date (yyyy-mm-dd):", date,
                "Description:", desc
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Report " + type.toUpperCase(),
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Item item = new Item(
                        name.getText(),
                        phone.getText(),
                        itemName.getText(),
                        place.getText(),
                        Date.valueOf(date.getText()),
                        desc.getText());

                String response = type.equals("lost") ? stub.reportLost(item) : stub.reportFound(item);
                JOptionPane.showMessageDialog(this, response);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void searchItem() {
        String keyword = JOptionPane.showInputDialog(this, "Enter item name to search:");

        if (keyword != null && !keyword.trim().isEmpty()) {
            try {
                java.util.List<Item> items = stub.searchItem(keyword);

                if (items.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No items found.");
                } else {
                    StringBuilder result = new StringBuilder();
                    for (Item item : items) {
                        result.append("Found By: ").append(item.getName())
                                .append("\nPhone: ").append(item.getPhone())
                                .append("\nItem: ").append(item.getItemName())
                                .append("\nPlace: ").append(item.getPlace())
                                .append("\nDate: ").append(item.getDate())
                                .append("\nDescription: ").append(item.getDescription())
                                .append("\n------------------------\n");
                    }
                    JTextArea textArea = new JTextArea(result.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(350, 200));
                    JOptionPane.showMessageDialog(this, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new LostAndFoundGUI();
    }
}
