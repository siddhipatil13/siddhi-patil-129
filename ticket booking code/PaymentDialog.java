@@ -0,0 +1,166 @@
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.URL;

public class PaymentDialog extends JDialog {
    private boolean paymentSuccess = false;
    private String paymentMethod = "";
    private JLabel qrCodeLabel;
    private int amount; // amount to be paid
    private JLabel amountLabel;

    public PaymentDialog(JFrame parent, int numberOfTickets) {
        super(parent, "Payment Method", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);

        // Calculate amount based on number of tickets, 100 Rs per ticket
        amount = numberOfTickets * 100;
        if (amount == 0) {
            amount = 500; // default amount if no tickets
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Select Payment Method", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton onlineButton = new JButton("Online Payment");
        onlineButton.addActionListener(e -> showQRCode());

        JButton offlineButton = new JButton("Offline Payment");
        offlineButton.addActionListener(e -> {
            paymentSuccess = true;
            paymentMethod = "Offline";
            dispose();
        });

        buttonPanel.add(onlineButton);
        buttonPanel.add(offlineButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }

    private void showQRCode() {
        getContentPane().removeAll();

        JPanel qrPanel = new JPanel();
        qrPanel.setLayout(new BoxLayout(qrPanel, BoxLayout.Y_AXIS));
        qrPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel instructionLabel = new JLabel("Scan QR code to pay", JLabel.CENTER);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        qrPanel.add(instructionLabel);
        qrPanel.add(Box.createVerticalStrut(10));

        // QR Code Image
        qrCodeLabel = new JLabel();
        qrCodeLabel.setPreferredSize(new Dimension(200, 200));
        qrCodeLabel.setHorizontalAlignment(JLabel.CENTER);
        qrCodeLabel.setVerticalAlignment(JLabel.CENTER);

        ImageIcon qrCodeImage = loadQRCodeImage();
        if (qrCodeImage != null) {
            qrCodeLabel.setIcon(qrCodeImage);
        } else {
            qrCodeLabel.setText("QR Code Image not available");
        }
        qrCodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrPanel.add(qrCodeLabel);
        qrPanel.add(Box.createVerticalStrut(10));

        // Dropdown for payment app
        JPanel paymentAppPanel = new JPanel(new FlowLayout());
        paymentAppPanel.add(new JLabel("Select Payment App:"));
        String[] apps = {"GPay", "PhonePe", "Paytm", "QR"};
        JComboBox<String> paymentAppDropdown = new JComboBox<>(apps);
        paymentAppPanel.add(paymentAppDropdown);
        qrPanel.add(paymentAppPanel);

        // Confirm payment checkbox
        JCheckBox confirmPaymentBox = new JCheckBox("I have completed the payment");
        confirmPaymentBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrPanel.add(confirmPaymentBox);
        qrPanel.add(Box.createVerticalStrut(10));

        // Display amount
        amountLabel = new JLabel("Amount: ₹" + amount);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrPanel.add(amountLabel);
        qrPanel.add(Box.createVerticalStrut(10));

        // Done button
        JButton doneButton = new JButton("Pay & Book");
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneButton.addActionListener(e -> {
            if (!confirmPaymentBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please confirm that the payment has been made.",
                        "Payment Confirmation Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            paymentSuccess = true;
            paymentMethod = "Online (" + paymentAppDropdown.getSelectedItem().toString() + ")";
            dispose();
        });
        qrPanel.add(doneButton);

        add(qrPanel);
        revalidate();
        repaint();
    }

    private ImageIcon loadQRCodeImage() {
        try {
            URL url = new URL("https://vinsmileinc.com/userfiles/image/GPay.jpg");
            Image image = ImageIO.read(url);

            // Scale image proportionally within 200x200
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            int maxSize = 200;

            if (width > maxSize || height > maxSize) {
                float aspectRatio = (float) width / height;
                int newWidth = width > height ? maxSize : (int) (maxSize * aspectRatio);
                int newHeight = height >= width ? maxSize : (int) (maxSize / aspectRatio);
                image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            }

            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}











