import java.util.Scanner;

public class ProtocolHandler {

    public String join() {
        String userName;
        String splChrs = "-/@#$!%^&_+=()";
        Scanner input = new Scanner(System.in);
        String joinServ = input.nextLine();
        if (!joinServ.substring(0,4).equals("JOIN") || joinServ.matches(splChrs) ||	joinServ.length() > 16) {
            System.out.println("Follow the JOIN instructions");
            System.exit(1);

        }
        userName = joinServ.substring(5,joinServ.length());
        return userName;
    }

}
