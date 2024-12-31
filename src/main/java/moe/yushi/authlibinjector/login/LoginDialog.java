package moe.yushi.authlibinjector.login;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
    boolean shouldContinue;
    JTextField idField;
    JTextField passwordField;

    public LoginDialog() {
        super();
        Container panel = this.getContentPane();

        JLabel header = new JLabel("AnvilAuth-Injector Login");
        panel.add(header, BorderLayout.NORTH);
        header.setFont(header.getFont().deriveFont(24f));

        panel.add(this.createMainPanel(), BorderLayout.CENTER);
        panel.add(this.createBottomBar(), BorderLayout.SOUTH);

        this.pack();
        this.addWindowListener(this.new CloseListener());
        this.setMinimumSize(this.getPreferredSize());
        this.setModal(true);
        this.setVisible(true);
    }

    private void closeAndContinue() {
        this.shouldContinue = true;
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private Container createBottomBar() {
        JPanel bottomBar = new JPanel();

        JButton exitButton = new JButton("Quit");
        bottomBar.add(exitButton);
        exitButton.addActionListener(
            alec -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING))
        );
        exitButton.setToolTipText("Abort the launch.");

        JButton continueButton = new JButton("Continue");
        bottomBar.add(continueButton);
        continueButton.addActionListener(alec -> this.closeAndContinue());
        continueButton.setToolTipText("Continue launching the game.");

        return bottomBar;
    }

    private Container createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        panel.add(new JLabel("User ID"));
        panel.add(this.idField = new JTextField());
        this.idField.setToolTipText(
            "Enter the User ID here. "
            + "It looks a bit like an email, consisting of a name and a domain separated by an @-sign. "
            + "Example: MyName@example.xyz"
        );

        panel.add(new JLabel("Password"));
        panel.add(this.passwordField = new JPasswordField());

        return panel;
    }

    public class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            // Quit the JVM when the dialog is closed without confirmation.
            if (!LoginDialog.this.shouldContinue)
                System.exit(0);
        }
    }
}
