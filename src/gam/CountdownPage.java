package gam ;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CountdownPage extends JFrame {
    private JLabel countdownLabel;

    public CountdownPage() {
        super("Countdown Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Initialize GUI components
        countdownLabel = new JLabel();
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Set layout
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add the countdown label to the frame
        add(countdownLabel);

        setVisible(true);

        // Start the countdown
        startCountdown();
    }

    private void startCountdown() {
        new Thread(() -> {
            while (true) {
                
                LocalDate today = LocalDate.now();
                    System.out.println(today);
                LocalDate nextEventDate = today.plusDays(30).withDayOfMonth(1); // Assume event happens on the first of each month
                long daysRemaining = ChronoUnit.DAYS.between(today, nextEventDate);

                SwingUtilities.invokeLater(() -> countdownLabel.setText("Days Remaining: " + daysRemaining));

                try {
                    // Sleep for 1 day
                    Thread.sleep(24 * 60 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CountdownPage::new);
    }
}

