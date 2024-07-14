import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void checkBalance(JTextField balanceField) {
        balanceField.setText("Your current balance is: rs" + account.getBalance());
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }
}

public class Atmmachine {
    private ATM atm;
    private JFrame frame;
    private JTextField balanceField;
    private JTextField amountField;

    public Atmmachine(BankAccount account) {
        atm = new ATM(account);
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel balanceLabel = new JLabel("Balance:");
        balanceField = new JTextField(20);
        balanceField.setEditable(false);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(20);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atm.checkBalance(balanceField);
            }
        });

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    atm.deposit(amount);
                    atm.checkBalance(balanceField);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount");
                }
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (atm.withdraw(amount)) {
                        atm.checkBalance(balanceField);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount");
                }
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(balanceLabel);
        frame.add(balanceField);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(checkBalanceButton);
        frame.add(depositButton);
        frame.add(withdrawButton);
        frame.add(exitButton);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(500.0); // initial balance of $500
        Atmmachine atmMachine = new Atmmachine(account);
    }
}