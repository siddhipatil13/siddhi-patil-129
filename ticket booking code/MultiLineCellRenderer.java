@@ -0,0 +1,42 @@
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setText(value != null ? value.toString() : "");
        setFont(table.getFont());

        if (column == 2) { // Member Names column
            adjustRowHeight(table, row);
        }

        return this;
    }

    private void adjustRowHeight(JTable table, int row) {
        int preferredHeight = getPreferredSize().height + 4;
        int defaultHeight = table.getRowHeight();
        if (table.getRowHeight(row) < preferredHeight) {
            table.setRowHeight(row, Math.max(preferredHeight, defaultHeight));
        }
    }
}