public class InfixToPostfix {

    // Basit char yığın (dizi)
    static class CStack {
        char[] a = new char[100];
        int top = -1;

        void push(char c) {
            a[++top] = c;
        }

        char pop() {
            return a[top--];
        }

        char peek() {
            return a[top];
        }

        boolean isEmpty() {
            return top == -1;
        }
    }

    // Operatör mü?
    static boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Öncelik
    static int prec(char c) {
        if (c == '^') return 3;
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return 0;
    }

    // Sağdan bağlanan mı? (^ sağdan bağlanır)
    static boolean rightAssoc(char c) {
        return c == '^';
    }

    // İnfix → Postfix (tek haneli/harf operant, parantez destekli)
    static String toPostfix(String s) {
        CStack st = new CStack();
        StringBuilder out = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                // Operant ise direkt yaz
                out.append(ch);
            } else if (ch == '(') {
                st.push(ch);
            } else if (ch == ')') {
                // '(' görünceye kadar operatörleri boşalt
                while (!st.isEmpty() && st.peek() != '(') {
                    out.append(st.pop());
                }
                if (!st.isEmpty()) {
                    st.pop(); // '(' at
                }
            } else if (isOp(ch)) {
                // Öncelik/bağlanma kuralı
                while (!st.isEmpty() && isOp(st.peek())) {
                    char t = st.peek();
                    boolean lower = prec(ch) < prec(t);
                    boolean equalLeftAssoc = prec(ch) == prec(t) && !rightAssoc(ch);
                    if (lower || equalLeftAssoc) {
                        out.append(st.pop());
                    } else {
                        break;
                    }
                }
                st.push(ch);
            }
        }

        // Yığında kalanları ekle
        while (!st.isEmpty()) {
            out.append(st.pop());
        }

        return out.toString();
    }

    public static void main(String[] args) {
        String infix = "(A+B)*C-D/E";
        System.out.println("Infix   : " + infix);
        System.out.println("Postfix : " + toPostfix(infix)); // AB+C*DE/-
    }
}
