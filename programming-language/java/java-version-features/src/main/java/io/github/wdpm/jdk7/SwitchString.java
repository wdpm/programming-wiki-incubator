package io.github.wdpm.jdk7;

/**
 * Switch 支持 String类型
 *
 * @author evan
 * @since 2020/4/19
 */
public class SwitchString {

    public static void main(String[] args) throws Exception {
        String status = "success";
        switch (status.toUpperCase()) {
            case "SUCCESS":
                System.out.println("Order success.");
                break;
            case "FAILURE":
                System.out.println("Order failed.");
                break;
            case "CANCEL":
                System.out.println("Order cancelled.");
                break;
            default:
                throw new Exception("Invalid order status: " + status);
        }
    }
}
