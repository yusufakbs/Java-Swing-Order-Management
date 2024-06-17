package org.yusufakbas.core;

import javax.swing.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }


    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        //info@test.com
        if (email == null || email.trim().isEmpty()) return false;

        if (!email.contains("@")) return false;

        String[] parseEmail = email.split("@");

        if (parseEmail.length != 2) return false;

        if (parseEmail[0].trim().isEmpty() || parseEmail[1].trim().isEmpty()) return false;

        if (!parseEmail[1].contains(".")) return false;

        return true;
    }

    public static void optionPaneDialogTR() {
        UIManager.put("OptionPane.yesButtonText", "TAMAM");
    }

    public static void showMsg(String message) {
        String title;
        String msg;

        title = switch (message) {
            case "fill" -> {
                msg = "Please fill in all fields";
                yield "Error!";
            }
            case "done" -> {
                msg = "Transaction successful";
                yield "Result";
            }
            case "error" -> {
                msg = "Transaction failed";
                yield "Error!";
            }
            default -> {
                msg = message;
                yield "Message";
            }
        };
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

    }


}
