public class PrefixToInfix {

    // String yığın
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

    // Prefix → Infix (sağdan sola tara, tam parantezli)
    static String toInfix(String s) {
        SStack st = new SStack();

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                st.push(String.valueOf(ch));
            } else if (isOp(ch)) {
                String a = st.pop(); // sol
                String b = st.pop(); // sağ
                st.push("(" + a + ch + b + ")");
            }
        }

        return st.pop();
    }

    public static void main(String[] args) {
        String prefix = "*+ABC";
        System.out.println("Prefix: " + prefix);
        System.out.println("Infix : " + toInfix(prefix)); // (A+B)*C
    }
}
