package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    public static void main (final String[] args) {

        ClientView clientView = new ClientView();
        VisitView visitView = new VisitView();
        Operation operation = null;
        do {
            try {
                operation = askOperation();
                switch (operation) {
                    case ADD_CLIENT: clientView.fireAddClient(); break;
                    case REGISTER_VISIT: visitView.fireRegisterVisit(); break;
                    case EXIT:
                        System.out.println("Пока!"); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели неверное число. Попробуйте еще раз.");
            } catch (Exception e) {
                System.out.println("Произошла ошибка, проверьте введённые данные.");
                e.printStackTrace();
            }

        } while (operation != Operation.EXIT);


    }
    static Operation askOperation() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("Выберите операцию:");
        System.out.println(String.format("\t%d - добавить клиента.", Operation.ADD_CLIENT.ordinal()));
        System.out.println(String.format("\t%d - зарегистрировать посещение.", Operation.REGISTER_VISIT.ordinal()));
        System.out.println(String.format("\t%d - выход.", Operation.EXIT.ordinal()));
        return Operation.values()[Integer.parseInt(reader.readLine().trim())];
    }
}
