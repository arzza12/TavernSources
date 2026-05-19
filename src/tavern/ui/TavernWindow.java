package tavern.ui;

import tavern.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TavernWindow extends JFrame {

    // --- Цвета для оформления в стиле таверны ---
    private static final Color BG_DARK       = new Color(28, 25, 25);
    private static final Color BG_PANEL      = new Color(168, 136, 102);
    private static final Color ACCENT_GOLD   = new Color(200, 155, 60);
    private static final Color ACCENT_FIRE   = new Color(210, 90, 30);
    private static final Color TEXT_LIGHT    = new Color(235, 220, 190);
    private static final Color TEXT_MUTED    = new Color(160, 140, 110);
    private static final Color BORDER_COLOR  = new Color(100, 75, 40);

    // --- Виджеты ---
    private JCheckBox cbFireSauce;
    private JCheckBox cbDoubleVenison;
    private JCheckBox cbSnowBerries;
    private JCheckBox cbNordicFlatbread;
    private JTextArea orderHistory;
    private JLabel priceLabel;

    private List<JCheckBox> allCheckBoxes;

    public TavernWindow() {
        setTitle("Таверна «Гарцующая кобыла» ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);
        updatePrice();
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(new EmptyBorder(0, 0, 12, 0));

        JLabel title = new JLabel("Таверна Гарцующая кобыла", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(ACCENT_GOLD);

        JLabel subtitle = new JLabel("Меню • Нордское рагу (50 септимов)", SwingConstants.CENTER);
        subtitle.setFont(new Font("Serif", Font.ITALIC, 14));
        subtitle.setForeground(TEXT_MUTED);

        panel.add(title,    BorderLayout.CENTER);
        panel.add(subtitle, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildCenter() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 12, 0));
        panel.setBackground(BG_DARK);

        panel.add(buildModifiersPanel());
        panel.add(buildHistoryPanel());
        return panel;
    }

    private JPanel buildModifiersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_PANEL);
        panel.setBorder(createTitledBorder("Добавки (макс. 3)"));

        cbFireSauce      = makeCheckBox("Огненный соус",   "+40 септ.");
        cbDoubleVenison  = makeCheckBox("Двойная оленина", "+20 септ.");
        cbSnowBerries    = makeCheckBox("Снежные ягоды",   "+6 септ.");
        cbNordicFlatbread = makeCheckBox("Нордский лаваш", "+7 септ.");

        allCheckBoxes = new ArrayList<JCheckBox>();
        allCheckBoxes.add(cbFireSauce);
        allCheckBoxes.add(cbDoubleVenison);
        allCheckBoxes.add(cbSnowBerries);
        allCheckBoxes.add(cbNordicFlatbread);

        ItemListener limiter = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                enforceCheckboxLimit();
                updatePrice();
            }
        };

        for (JCheckBox cb : allCheckBoxes) {
            cb.addItemListener(limiter);
            panel.add(Box.createVerticalStrut(8));
            panel.add(cb);
        }

        panel.add(Box.createVerticalGlue());

        priceLabel = new JLabel("Итого: 50 септимов", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Serif", Font.BOLD, 16));
        priceLabel.setForeground(ACCENT_GOLD);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(12));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private JPanel buildHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setBorder(createTitledBorder("История заказов"));

        orderHistory = new JTextArea();
        orderHistory.setEditable(false);
        orderHistory.setFont(new Font("Monospaced", Font.PLAIN, 12));
        orderHistory.setBackground(new Color(22, 15, 8));
        orderHistory.setForeground(TEXT_LIGHT);
        orderHistory.setCaretColor(ACCENT_GOLD);
        orderHistory.setBorder(new EmptyBorder(6, 6, 6, 6));

        JScrollPane scroll = new JScrollPane(orderHistory);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scroll.getViewport().setBackground(new Color(22, 15, 8));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panel.setBackground(BG_DARK);

        JButton orderBtn = new JButton("⚔  Оформить заказ");
        orderBtn.setFont(new Font("Serif", Font.BOLD, 16));
        orderBtn.setForeground(BG_DARK);
        orderBtn.setBackground(ACCENT_GOLD);
        orderBtn.setFocusPainted(false);
        orderBtn.setBorderPainted(false);
        orderBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        orderBtn.setPreferredSize(new Dimension(240, 42));

        orderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });

        panel.add(orderBtn);
        return panel;
    }

    // ---- Логика ----

    private void enforceCheckboxLimit() {
        int checked = countChecked();
        for (JCheckBox cb : allCheckBoxes) {
            if (!cb.isSelected()) {
                cb.setEnabled(checked < 3);
            }
        }
    }

    private int countChecked() {
        int count = 0;
        for (JCheckBox cb : allCheckBoxes) {
            if (cb.isSelected()) {
                count++;
            }
        }
        return count;
    }

    private void updatePrice() {
        Dish dish = buildDish();
        priceLabel.setText("Итого: " + dish.getPrice() + " септимов");

        // Смена цвета при дорогом заказе
        if (dish.getPrice() >= 100) {
            priceLabel.setForeground(ACCENT_FIRE);
        } else {
            priceLabel.setForeground(ACCENT_GOLD);
        }
    }


    private Dish buildDish() {
        Dish dish = new NordicStew();

        if (cbFireSauce.isSelected()) {
            dish = new FireSauce(dish);
        }
        if (cbDoubleVenison.isSelected()) {
            dish = new DoubleVenison(dish);
        }
        if (cbSnowBerries.isSelected()) {
            dish = new SnowBerries(dish);
        }
        if (cbNordicFlatbread.isSelected()) {
            dish = new NordicFlatbread(dish);
        }

        return dish;
    }

    private void placeOrder() {
        Dish dish = buildDish();

        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String line = time + " | " + dish.getName() + " | " + dish.getPrice() + " септимов\n";

        orderHistory.append(line);
        orderHistory.setCaretPosition(orderHistory.getDocument().getLength());

        for (JCheckBox cb : allCheckBoxes) {
            cb.setSelected(false);
            cb.setEnabled(true);
        }
        updatePrice();
    }


    private JCheckBox makeCheckBox(String label, String price) {
        JCheckBox cb = new JCheckBox("<html><b>" + label + "</b>  <font color='#c89b3c'>" + price + "</font></html>");
        cb.setBackground(BG_PANEL);
        cb.setForeground(TEXT_LIGHT);
        cb.setFont(new Font("Serif", Font.PLAIN, 14));
        cb.setFocusPainted(false);
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setBorder(new EmptyBorder(2, 12, 2, 12));
        return cb;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            title
        );
        border.setTitleFont(new Font("Serif", Font.BOLD, 13));
        border.setTitleColor(ACCENT_GOLD);
        return border;
    }
}
