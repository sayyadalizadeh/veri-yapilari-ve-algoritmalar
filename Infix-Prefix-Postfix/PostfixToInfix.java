public class PostfixToInfix {

    // String yığın (ifade parçaları)
    static class SStack {
        String[] a = new String[100];
        int top = -1;

        void push(String s) {
            a[++top] = s;
        }

        String pop() {
            return a[top--];
        }

        boolean isEmpty() {
            return top == -1;
        }
    }

    static boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Postfix → Infix (tam parantezli)
    static String toInfix(String s) {
        SStack st = new SStack();

        for (char ch : s.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                st.push(String.valueOf(ch));
            } else if (isOp(ch)) {
                // İki operant çek (a = sol, b = sağ)
                String b = st.pop();
                String a = st.pop();
                st.push("(" + a + ch + b + ")");
            }
        }

        return st.pop();
    }

    public static void main(String[] args) {
        String postfix = "AB+C*";
        System.out.println("Postfix: " + postfix);
        System.out.println("Infix  : " + toInfix(postfix)); // (A+B)*C
    }
}
